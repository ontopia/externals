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

import org.junit.Assert;
import org.junit.Test;

/** 
 * Tests against the {@link TopicMapSystem}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 175 $ - $Date: 2010-03-10 13:06:21 +0000 (Wed, 10 Mar 2010) $
 */
public class TestTopicMapSystem extends TMAPITestCase {

    @Test
    public void testLoad() {
        TopicMap tm = _sys.getTopicMap(_defaultLocator); 
        Assert.assertNotNull("TopicMap was not created", tm);
        Assert.assertNotNull("There is no identifier for TopicMap", tm.getId());
        Assert.assertEquals(_tm.getId(), tm.getId());
    }

    @Test
    public void testSameLocator() {
        try {
            _sys.createTopicMap(_defaultLocator);
            Assert.fail("A topic map under the same IRI exists already.");
        }
        catch (TopicMapExistsException ex) {
            // noop.
        }
    }

    @Test
    public void testSet() throws Exception {
        final String BASE = "http://www.tmapi.org/test-tm-system/";
        TopicMap tm1 = createTopicMap(BASE + "test1"); 
        TopicMap tm2 = createTopicMap(BASE + "test2"); 
        TopicMap tm3 = createTopicMap(BASE + "test3"); 
        Assert.assertNotNull(tm1);
        Assert.assertNotNull(tm2);
        Assert.assertNotNull(tm3);
    }

    @Test
    public void testRemoveTopicMaps() throws Exception {
        final String BASE = "http://www.tmapi.org/test-tm-system-removal/";
        TopicMap tm1 = createTopicMap(BASE + "test1"); 
        TopicMap tm2 = createTopicMap(BASE + "test2"); 
        TopicMap tm3 = createTopicMap(BASE + "test3"); 
        Assert.assertNotNull(tm1);
        Assert.assertNotNull(tm2);
        Assert.assertNotNull(tm3);
        int tmcount = _sys.getLocators().size();
        removeTopicMap(tm3);
        Assert.assertEquals("Expected locator set size to decrement for the topic map sytem", 
                        tmcount-1, _sys.getLocators().size());
    }

    @Test
    public void testLocatorCreation() {
        final String ref = "http://www.tmapi.org/";
        final Locator loc = _sys.createLocator(ref);
        Assert.assertEquals(ref, loc.getReference());
    }
    
    @Test
    public void testTopicMapLocator() throws Exception {
        final String ref = "http://www.tmapi.org/";
        final Locator loc = _sys.createLocator(ref+"2");
        TopicMap tm = _sys.createTopicMap(ref);
        Assert.assertEquals(ref, tm.getLocator().getReference());
        Assert.assertEquals(tm, _sys.getTopicMap(ref));
        tm.close();
        tm = _sys.createTopicMap(loc);
        Assert.assertEquals(loc, tm.getLocator());
        Assert.assertEquals(tm, _sys.getTopicMap(loc));
        tm.close();
    }

}
