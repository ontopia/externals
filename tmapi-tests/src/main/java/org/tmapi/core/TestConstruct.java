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
 * Tests against the {@link Construct} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestConstruct extends TMAPITestCase {

    public TestConstruct(String name) {
        super(name);
    }

    /**
     * Tests adding / removing item identifiers, retrieval by item identifier 
     * and retrieval by the system specific id.
     *
     * @param construct The Topic Maps construct to test.
     */
    protected void _testConstruct(final Construct construct) {
        assertEquals("Unexpected item identifiers", 0, construct.getItemIdentifiers().size());
        final Locator iid = _tm.createLocator("http://www.tmapi.org/test#test");
        construct.addItemIdentifier(iid);
        assertEquals("Expected a item identifier", 1, construct.getItemIdentifiers().size());
        assertTrue("Unexpected item identifier in item identifier property",
                    construct.getItemIdentifiers().contains(iid));
        assertEquals("Unexpected construct retrieved", 
                        construct, _tm.getConstructByItemIdentifier(iid));
        construct.removeItemIdentifier(iid);
        assertEquals("Item identifier was not removed", 
                        0, construct.getItemIdentifiers().size());
        assertFalse(construct.getItemIdentifiers().contains(iid));
        assertNull("Got an construct even if the item identifier is unassigned",
                    _tm.getConstructByItemIdentifier(iid));
        try {
            construct.addItemIdentifier(null);
            fail("addItemIdentifier(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
        if (construct instanceof TopicMap) {
            assertNull(construct.getParent());
        }
        else {
            assertNotNull(construct.getParent());
        }
        assertEquals(_tm, construct.getTopicMap());
        final String id = construct.getId();
        assertEquals("Unexpected result", construct, _tm.getConstructById(id));
    }

    /**
     * Tests against a topic map.
     */
    public void testTopicMap() {
        _testConstruct(_tm);
    }

    /**
     * Tests against a topic. 
     */
    public void testTopic() {
        // Avoid that the topic has an item identifier
        Topic topic = _tm.createTopicBySubjectLocator(_tm.createLocator("http://www.tmapi.org/"));
        _testConstruct(topic);
    }

    /**
     * Tests against an association.
     */
    public void testAssociation() {
        _testConstruct(createAssociation());
    }

    /**
     * Tests against a role.
     */
    public void testRole() {
        _testConstruct(createRole());
    }

    /**
     * Tests against an occurrence.
     */
    public void testOccurrence() {
        _testConstruct(createOccurrence());
    }

    /**
     * Test against a name.
     */
    public void testName() {
        _testConstruct(createName());
    }

    /**
     * Tests against a variant.
     */
    public void testVariant() {
        _testConstruct(createVariant());
    }

}
