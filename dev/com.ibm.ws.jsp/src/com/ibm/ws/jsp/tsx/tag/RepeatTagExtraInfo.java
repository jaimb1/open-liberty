/*******************************************************************************
 * Copyright (c) 1997, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.jsp.tsx.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
* @author todd
*
* To change this generated comment edit the template variable "typecomment":
* Window>Preferences>Java>Templates.
* To enable and disable the creation of type comments go to
* Window>Preferences>Java>Code Generation.
*/
public class RepeatTagExtraInfo extends TagExtraInfo {

/**
 * Constructor for RepeatTagExtraInfo.
 */
public RepeatTagExtraInfo() {
    super();
}

public VariableInfo[] getVariableInfo(TagData data) {
    if (data.getAttributeString("index") != null) {
        return (new VariableInfo[] { new VariableInfo(data.getAttributeString("index"),"java.lang.Integer" , true, VariableInfo.NESTED)});
    }
    else {
        return null;
    }
}

}
