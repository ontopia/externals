/*
 * The Topic Maps API (TMAPI) was created collectively by
 * the membership of the tmapi-discuss mailing list
 * <http://lists.sourceforge.net/mailman/listinfo/tmapi-discuss>,
 * is hereby released into the public domain; and comes with 
 * NO WARRANTY.
 * 
 * No one owns TMAPI: you may use it freely in both commercial and
 * non-commercial applications, bundle it with your software
 * distribution, include it on a CD-ROM, list the source code in a
 * book, mirror the documentation at your own web site, or use it in
 * any other way you see fit.
 */
package org.tmapi.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * This factory class provides access to a topic map system. 
 * 
 * A new {@link TopicMapSystem} instance is created by invoking the 
 * {@link #newTopicMapSystem()} method. 
 * Configuration properties for the new {@link TopicMapSystem} instance can be 
 * set by calling the {@link #setFeature(String, boolean)} and / or 
 * {@link #setProperty(String, Object)} methods prior to invoking 
 * {@link #newTopicMapSystem()}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 155 $ - $Date: 2010-02-27 18:46:28 +0000 (Sat, 27 Feb 2010) $
 */
public abstract class TopicMapSystemFactory {

    /**
     * The key under which the implementation class name should
     * be specified in the system runtime properties or in the 
     * properties file lib/tmapi.properties under JAVA_HOME.
     */
    private static final String FACTORY_PROPERTY = "org.tmapi.core.TopicMapSystemFactory";
    
    /**
     * The path to a JAR resource that contains the implementation
     * class name as the only text on the first line of the file.
     */
    private static final String FACTORY_CONFIGURATION_RESOURCE = "/META-INF/services/" + FACTORY_PROPERTY;

    /**
     * Returns if the particular feature is supported by the 
     * {@link TopicMapSystem}.
     * <p>
     * Opposite to {@link #getFeature} this method returns if the requested 
     * feature is generally available / supported by the underlying 
     * {@link TopicMapSystem} and does not return the state (enabled/disabled) 
     * of the feature.
     * </p>
     * 
     * @param featureName The name of the feature to check.
     * @return <tt>true</tt> if the requested feature is supported, 
     *          otherwise <tt>false</tt>.
     */
    abstract public boolean hasFeature(String featureName);

    /**
     * Returns the particular feature requested for in the underlying
     * implementation of {@link TopicMapSystem}.
     * 
     * @param featureName The name of the feature to check.
     * @return <tt>true</tt> if the named feature is enabled for
     *         {@link TopicMapSystem} instances created by this factory;
     *         <tt>false</tt> if the named feature is disabled for
     *         {@link TopicMapSystem} instances created by this factory.
     * @throws FeatureNotRecognizedException
     *             If the underlying implementation does not recognize the
     *             named feature.
     */
    abstract public boolean getFeature(String featureName)
            throws FeatureNotRecognizedException;

    /**
     * Sets a particular feature in the underlying implementation of 
     * {@link TopicMapSystem}. 
     * <p>
     * A list of the core features can be found at 
     * <a href="http://tmapi.org/features/">http://tmapi.org/features/</a>.
     * </p>
     * 
     * @param featureName The name of the feature to be set.
     * @param enable <tt>true</tt> to enable the feature, 
     *                  <tt>false</tt> to disable it.
     * @throws FeatureNotRecognizedException If the underlying implementation 
     *          does not recognize the named feature.
     * @throws FeatureNotSupportedException If the underlying implementation 
     *          recognizes the named feature but does not support enabling or 
     *          disabling it (as specified by the <tt>enabled</tt> parameter.
     */
    abstract public void setFeature(String featureName, boolean enable)
            throws FeatureNotSupportedException, FeatureNotRecognizedException;

    /**
     * Gets the value of a property in the underlying implementation of 
     * {@link TopicMapSystem}.
     * <p>
     * A list of the core properties defined by TMAPI can be found at 
     * <a href="http://tmapi.org/properties/">http://tmapi.org/properties/</a>. 
     * </p>
     * 
     * An implementation is free to support properties other than the core ones.
     * 
     * @param propertyName The name of the property to retrieve.
     * @return The value set for this property or <tt>null</tt> if no value is 
     *          currently set for the property.
     */
    abstract public Object getProperty(String propertyName);

    /**
     * Sets a property in the underlying implementation of 
     * {@link TopicMapSystem}.
     * <p>
     * A list of the core properties defined by TMAPI can be found at 
     * <a href="http://tmapi.org/properties/">http://tmapi.org/properties/</a>. 
     * </p>
     * An implementation is free to support properties other than the core ones.
     * 
     * @param propertyName The name of the property to be set.
     * @param value The value to be set of this property or <tt>null</tt> to 
     *              remove the property from the current factory configuration.
     */
    abstract public void setProperty(String propertyName, Object value);

    /**
     * Creates a new {@link TopicMapSystem} instance using the currently
     * configured factory parameters.
     *
     * @return A new instance of a {@link TopicMapSystem}
     * @throws TMAPIException If a TopicMapSystem cannot be created which
     *              satisfies the requested configuration.
     */
    abstract public TopicMapSystem newTopicMapSystem() throws TMAPIException;

    /**
     * Obtain a new instance of a TopicMapSystemFactory. This static method
     * creates a new factory instance. This method uses the following ordered
     * lookup procedure to determine the TopicMapSystemFactory implementation
     * class to load:
     * <ul>
     * <li> Use the <code>org.tmapi.core.TopicMapSystemFactory</code> system
     * property. The value of this property is the fully qualified name of the
     * implementation class to use. </li>
     * <li> Use the properties file <code>lib/tmapi.properties</code> in the
     * JRE directory. This configuration file is in standard
     * java.util.Properties format and contains the fully qualified name of the
     * implementation class with the key being the system property defined
     * above. </li>
     * <li> Use the Services API (as detailed in the JAR specification), if
     * available, to determine the classname. The Services API will look for a
     * classname in the file
     * <code>META-INF/services/org.tmapi.TopicMapSystemFactory</code> in jars
     * available to the runtime. </li>
     * </ul>
     * <p>
     * Once an application has obtained a reference to a TopicMapSystemFactory
     * it can use the factory to configure and obtain TopicMapSystem instances.
     * </p>
     * 
     * @return A new instance of TopicMapSystemFactory.
     * @throws FactoryConfigurationException
     */
    public static TopicMapSystemFactory newInstance()
            throws FactoryConfigurationException {
        Class<? extends TopicMapSystemFactory> implClass = getImplementationClass();
        try {
            return implClass.newInstance();
        } 
        catch (Exception ex) {
            throw new FactoryConfigurationException(
                    "Unable to instantiate the TopicMapSystemFactory implementation "
                            + implClass.getName() + ".", ex);
        }
    }

    /**
     * Implements the class name lookup logic for the newInstance() method. When
     * a class name is found, this method attempts to load the specified class.
     * 
     * @return The loaded implementation class or null if no implementation
     *         class name was found.
     * @throws FactoryConfigurationException
     *             If the specified implementation class could not be loaded.
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends TopicMapSystemFactory> getImplementationClass()
            throws FactoryConfigurationException {
        try {
            // Try use OSGi. Will fail silently within non-OSGi environments
            Class spiClass = org.tmapi.core.internal.OsgiLocator.locate(FACTORY_PROPERTY);
            if (spiClass != null) {
                return (Class<TopicMapSystemFactory>) spiClass;
            }
        }
        catch (Throwable e) {
            // noop.
        }
        String implClassName = getClassNameFromProperties();
        if (implClassName == null) {
            implClassName = getClassNameFromResource();
        }
        if (implClassName == null) {
            throw new FactoryConfigurationException(
                    "No TopicMapSystemFactory implementation class specified.");
        } 
        else {
            try {
                return (Class<TopicMapSystemFactory>) Class.forName(implClassName);
            } 
            catch (Exception ex) {
                throw new FactoryConfigurationException(
                        "Error loading TopicMapSystemFactory implementation class "
                                + implClassName + ".", ex);
            }
        }
    }

    /**
     * Attempts to locate the factory implementation class name in either the
     * system properties or in the file lib/tmapi.properties under the home
     * directory of the Java runtime.
     * 
     * @return The factory implementation class name configured in the system
     *         properties or run-time properties file or <code>null</code> if
     *         no implementation class name was specified in the system
     *         properties and the file lib/tmapi.properties does not exist.
     * @throws FactoryConfigurationException
     *             If an error occured while reading the lib/tmapi.properties
     *             file.
     */
    private static String getClassNameFromProperties()
            throws FactoryConfigurationException {
        String factoryClassName = System.getProperty(FACTORY_PROPERTY);

        if (factoryClassName == null) {
            String javaHome = System.getProperty("java.home");
            if (javaHome != null) {
                File propsFile = new File(javaHome + File.separator + "lib"
                        + File.separator + "tmapi.properties");
                FileInputStream fis = null;
                try {
                    if (propsFile.exists()) {
                        Properties props = new Properties();
                        fis = new FileInputStream(propsFile);
                        props.load(fis);
                        factoryClassName = props.getProperty(FACTORY_PROPERTY);
                    }
                } catch (IOException ex) {
                    throw new FactoryConfigurationException(
                            "Unable to read TMAPI properties file "
                                    + propsFile.getAbsolutePath() + ".", ex);
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException ex) {
                            // pass
                        }
                    }
                }
            }
        }
        return factoryClassName;
    }

    /**
     * Attempts to locate the TopicMapSystemFactory implementation class name in
     * the file META-INF/services/org.tmapi.core.TopicMapSystemFactory in jars
     * available to the runtime.
     * 
     * @return The implementation class name found on the first line of the
     *         configuration file or <code>null</code> if no such file was
     *         found.
     */
    private static String getClassNameFromResource()
            throws FactoryConfigurationException {
        InputStream is = TopicMapSystemFactory.class
                .getResourceAsStream(FACTORY_CONFIGURATION_RESOURCE);
        if (is != null) {
            try {
                BufferedReader rdr = new BufferedReader(new InputStreamReader(is));
                String line = rdr.readLine();
                return line;
            } catch (IOException ex) {
                throw new FactoryConfigurationException(
                        "Error reading TopicMapSystemFactory services configuration file.",
                        ex);
            }
        } else {
            return null;
        }
    }

}
