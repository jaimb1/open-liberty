/*******************************************************************************
 * Copyright (c) 2004, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wsspi.genericbnf.exception;

/**
 * Thrown when the scheme given is not supported or enabled.
 * 
 * @ibm-private-in-use
 */
public class UnsupportedSchemeException extends MalformedMessageException {

    /** Serialization ID value */
    static final private long serialVersionUID = 7319004757355220458L;

    /**
     * Constructor for the unsupported scheme exception
     * 
     * @param message
     */
    public UnsupportedSchemeException(String message) {
        super(message);
    }
}
