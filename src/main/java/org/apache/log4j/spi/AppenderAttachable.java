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

package org.apache.log4j.spi;

import org.apache.log4j.Appender;

import java.util.Enumeration;

/**
 * Interface for attaching appenders to objects.
 *  一个可以将appender对象附着到其他对象的接口
 *  进一步理解为：这个接口可以将其他对象与appender对象建立某种联系
 *  这里的appender是一个用于决定将日志以何种形式输出到何处
 * @author Ceki G&uuml;lc&uuml;
 * @since 0.9.1
 */
public interface AppenderAttachable {

    /**
     * Add an appender.
     */
    public void addAppender(Appender newAppender);

    /**
     * Get all previously added appenders as an Enumeration.
     */
    public Enumeration getAllAppenders();

    /**
     * Get an appender by name.
     */
    public Appender getAppender(String name);


    /**
     * Returns <code>true</code> if the specified appender is in list of
     * attached attached, <code>false</code> otherwise.
     *
     * @since 1.2
     */
    public boolean isAttached(Appender appender);

    /**
     * Remove all previously added appenders.
     */
    void removeAllAppenders();


    /**
     * Remove the appender passed as parameter from the list of appenders.
     */
    void removeAppender(Appender appender);


    /**
     * Remove the appender with the name passed as parameter from the
     * list of appenders.
     */
    void removeAppender(String name);
}

