/*******************************************************************************
 * Copyright (c) 1997, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.http.channel.h2internal;

import java.io.IOException;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.http.channel.internal.HttpMessages;
import com.ibm.wsspi.channelfw.VirtualConnection;
import com.ibm.wsspi.tcpchannel.TCPWriteCompletedCallback;
import com.ibm.wsspi.tcpchannel.TCPWriteRequestContext;

public class H2MuxTCPWriteCallback implements TCPWriteCompletedCallback {

    private static final TraceComponent tc = Tr.register(H2MuxTCPWriteCallback.class, HttpMessages.HTTP_TRACE_NAME, HttpMessages.HTTP_BUNDLE);

    H2WriteQEntry qEntry = null;
    H2WorkQInterface h2WorkQ = null;

    public void setCurrentQEntry(H2WriteQEntry x) {
        if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
            Tr.debug(tc, "muxCallback entry set to stream-id: " + x.getStreamID());
        }
        qEntry = x;
    }

    public void setH2WorkQ(H2WorkQInterface x) {
        h2WorkQ = x;
    }

    @Override
    public void complete(VirtualConnection vc, TCPWriteRequestContext twc) {

        commonProcessing(vc, twc, null, true);
    }

    @Override
    public void error(VirtualConnection vc, TCPWriteRequestContext twc, IOException ioe) {

        commonProcessing(vc, twc, ioe, false);
    }

    public void commonProcessing(VirtualConnection vc, TCPWriteRequestContext twc, IOException ioe, boolean complete) {

        // if calling the callbacks is moved to after this code, then the H2WriteQEntry needs to be copied to a variable that is local to avoid a race
        // condition.
        // need a local copy, since the next async write could be dequeued and executed once we hit either latch
        // H2WriteQEntry qEntry = qEntry;

        // should not be null, but if so, debug and leave, rather than NPE
        if (qEntry == null) {
            if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
                Tr.debug(tc, "no queue entries for this callback");
            }
            return;
        }

        // If this was an async write, then invoke the original caller's callback
        if (qEntry.getWriteType() == H2WriteQEntry.WRITE_TYPE.ASYNC) {
            VirtualConnection eVC = qEntry.getConnectionContext().getVC();
            TCPWriteRequestContext eTWC = qEntry.getConnectionContext().getWriteInterface();

            try {
                if (complete) {

                    if (h2WorkQ != null) {
                        if (qEntry.getFrameType() == FrameTypes.DATA) {
                            h2WorkQ.decreaseConnectionWindowUpdateWriteLimit(qEntry.getPayloadLength());
                        }
                    }

                    if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
                        Tr.debug(tc, "invoke complete callback vc: " + eVC + " TWC: " + eTWC);
                    }
                    qEntry.getCallback().complete(eVC, eTWC);
                } else {
                    if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
                        Tr.debug(tc, "invoke error callback vc: " + eVC + " TWC: " + eTWC);
                    }
                    qEntry.getCallback().error(eVC, eTWC, ioe);
                }
            } catch (Throwable t) {
                // debug, not much else to do with it
                if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
                    Tr.debug(tc, "caught a Throwable. log and leave: " + t);
                }
            }
        }

        // Having this after the callback is not good for performance, but issue is if there can be more than one write outstanding per a stream
        // on the write queue, then writes could finish out of order if this is before the callback.
        // Since most writes should be sync, hopefully this will not become an issue
        // Release waiting threads, Sync writes are waiting, and/or the queue service thread is waiting so it can start another write
        if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
            Tr.debug(tc, "hit write complete latch for qentry: " + qEntry.hashCode());
        }

        qEntry.hitWriteCompleteLatch();
        // allow the next thread to come through, or allow the queue service thread to start, if it is waiting.
        if (h2WorkQ != null) {
            h2WorkQ.notifyStandBy();
        }

    }

}
