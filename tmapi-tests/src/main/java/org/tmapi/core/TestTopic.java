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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Tests against the {@link Topic} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 89 $ - $Date: 2008-11-13 18:44:41 +0000 (Thu, 13 Nov 2008) $
 */
public class TestTopic extends TMAPITestCase {

    public TestTopic(String name) {
        super(name);
    }

    /**
     * Tests the parent/child relationship between topic map and topic.
     * @throws Exception If an error occurs.
     */
    public void testParent() throws Exception {
        final TopicMap parent = createTopicMap("http://www.tmapi.org/test/topic/parent");
        assertTrue("Expected new topic maps to be created with no topics",
                    parent.getTopics().isEmpty());
        final Topic topic = parent.createTopic();
        assertEquals("Unexpected topic parent after creation",
                parent, topic.getParent());
        assertEquals("Expected topic set size to increment for topic map",
                    1, parent.getTopics().size());
        assertTrue("Topic is not part of getTopics()",
                    parent.getTopics().contains(topic));
        topic.remove();
        assertTrue("Expected topic set size to decrement for topic map.",
                    parent.getTopics().isEmpty());
    }

    public void testAddSubjectIdentifierIllegal() {
        Topic topic = createTopic();
        try {
            topic.addSubjectIdentifier(null);
            fail("addSubjectIdentifier(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAddSubjectLocatorIllegal() {
        Topic topic = createTopic();
        try {
            topic.addSubjectLocator(null);
            fail("addSubjectLocator(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testSubjectIdentifiers() {
        final Locator loc1 = createLocator("http://www.example.org/1");
        final Locator loc2 = createLocator("http://www.example.org/2");
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc1);
        assertEquals(1, topic.getSubjectIdentifiers().size());
        assertTrue(topic.getSubjectIdentifiers().contains(loc1));
        topic.addSubjectIdentifier(loc2);
        assertEquals(2, topic.getSubjectIdentifiers().size());
        assertTrue(topic.getSubjectIdentifiers().contains(loc2));
        topic.removeSubjectIdentifier(loc1);
        assertEquals(1, topic.getSubjectIdentifiers().size());
        assertTrue(topic.getSubjectIdentifiers().contains(loc2));
    }

    public void testSubjectLocators() {
        final Locator loc1 = createLocator("http://www.example.org/1");
        final Locator loc2 = createLocator("http://www.example.org/2");
        final Topic topic = _tm.createTopicBySubjectLocator(loc1);
        assertEquals(1, topic.getSubjectLocators().size());
        assertTrue(topic.getSubjectLocators().contains(loc1));
        topic.addSubjectLocator(loc2);
        assertEquals(2, topic.getSubjectLocators().size());
        assertTrue(topic.getSubjectLocators().contains(loc2));
        topic.removeSubjectLocator(loc1);
        assertEquals(1, topic.getSubjectLocators().size());
        assertTrue(topic.getSubjectLocators().contains(loc2));
    }

    public void testTopicTypes() {
        final Topic topic = createTopic();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        assertTrue(topic.getTypes().isEmpty());
        topic.addType(type1);
        assertEquals(1, topic.getTypes().size());
        assertTrue(topic.getTypes().contains(type1));
        topic.addType(type2);
        assertEquals(2, topic.getTypes().size());
        assertTrue(topic.getTypes().contains(type2));
        topic.removeType(type1);
        assertEquals(1, topic.getTypes().size());
        assertTrue(topic.getTypes().contains(type2));
        topic.removeType(type2);
        assertTrue(topic.getTypes().isEmpty());
    }

    public void testAddTypeIllegal() {
        Topic topic = createTopic();
        try {
            topic.addType(null);
            fail("addType(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testRoleFilter() {
        final Topic player = createTopic();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        final Association assoc = createAssociation();
        assertEquals(0, player.getRolesPlayed(type1).size());
        assertEquals(0, player.getRolesPlayed(type2).size());
        assertEquals(0, player.getRolesPlayed(unusedType).size());
        final Role role = assoc.createRole(type1, player);
        assertEquals(1, player.getRolesPlayed(type1).size());
        assertTrue(player.getRolesPlayed(type1).contains(role));
        assertEquals(0, player.getRolesPlayed(type2).size());
        assertEquals(0, player.getRolesPlayed(unusedType).size());
        role.setType(type2);
        assertEquals(1, player.getRolesPlayed(type2).size());
        assertTrue(player.getRolesPlayed(type2).contains(role));
        assertEquals(0, player.getRolesPlayed(type1).size());
        assertEquals(0, player.getRolesPlayed(unusedType).size());
        role.remove();
        assertEquals(0, player.getRolesPlayed(type1).size());
        assertEquals(0, player.getRolesPlayed(type2).size());
        assertEquals(0, player.getRolesPlayed(unusedType).size());
    }

    public void testRoleFilterIllegal() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(null);
            fail("topic.getRolesPlayed(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testRoleAssociationFilter() {
        final Topic player = createTopic();
        final Topic assocType1 = createTopic();
        final Topic assocType2 = createTopic();
        final Topic roleType1 = createTopic();
        final Topic roleType2 = createTopic();
        final Association assoc = _tm.createAssociation(assocType1);
        assertEquals(0, player.getRolesPlayed(roleType1, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        final Role role1 = assoc.createRole(roleType1, player);
        assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        final Role role2 = assoc.createRole(roleType2, player);
        assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(1, player.getRolesPlayed(roleType2, assocType1).size());
        assertTrue(player.getRolesPlayed(roleType2, assocType1).contains(role2));
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        role2.setType(roleType1);
        assertEquals(2, player.getRolesPlayed(roleType1, assocType1).size());
        assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role2));
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        role1.remove();
        assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role2));
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        assoc.remove();
        assertEquals(0, player.getRolesPlayed(roleType1, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
    }

    public void testRoleAssociationFilterIllegalAssociation() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(role.getType(), null);
            fail("topic.getRolesPlayed(type, null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testRoleAssociationFilterIllegalRole() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(null, role.getParent().getType());
            fail("topic.getRolesPlayed(null, type) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrenceFilter() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        assertEquals(0, topic.getOccurrences(type).size());
        assertEquals(0, topic.getOccurrences(type2).size());
        assertEquals(0, topic.getOccurrences(unusedType).size());
        final Occurrence occ = topic.createOccurrence(type, "Occurrence");
        assertEquals(1, topic.getOccurrences(type).size());
        assertTrue(topic.getOccurrences(type).contains(occ));
        assertEquals(0, topic.getOccurrences(type2).size());
        assertEquals(0, topic.getOccurrences(unusedType).size());
        occ.setType(type2);
        assertEquals(1, topic.getOccurrences(type2).size());
        assertTrue(topic.getOccurrences(type2).contains(occ));
        assertEquals(0, topic.getOccurrences(type).size());
        assertEquals(0, topic.getOccurrences(unusedType).size());
        occ.remove();
        assertEquals(0, topic.getOccurrences(type).size());
        assertEquals(0, topic.getOccurrences(type2).size());
        assertEquals(0, topic.getOccurrences(unusedType).size());
    }

    public void testOccurrenceFilterIllegal() {
        final Occurrence occ = createOccurrence();
        final Topic parent = occ.getParent();
        try {
            parent.getOccurrences(null);
            fail("topic.getOccurrences(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testNameFilter() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        assertEquals(0, topic.getNames(type).size());
        assertEquals(0, topic.getNames(type2).size());
        assertEquals(0, topic.getNames(unusedType).size());
        final Name occ = topic.createName(type, "Name");
        assertEquals(1, topic.getNames(type).size());
        assertTrue(topic.getNames(type).contains(occ));
        assertEquals(0, topic.getNames(type2).size());
        assertEquals(0, topic.getNames(unusedType).size());
        occ.setType(type2);
        assertEquals(1, topic.getNames(type2).size());
        assertTrue(topic.getNames(type2).contains(occ));
        assertEquals(0, topic.getNames(type).size());
        assertEquals(0, topic.getNames(unusedType).size());
        occ.remove();
        assertEquals(0, topic.getNames(type).size());
        assertEquals(0, topic.getNames(type2).size());
        assertEquals(0, topic.getNames(unusedType).size());
    }

    public void testNameFilterIllegal() {
        final Name name = createName();
        final Topic parent = name.getParent();
        try {
            parent.getNames(null);
            fail("topic.getNames(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationTypeString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertTrue(occ.getScope().isEmpty());
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertTrue(occ.getScope().isEmpty());
        assertEquals(type, occ.getType());
        assertEquals(value.getReference(), occ.getValue());
        assertEquals(value, occ.locatorValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertTrue(occ.getScope().isEmpty());
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeArrayString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, theme1, theme2);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeArrayURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, theme1, theme2);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value.getReference(), occ.getValue());
        assertEquals(value, occ.locatorValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeArrayExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt, theme1, theme2);
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeCollectionString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, Arrays.asList(theme1, theme2));
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeCollectionURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, Arrays.asList(theme1, theme2));
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value.getReference(), occ.getValue());
        assertEquals(value, occ.locatorValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeScopeCollectionExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt, Arrays.asList(theme1, theme2));
        assertEquals(1, topic.getOccurrences().size());
        assertTrue(topic.getOccurrences().contains(occ));
        assertEquals(2, occ.getScope().size());
        assertTrue(occ.getScope().contains(theme1));
        assertTrue(occ.getScope().contains(theme2));
        assertEquals(type, occ.getType());
        assertEquals(value, occ.getValue());
        assertEquals(dt, occ.getDatatype());
        assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    public void testOccurrenceCreationTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), (String)null);
            fail("createOccurrence(topic, (String)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationTypeIllegalURI() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), (Locator)null);
            fail("createOccurrence(topic, (Locator)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationTypeIllegalDatatype() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Locator)null);
            fail("createOccurrence(topic, \"Occurrence\", (Locator)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationIllegalType() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(null, "Occurrence");
            fail("createOccurrence(null, \"Occurrence\" is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Topic[])null);
            fail("createOccurrence(topic, \"Occurrence\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Collection<Topic>)null);
            fail("createOccurrence(topic, \"Occurrence\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationType() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Name";
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value);
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertTrue(name.getScope().isEmpty());
        assertEquals(type, name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
    }

    public void testNameCreationTypeScopeCollection() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme = createTopic();
        final String value = "Name";
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value, Collections.singleton(theme));
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(theme));
        assertEquals(type, name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
    }

    public void testNameCreationTypeScopeArray() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Name";
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value, theme1, theme2);
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertEquals(2, name.getScope().size());
        assertTrue(name.getScope().contains(theme1));
        assertTrue(name.getScope().contains(theme2));
        assertEquals(type, name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
    }

    public void testNameCreationDefaultType() {
        final Topic topic = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value);
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertTrue(name.getScope().isEmpty());
        assertNotNull(name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    public void testNameCreationDefaultTypeScopeCollection() {
        final Topic topic = createTopic();
        final Topic theme = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value, Collections.singleton(theme));
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(theme));
        assertNotNull(name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    public void testNameCreationDefaultTypeScopeArray() {
        final Topic topic = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value, theme1, theme2);
        assertEquals(1, topic.getNames().size());
        assertTrue(topic.getNames().contains(name));
        assertEquals(2, name.getScope().size());
        assertTrue(name.getScope().contains(theme1));
        assertTrue(name.getScope().contains(theme2));
        assertNotNull(name.getType());
        assertEquals(value, name.getValue());
        assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    public void testNameCreationTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), (String)null);
            fail("createName(topic, null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), "Name", (Topic[])null);
            fail("createName(topic, \"Name\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), "Name", (Collection<Topic>)null);
            fail("createName(topic, \"Name\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationDefaultTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createName((String)null);
            fail("createName(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationDefaultTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createName("Name", (Topic[])null);
            fail("createName(\"Name\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationDefaultTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createName("Name", (Collection<Topic>)null);
            fail("createName(\"Name\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

}
