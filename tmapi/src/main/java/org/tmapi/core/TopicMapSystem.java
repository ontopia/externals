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

import java.util.Set;

/**
 * A generic interface to a TMAPI system. 
 * 
 * Any TMAPI system must be capable of providing access to one or more 
 * {@link TopicMap} objects. A TMAPI system may be capable of allowing a client
 * to create new {@link TopicMap} instances.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 122 $ - $Date: 2009-10-01 17:08:29 +0000 (Thu, 01 Oct 2009) $
 */
public interface TopicMapSystem {

    /**
     * Retrieves a {@link TopicMap} managed by this system with the
     * specified storage address <tt>iri</tt>. 
     * 
     * The string is assumed to be in IRI notation.
     * <p>
     * This method should return the same result as the following code:
     * <pre>
     *      TopicMapSystem tmSys = ... // Assumed to be there
     *      String iri = "http://www.example.org/tm";
     *      TopicMap tm = tmSys.getTopicMap(tmSys.createLocator(iri));
     * </pre>
     * </p>
     * 
     * @see #getTopicMap(Locator)
     * 
     * @param iri The storage address to retrieve the {@link TopicMap} from.
     * @return The {@link TopicMap} instance managed by this system which 
     *         is stored at the specified <tt>iri</tt>, or <tt>null</tt> if no 
     *         such {@link TopicMap} is found.
     */
    public TopicMap getTopicMap(String iri);

    /**
     * Retrieves a {@link TopicMap} managed by this system with the
     * specified storage address <tt>iri</tt>. 
     * 
     * @see #getTopicMap(String)
     * 
     * @param iri The storage address to retrieve the {@link TopicMap} from.
     * @return The {@link TopicMap} instance managed by this system which 
     *         is stored at the specified <tt>iri</tt>, or <tt>null</tt> if no 
     *         such {@link TopicMap} is found.
     */
    public TopicMap getTopicMap(Locator iri);

    /**
     * Returns all storage addresses of {@link TopicMap} instances known by this
     * system.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable set of {@link Locator}s which represent 
     *          IRIs of known {@link TopicMap} instances.
     */
    public Set<Locator> getLocators();

    /**
     * @see org.tmapi.core.TopicMap#createLocator(String reference)
     */
    public Locator createLocator(String reference) throws MalformedIRIException;

    /**
     * Creates a new {@link TopicMap} and stores it within the system under the
     * specified <tt>iri</tt>. 
     * 
     * @see #createTopicMap(String)
     * 
     * @param iri The address which should be used to store the {@link TopicMap}.
     * @return The newly created {@link TopicMap} instance.
     * @throws TopicMapExistsException If this TopicMapSystem already manages a
     *                 {@link TopicMap} under the specified IRI.
     */
    public TopicMap createTopicMap(Locator iri) throws TopicMapExistsException;

    /**
     * Creates a new {@link TopicMap} and stores it within the system under the
     * specified <tt>iri</tt>. 
     * 
     * The string is assumed to be in IRI notation.
     * <p>
     * This method should return the same result as the following code:
     * <pre>
     *      TopicMapSystem tmSys = ... // Assumed to be there
     *      String iri = "http://www.example.org/tm";
     *      TopicMap tm = tmSys.createTopicMap(tmSys.createLocator(iri));
     * </pre>
     * </p>
     * 
     * @see #createTopicMap(Locator)
     * 
     * @param iri The address which should be used to store the {@link TopicMap}.
     * @return The newly created {@link TopicMap} instance.
     * @throws TopicMapExistsException If this TopicMapSystem already manages a
     *                 {@link TopicMap} under the specified IRI.
     */
    public TopicMap createTopicMap(String iri) throws TopicMapExistsException;

    /**
     * Returns the value of the feature specified by <tt>featureName</tt>
     * for this TopicMapSystem instance. 
     * <p>
     * The features supported by the TopicMapSystem and the value for each 
     * feature is set when the TopicMapSystem is created by a call to 
     * {@link TopicMapSystemFactory#newTopicMapSystem()} and cannot be modified
     * subsequently.
     * </p>
     * 
     * @param featureName The name of the feature to check.
     * @return <tt>true</tt> if the named feature is enabled for this TopicMapSystem 
     *          instance; <tt>false</tt> if the named feature is disabled for 
     *          this instance.
     * @throws FeatureNotRecognizedException If the underlying implementation 
     *          does not recognize the named feature.
     */
    public boolean getFeature(String featureName)
            throws FeatureNotRecognizedException;

    /**
     * Returns a property in the underlying implementation of 
     * {@link TopicMapSystem}.
     * <p>
     * A list of the core properties defined by TMAPI can be found at 
     * <a href="http://tmapi.org/properties/">http://tmapi.org/properties/</a>.
     * </p>
     * <p>
     * An implementation is free to support properties other than the core ones.
     * </p>
     * <p>
     * The properties supported by the TopicMapSystem
     * and the value for each property is set when the TopicMapSystem is created
     * by a call to {@link TopicMapSystemFactory#newTopicMapSystem()} and cannot
     * be modified subsequently.
     * </p>
     * 
     * @param propertyName The name of the property to retrieve.
     * @return The value set for the property or <tt>null</tt> if no value is
     *          set for the specified <tt>propertyName</tt>.
     */
    public Object getProperty(String propertyName);

    /**
     * Applications SHOULD call this method when the TopicMapSystem instance is 
     * no longer required. 
     * <p>
     * Once the TopicMapSystem instance is closed, the TopicMapSystem and any 
     * object retrieved from or created in this TopicMapSystem MUST NOT be used
     * by the application.
     * </p>
     * 
     * An implementation of the TopicMapSystem interface may use this method to
     * clean up any resources used by the implementation.
     */
    public void close();

}
