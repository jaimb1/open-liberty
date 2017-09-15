/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package javax.faces;

/**
 * Any wrapper class in JSF that must provide access to the object it wraps must implement this interface.
 * 
 * @since 2.0
 */
public interface FacesWrapper<T>
{
    /**
     * A class that implements this interface uses this method to return an instance of the class being wrapped.
     * 
     * @return the instance of the class being wrapped
     */
    public T getWrapped();
}
