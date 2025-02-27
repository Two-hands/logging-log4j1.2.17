/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package examples;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * NumberCruncher's factor positive integers. See <a
 * href=doc-files/NumberCruncher.java>source</a> code for more details.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public interface NumberCruncher extends Remote {

    /**
     * Factor a positive integer <code>number</code> and return its
     * <em>distinct</em> factor's as an integer array.
     */
    int[] factor(int number) throws RemoteException;
}
