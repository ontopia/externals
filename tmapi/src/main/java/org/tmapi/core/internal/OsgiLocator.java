/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tmapi.core.internal;

import java.util.concurrent.Callable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class OsgiLocator {

    private static Map<String, List<Callable<Class>>> _factories;

    private OsgiLocator() {
    }

    public static synchronized void unregister(String id, Callable<Class> factory) {
        if (_factories != null) {
            List<Callable<Class>> l = _factories.get(id);
            if (l != null) {
                l.remove(factory);
            }
        }
    }

    public static synchronized void register(String id, Callable<Class> factory) {
        if (_factories == null) {
            _factories = new HashMap<String, List<Callable<Class>>>();
        }
        List<Callable<Class>> l = _factories.get(id);
        if (l == null) {
            l = new ArrayList<Callable<Class>>();
            _factories.put(id, l);
        }
        l.add(factory);
    }

    public static synchronized Class locate(String factoryId) {
        if (_factories != null) {
            List<Callable<Class>> l = _factories.get(factoryId);
            if (l != null && !l.isEmpty()) {
                Callable<Class> c = l.get(l.size() - 1);
                try {
                    return c.call();
                }
                catch (Exception e) {
                }
            }
        }
        return null;
    }

}
