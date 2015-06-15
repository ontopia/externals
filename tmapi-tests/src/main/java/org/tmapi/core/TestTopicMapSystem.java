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

/** 
 * Tests against the {@link TopicMapSystem}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 175 $ - $Date: 2010-03-10 13:06:21 +0000 (Wed, 10 Mar 2010) $
 */
public class TestTopicMapSystem extends TMAPITestCase {

    public TestTopicMapSystem(String name) {
        super(name);
    }

    public void testLoad() {
        TopicMap tm = _sys.getTopicMap(_defaultLocator); 
        assertNotNull("TopicMap was not created", tm);
        assertNotNull("There is no identifier for TopicMap", tm.getId());
        assertEquals(_tm.getId(), tm.getId());
    }

    public void testSameLocator() {
        try {
            _sys.createTopicMap(_defaultLocator);
            fail("A topic map under the same IRI exists already.");
        }
        catch (TopicMapExistsException ex) {
            // noop.
        }
    }

    public void testSet() throws Exception {
        final String BASE = "http://www.tmapi.org/test-tm-system/";
        TopicMap tm1 = createTopicMap(BASE + "test1"); 
        TopicMap tm2 = createTopicMap(BASE + "test2"); 
        TopicMap tm3 = createTopicMap(BASE + "test3"); 
        assertNotNull(tm1);
        assertNotNull(tm2);
        assertNotNull(tm3);
    }

    public void testRemoveTopicMaps() throws Exception {
        final String BASE = "http://www.tmapi.org/test-tm-system-removal/";
        TopicMap tm1 = createTopicMap(BASE + "test1"); 
        TopicMap tm2 = createTopicMap(BASE + "test2"); 
        TopicMap tm3 = createTopicMap(BASE + "test3"); 
        assertNotNull(tm1);
        assertNotNull(tm2);
        assertNotNull(tm3);
        int tmcount = _sys.getLocators().size();
        removeTopicMap(tm3);
        assertEquals("Expected locator set size to decrement for the topic map sytem", 
                        tmcount-1, _sys.getLocators().size());
    }

    public void testLocatorCreation() {
        final String ref = "http://www.tmapi.org/";
        final Locator loc = _sys.createLocator(ref);
        assertEquals(ref, loc.getReference());
    }
    
    public void testTopicMapLocator() throws Exception {
        final String ref = "http://www.tmapi.org/";
        final Locator loc = _sys.createLocator(ref+"2");
        TopicMap tm = _sys.createTopicMap(ref);
        assertEquals(ref, tm.getLocator().getReference());
        assertEquals(tm, _sys.getTopicMap(ref));
        tm.close();
        tm = _sys.createTopicMap(loc);
        assertEquals(loc, tm.getLocator());
        assertEquals(tm, _sys.getTopicMap(loc));
        tm.close();
    }

}
