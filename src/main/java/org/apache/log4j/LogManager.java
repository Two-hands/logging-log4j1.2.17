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

package org.apache.log4j;

import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.DefaultRepositorySelector;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.spi.NOPLoggerRepository;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.helpers.LogLog;

import java.net.URL;
import java.net.MalformedURLException;


import java.util.Enumeration;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Use the <code>LogManager</code> class to retreive {@link Logger}
 * instances or to operate on the current {@link
 * LoggerRepository}. When the <code>LogManager</code> class is loaded
 * into memory the default initalzation procedure is inititated. The
 * default intialization procedure</a> is described in the <a
 * href="../../../../manual.html#defaultInit">short log4j manual</a>.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class LogManager {

    /**
     * @deprecated This variable is for internal use only. It will
     * become package protected in future versions.
     */
    static public final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";

    static final String DEFAULT_XML_CONFIGURATION_FILE = "log4j.xml";

    /**
     * @deprecated This variable is for internal use only. It will
     * become private in future versions.
     */
    static final public String DEFAULT_CONFIGURATION_KEY = "log4j.configuration";

    /**
     * @deprecated This variable is for internal use only. It will
     * become private in future versions.
     */
    static final public String CONFIGURATOR_CLASS_KEY = "log4j.configuratorClass";

    /**
     * @deprecated This variable is for internal use only. It will
     * become private in future versions.
     */
    public static final String DEFAULT_INIT_OVERRIDE_KEY =
            "log4j.defaultInitOverride";


    static private Object guard = null;

    /*
    仓库选择器
     */
    static private RepositorySelector repositorySelector;

    static {

        Hierarchy h = new Hierarchy(new RootLogger((Level) Level.DEBUG));
        repositorySelector = new DefaultRepositorySelector(h);


        /*
        获取log4j.defaultInitOverride系统属性
         */
        String override = OptionConverter.getSystemProperty(DEFAULT_INIT_OVERRIDE_KEY,
                null);


        /*
        log4j.defaultInitOverride为true,跳过从指定的路径或者类路径下获取配置文件
         */
        if (override == null || "false".equalsIgnoreCase(override)) {

            /*
            log4j.configuration  自定义指定配置文件的位置
            若没指定：
               先尝试从类路径获取log4j.xml配置文件
               获取不到再尝试从类路径获取log4j.properties配置文件
             */
            String configurationOptionStr = OptionConverter.getSystemProperty(
                    DEFAULT_CONFIGURATION_KEY,
                    null);

            /*
            log4j.configuratorClass  自定义解析配置文件的类
            若没指定：
                若配置文件以.xml结尾，用org.apache.log4j.xml.DOMConfigurator类解析配置文件
                若配置文件不以.xml结尾，用org.apache.log4j.PropertyConfigurator类解析配置文件
             */
            String configuratorClassName = OptionConverter.getSystemProperty(
                    CONFIGURATOR_CLASS_KEY,
                    null);

            URL url;

            /*
            获取配置文件：
             */
            if (configurationOptionStr == null) {
                //若用户没有指定log4j.configuration配置
                //  默认从类路径先获取log4j.xml配置文件
                url = Loader.getResource(DEFAULT_XML_CONFIGURATION_FILE);
                if (url == null) {
                    //若没有改文件就从类路径中获取log4j.properties配置文件
                    url = Loader.getResource(DEFAULT_CONFIGURATION_FILE);
                }
            } else {
                //若用户设置了log4j.configuration的值，就以log4j.configuration的值获取配置文件
                try {
                    //先将其作为资源路径进行获取
                    url = new URL(configurationOptionStr);
                } catch (MalformedURLException ex) {
                    //再将其作为类路径资源进行获取
                    url = Loader.getResource(configurationOptionStr);
                }
            }


            //解析配置文件信息
            if (url != null) {
                LogLog.debug("Using URL [" + url + "] for automatic log4j configuration.");
                try {
                    OptionConverter.selectAndConfigure(url, configuratorClassName,
                            LogManager.getLoggerRepository());
                } catch (NoClassDefFoundError e) {
                    LogLog.warn("Error during default initialization", e);
                }
            } else {
                LogLog.debug("Could not find resource: [" + configurationOptionStr + "].");
            }
        } else {
            LogLog.debug("Default initialization of overridden by " +
                    DEFAULT_INIT_OVERRIDE_KEY + "property.");
        }
    }

    /**
     * Sets <code>LoggerFactory</code> but only if the correct
     * <em>guard</em> is passed as parameter.
     *
     * <p>Initally the guard is null.  If the guard is
     * <code>null</code>, then invoking this method sets the logger
     * factory and the guard. Following invocations will throw a {@link
     * IllegalArgumentException}, unless the previously set
     * <code>guard</code> is passed as the second parameter.
     *
     * <p>This allows a high-level component to set the {@link
     * RepositorySelector} used by the <code>LogManager</code>.
     *
     * <p>For example, when tomcat starts it will be able to install its
     * own repository selector. However, if and when Tomcat is embedded
     * within JBoss, then JBoss will install its own repository selector
     * and Tomcat will use the repository selector set by its container,
     * JBoss.
     */
    static
    public void setRepositorySelector(RepositorySelector selector, Object guard)
            throws IllegalArgumentException {
        if ((LogManager.guard != null) && (LogManager.guard != guard)) {
            throw new IllegalArgumentException(
                    "Attempted to reset the LoggerFactory without possessing the guard.");
        }

        if (selector == null) {
            throw new IllegalArgumentException("RepositorySelector must be non-null.");
        }

        LogManager.guard = guard;
        LogManager.repositorySelector = selector;
    }


    /**
     * This method tests if called from a method that
     * is known to result in class members being abnormally
     * set to null but is assumed to be harmless since the
     * all classes are in the process of being unloaded.
     *
     * @param ex exception used to determine calling stack.
     * @return true if calling stack is recognized as likely safe.
     */
    private static boolean isLikelySafeScenario(final Exception ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String msg = stringWriter.toString();
        return msg.indexOf("org.apache.catalina.loader.WebappClassLoader.stop") != -1;
    }

    static
    public LoggerRepository getLoggerRepository() {
        if (repositorySelector == null) {
            repositorySelector = new DefaultRepositorySelector(new NOPLoggerRepository());
            guard = null;
            Exception ex = new IllegalStateException("Class invariant violation");
            String msg =
                    "log4j called after unloading, see http://logging.apache.org/log4j/1.2/faq.html#unload.";
            if (isLikelySafeScenario(ex)) {
                LogLog.debug(msg, ex);
            } else {
                LogLog.error(msg, ex);
            }
        }
        return repositorySelector.getLoggerRepository();
    }

    /**
     * Retrieve the appropriate root logger.
     */
    public
    static Logger getRootLogger() {
        // Delegate the actual manufacturing of the logger to the logger repository.
        return getLoggerRepository().getRootLogger();
    }

    /**
     * Retrieve the appropriate {@link Logger} instance.
     */
    public
    static Logger getLogger(final String name) {
        // Delegate the actual manufacturing of the logger to the logger repository.
        //将实际创建logger实例的工作委派给LoggerRepository
        return getLoggerRepository().getLogger(name);
    }

    /**
     * Retrieve the appropriate {@link Logger} instance.
     */
    public
    static Logger getLogger(final Class clazz) {
        // Delegate the actual manufacturing of the logger to the logger repository.
        return getLoggerRepository().getLogger(clazz.getName());
    }


    /**
     * Retrieve the appropriate {@link Logger} instance.
     */
    public
    static Logger getLogger(final String name, final LoggerFactory factory) {
        // Delegate the actual manufacturing of the logger to the logger repository.
        return getLoggerRepository().getLogger(name, factory);
    }

    public
    static Logger exists(final String name) {
        return getLoggerRepository().exists(name);
    }

    public
    static Enumeration getCurrentLoggers() {
        return getLoggerRepository().getCurrentLoggers();
    }

    public
    static void shutdown() {
        getLoggerRepository().shutdown();
    }

    public
    static void resetConfiguration() {
        getLoggerRepository().resetConfiguration();
    }
}

