/*
 * =============================================================================
 * Copyright by Benjamin Sempere,
 * All rights reserved.
 * =============================================================================
 * Author  : Benjamin Sempere
 * E-mail  : benjamin@sempere.org
 * Homepage: www.sempere.org
 * =============================================================================
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.sempere.commons.proxy;

import org.junit.*;

import static junit.framework.Assert.*;

/**
 * Unit tests class for ProxyHelper.
 *
 * @author bsempere
 */
public class ProxyHelperTest {

    @Test
    public void createProxyWithCustomInterface() throws Exception {
        ToStringSupport proxy = ProxyHelper.createProxy(new DelegateHandler<String>("MyString"), ToStringSupport.class);
        assertEquals("MyString", proxy.toString());
    }

    // *************************************************************************
    //
    // Test fixtures, stubs, ...
    //
    // *************************************************************************

    private interface ToStringSupport {

        String toString();
    }
}
