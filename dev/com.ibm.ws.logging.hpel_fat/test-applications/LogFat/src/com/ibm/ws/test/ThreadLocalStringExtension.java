/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * This program may be used, executed, copied, modified and distributed
 * without royalty for the purpose of developing, using, marketing, or distributing.
 */

package com.ibm.ws.test;

import java.util.logging.Logger;

import com.ibm.websphere.logging.hpel.LogRecordContext;

public class ThreadLocalStringExtension implements LogRecordContext.Extension {
    private static Logger logger = Logger.getLogger("com.ibm.ws.test.ThreadLocalStringExtension");

    public ThreadLocalStringExtension() {}

    private final ThreadLocal<String> threadLocalString = new ThreadLocal<String>();

    public void setValue(String string) {
        threadLocalString.set(string);
        logger.info("setValue - new value: [" + string + "] for thread " + Thread.currentThread().getName());
//        System.out.println("setValue - new value: [" + string + "] for thread " + Thread.currentThread().getName());
    }

    @Override
    public String getValue() {
        return threadLocalString.get();
        // don't log here or you will get a recursion
    }
}
