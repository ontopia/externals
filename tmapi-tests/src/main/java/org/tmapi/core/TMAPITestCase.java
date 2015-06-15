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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * This TestClass is the base of all TMAPI test classes. 
 * 
 * It uses {@link TopicMapSystemFactory} in {@link #setUp()} method.
 * So, the Property "org.tmapi.core.TopicMapSystemFactory" has to be set
 * to the implementing factory class.
 *
 * Please use also the {@link #createTopicMap(Locator)}, {@link #createTopicMap(String)},
 * and {@link #removeTopicMap(Locator)}, {@link #removeTopicMap(String)} 
 * methods. 
 * So after running the test the {@link #teardown()} method removes all created 
 * {@link TopicMap} instances. 
 * That needed for TMAPI implementations which are working with persistent 
 * backends.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public class TMAPITestCase extends TestCase {

    /**
     * Default address under which the initial topic map is stored.
     */
    protected static final String _DEFAULT_ADDRESS = "http://www.tmapi.org/tmapi2.0";

    /**
     * Locator representing the <tt>_DEFAUL_ADDRESS</tt>.
     */
    protected Locator _defaultLocator;

    /**
     * Default TopicMapSystem instance created during {@link #setUp()}
     */
    protected TopicMapSystem _sys;

    /**
     * Default topic map which is created during {@link #setUp()}
     */
    protected TopicMap _tm;

    public TMAPITestCase(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        //bad hack to copy all System.Properties to factory
        for (Object obj: System.getProperties().keySet()) {
            String key = (String) obj;
            factory.setProperty(key, System.getProperty(key));
        }
        _sys = factory.newTopicMapSystem();
        removeAllMaps(); // Seems to be unnecessary, but who knows
        _defaultLocator = _sys.createLocator(_DEFAULT_ADDRESS);
        _tm = _sys.createTopicMap(_defaultLocator);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        removeAllMaps();
        _sys.close();
    }

    /**
     * Creates a topic with a random item identifier.
     *
     * @return The topic.
     */
    protected Topic createTopic() {
        return _tm.createTopic();
    }

    /**
     * Creates an association with a random type and no roles.
     *
     * @return The association.
     */
    protected Association createAssociation() {
        return _tm.createAssociation(createTopic());
    }

    /**
     * Creates a role which is part of a random association with a random
     * player and type.
     *
     * @return The role.
     */
    protected Role createRole() {
        return createAssociation().createRole(createTopic(), createTopic());
    }

    /**
     * Creates an occurrence which is part of a random topic with a random type.
     *
     * @return The occurrence.
     */
    protected Occurrence createOccurrence() {
        return createTopic().createOccurrence(createTopic(), "Occurrence");
    }

    /**
     * Creates a name which is part of a newly created topic using the default
     * type name.
     *
     * @return The name.
     */
    protected Name createName() {
        return createTopic().createName("Name");
    }

    /**
     * Creates a variant which is part of a newly created name.
     *
     * @return The variant.
     */
    protected Variant createVariant() {
        return createName().createVariant("Variant", createTopic());
    }

    protected Locator createLocator(final String iri) {
        return _sys.createLocator(iri);
    }

    /**
     * Creates a topic map under the specified <tt>iri</tt>.
     *
     * @param iri The IRI where the topic map should be stored.
     * @return A topic map instance.
     * @throws TopicMapExistsException If a topic map under the IRI exists already.
     */
    protected TopicMap createTopicMap(String iri) throws TopicMapExistsException {
        return createTopicMap(_sys.createLocator(iri));
    }

    /**
     * Creates a topic map under the specified <tt>locator</tt>.
     *
     * @param locator The locator under which the topic map should be stored.
     * @return A topic map instance.
     * @throws TopicMapExistsException If a topic map under the IRI exists already.
     */
    protected TopicMap createTopicMap(Locator locator) throws TopicMapExistsException {
        return _sys.createTopicMap(locator);
    }

    /**
     * Removes a topic map stored at <tt>iri</tt> from this system.
     *
     * @param iri The IRI where the topic map is stored.
     */
    protected void removeTopicMap(String iri) {
        removeTopicMap(createLocator(iri));
    }

    /**
     * Removes a topic map stored at <tt>locator</tt> from this system.
     *
     * @param locator The IRI where the topic map is stored.
     */
    protected void removeTopicMap(Locator locator) {
        removeTopicMap(_sys.getTopicMap(locator));
    }

    /**
     * Removes a topic map.
     *
     * @param tm The topic map to remove
     */
    protected void removeTopicMap(TopicMap tm) {
        tm.remove();
    }

    /**
     * Deletes all topic maps known in the system.
     */
    protected void removeAllMaps() {
        List<Locator> locs = new ArrayList<Locator>();
        locs.addAll(_sys.getLocators());
        for (Locator loc: locs) {
            removeTopicMap(loc);
        }
    }

}
