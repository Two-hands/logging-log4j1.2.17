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
package examples.lf5.InitUsingLog4JProperties;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * This class is a simple example of how to use the LogFactor5 logging
 * window.
 * <p>
 * The LF5Appender is the primary class that enables logging to the
 * LogFactor5 logging window. The simplest method of using this Appender
 * is to add the following line to your log4j.properties file:
 * <p>
 * log4j.appender.A1=org.apache.log4j.lf5.LF5Appender
 * <p>
 * The log4j.properties file MUST be in you system classpath. If this file
 * is in your system classpath, a static initializer in the Category class
 * will load the file during class initialization. The LF5Appender will be
 * added to the root category of the Category tree.
 * <p>
 * Create a log4j.properties file and add this line to it, or add this line
 * to your existing log4j.properties file. Run the example at the command line
 * and explore the results!
 *
 * @author Brent Sprecher
 */

// Contributed by ThoughtWorks Inc.

public class InitUsingLog4JProperties {
    //--------------------------------------------------------------------------
    //   Constants:
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //   Protected Variables:
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //   Private Variables:
    //--------------------------------------------------------------------------

    private static Logger logger =
            Logger.getLogger(InitUsingLog4JProperties.class);

    //--------------------------------------------------------------------------
    //   Constructors:
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //   Public Methods:
    //--------------------------------------------------------------------------

    public static void main(String argv[]) {
        // Add a bunch of logging statements ...
        logger.debug("Hello, my name is Homer Simpson.");
        logger.debug("Hello, my name is Lisa Simpson.");
        logger.debug("Hello, my name is Marge Simpson.");
        logger.debug("Hello, my name is Bart Simpson.");
        logger.debug("Hello, my name is Maggie Simpson.");

        logger.info("We are the Simpsons!");
        logger.info("Mmmmmm .... Chocolate.");
        logger.info("Homer likes chocolate");
        logger.info("Doh!");
        logger.info("We are the Simpsons!");

        logger.warn("Bart: I am through with working! Working is for chumps!" +
                "Homer: Son, I'm proud of you. I was twice your age before " +
                "I figured that out.");
        logger.warn("Mmm...forbidden donut.");
        logger.warn("D'oh! A deer! A female deer!");
        logger.warn("Truly, yours is a butt that won't quit." +
                "- Bart, writing as Woodrow to Ms. Krabappel.");

        logger.error("Dear Baby, Welcome to Dumpsville. Population: you.");
        logger.error("Dear Baby, Welcome to Dumpsville. Population: you.",
                new IOException("Dumpsville, USA"));
        logger.error("Mr. Hutz, are you aware you're not wearing pants?");
        logger.error("Mr. Hutz, are you aware you're not wearing pants?",
                new IllegalStateException("Error !!"));


        logger.fatal("Eep.");
        logger.fatal("Mmm...forbidden donut.",
                new SecurityException("Fatal Exception"));
        logger.fatal("D'oh! A deer! A female deer!");
        logger.fatal("Mmmmmm .... Chocolate.",
                new SecurityException("Fatal Exception"));
    }

    //--------------------------------------------------------------------------
    //   Protected Methods:
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //   Private Methods:
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //   Nested Top-Level Classes or Interfaces:
    //--------------------------------------------------------------------------

}
