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
package com.ibm.ws.microprofile.config.fat.tests;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.ibm.ws.fat.util.SharedServer;

/**
 *
 */
public class CustomSourcesTest extends AbstractConfigApiTest {

    @ClassRule
    public static SharedServer SHARED_SERVER = new SharedServer("CustomSourcesServer");

    public CustomSourcesTest() {
        super("/customSources/");
    }

    @Override
    protected SharedServer getSharedServer() {
        return SHARED_SERVER;
    }

    @Rule
    public TestName testName = new TestName();

    /**
     * This tests proper sorting of ordinals and that this is done
     * on a per property interface (not all properties in a source
     * may be 'winning' config sources).
     *
     * What happens if two sources have the same ordinal
     *
     * @throws Exception
     */
    @Test
    public void testOrdinalsCustomSources() throws Exception {
        test(testName.getMethodName());
    }

    /**
     * This tests user provided ConfigSource objects together
     * with them being interleaved with default sources and value overriding.
     *
     * @throws Exception
     */
    @Test
    public void testInterleaveCustomDefaultSources() throws Exception {
        test(testName.getMethodName());
    }

    /**
     * Tests getConfig with ClassLoader that effects how resourceNames are loaded
     *
     * @throws Exception
     */
    @Test
    public void testServiceLoadingSources() throws Exception {
        test(testName.getMethodName());
    }

}
