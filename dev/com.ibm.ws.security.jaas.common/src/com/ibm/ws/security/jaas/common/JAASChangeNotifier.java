/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.security.jaas.common;

import com.ibm.ws.security.notifications.BaseSecurityChangeNotifier;
import com.ibm.ws.security.notifications.SecurityChangeNotifier;

/**
 * Service to send notifications to registered SecurityChangeListener objects
 * when the JAAS configuration is modified.
 */
public class JAASChangeNotifier extends BaseSecurityChangeNotifier implements SecurityChangeNotifier {

}
