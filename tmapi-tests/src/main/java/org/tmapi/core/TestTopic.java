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
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests against the {@link Topic} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 89 $ - $Date: 2008-11-13 18:44:41 +0000 (Thu, 13 Nov 2008) $
 */
public class TestTopic extends TMAPITestCase {

    /**
     * Tests the parent/child relationship between topic map and topic.
     * @throws Exception If an error occurs.
     */
    @Test
    public void testParent() throws Exception {
        final TopicMap parent = createTopicMap("http://www.tmapi.org/test/topic/parent");
        Assert.assertTrue("Expected new topic maps to be created with no topics",
                    parent.getTopics().isEmpty());
        final Topic topic = parent.createTopic();
        Assert.assertEquals("Unexpected topic parent after creation",
                parent, topic.getParent());
        Assert.assertEquals("Expected topic set size to increment for topic map",
                    1, parent.getTopics().size());
        Assert.assertTrue("Topic is not part of getTopics()",
                    parent.getTopics().contains(topic));
        topic.remove();
        Assert.assertTrue("Expected topic set size to decrement for topic map.",
                    parent.getTopics().isEmpty());
    }

    @Test
    public void testAddSubjectIdentifierIllegal() {
        Topic topic = createTopic();
        try {
            topic.addSubjectIdentifier(null);
            Assert.fail("addSubjectIdentifier(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testAddSubjectLocatorIllegal() {
        Topic topic = createTopic();
        try {
            topic.addSubjectLocator(null);
            Assert.fail("addSubjectLocator(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testSubjectIdentifiers() {
        final Locator loc1 = createLocator("http://www.example.org/1");
        final Locator loc2 = createLocator("http://www.example.org/2");
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc1);
        Assert.assertEquals(1, topic.getSubjectIdentifiers().size());
        Assert.assertTrue(topic.getSubjectIdentifiers().contains(loc1));
        topic.addSubjectIdentifier(loc2);
        Assert.assertEquals(2, topic.getSubjectIdentifiers().size());
        Assert.assertTrue(topic.getSubjectIdentifiers().contains(loc2));
        topic.removeSubjectIdentifier(loc1);
        Assert.assertEquals(1, topic.getSubjectIdentifiers().size());
        Assert.assertTrue(topic.getSubjectIdentifiers().contains(loc2));
    }

    @Test
    public void testSubjectLocators() {
        final Locator loc1 = createLocator("http://www.example.org/1");
        final Locator loc2 = createLocator("http://www.example.org/2");
        final Topic topic = _tm.createTopicBySubjectLocator(loc1);
        Assert.assertEquals(1, topic.getSubjectLocators().size());
        Assert.assertTrue(topic.getSubjectLocators().contains(loc1));
        topic.addSubjectLocator(loc2);
        Assert.assertEquals(2, topic.getSubjectLocators().size());
        Assert.assertTrue(topic.getSubjectLocators().contains(loc2));
        topic.removeSubjectLocator(loc1);
        Assert.assertEquals(1, topic.getSubjectLocators().size());
        Assert.assertTrue(topic.getSubjectLocators().contains(loc2));
    }

    @Test
    public void testTopicTypes() {
        final Topic topic = createTopic();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        Assert.assertTrue(topic.getTypes().isEmpty());
        topic.addType(type1);
        Assert.assertEquals(1, topic.getTypes().size());
        Assert.assertTrue(topic.getTypes().contains(type1));
        topic.addType(type2);
        Assert.assertEquals(2, topic.getTypes().size());
        Assert.assertTrue(topic.getTypes().contains(type2));
        topic.removeType(type1);
        Assert.assertEquals(1, topic.getTypes().size());
        Assert.assertTrue(topic.getTypes().contains(type2));
        topic.removeType(type2);
        Assert.assertTrue(topic.getTypes().isEmpty());
    }

    @Test
    public void testAddTypeIllegal() {
        Topic topic = createTopic();
        try {
            topic.addType(null);
            Assert.fail("addType(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testRoleFilter() {
        final Topic player = createTopic();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        final Association assoc = createAssociation();
        Assert.assertEquals(0, player.getRolesPlayed(type1).size());
        Assert.assertEquals(0, player.getRolesPlayed(type2).size());
        Assert.assertEquals(0, player.getRolesPlayed(unusedType).size());
        final Role role = assoc.createRole(type1, player);
        Assert.assertEquals(1, player.getRolesPlayed(type1).size());
        Assert.assertTrue(player.getRolesPlayed(type1).contains(role));
        Assert.assertEquals(0, player.getRolesPlayed(type2).size());
        Assert.assertEquals(0, player.getRolesPlayed(unusedType).size());
        role.setType(type2);
        Assert.assertEquals(1, player.getRolesPlayed(type2).size());
        Assert.assertTrue(player.getRolesPlayed(type2).contains(role));
        Assert.assertEquals(0, player.getRolesPlayed(type1).size());
        Assert.assertEquals(0, player.getRolesPlayed(unusedType).size());
        role.remove();
        Assert.assertEquals(0, player.getRolesPlayed(type1).size());
        Assert.assertEquals(0, player.getRolesPlayed(type2).size());
        Assert.assertEquals(0, player.getRolesPlayed(unusedType).size());
    }

    @Test
    public void testRoleFilterIllegal() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(null);
            Assert.fail("topic.getRolesPlayed(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testRoleAssociationFilter() {
        final Topic player = createTopic();
        final Topic assocType1 = createTopic();
        final Topic assocType2 = createTopic();
        final Topic roleType1 = createTopic();
        final Topic roleType2 = createTopic();
        final Association assoc = _tm.createAssociation(assocType1);
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        final Role role1 = assoc.createRole(roleType1, player);
        Assert.assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        final Role role2 = assoc.createRole(roleType2, player);
        Assert.assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(1, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertTrue(player.getRolesPlayed(roleType2, assocType1).contains(role2));
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        role2.setType(roleType1);
        Assert.assertEquals(2, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role1));
        Assert.assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role2));
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        role1.remove();
        Assert.assertEquals(1, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertTrue(player.getRolesPlayed(roleType1, assocType1).contains(role2));
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
        assoc.remove();
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType1, assocType2).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType1).size());
        Assert.assertEquals(0, player.getRolesPlayed(roleType2, assocType2).size());
    }

    @Test
    public void testRoleAssociationFilterIllegalAssociation() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(role.getType(), null);
            Assert.fail("topic.getRolesPlayed(type, null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testRoleAssociationFilterIllegalRole() {
        final Role role = createRole();
        final Topic player = role.getPlayer();
        try {
            player.getRolesPlayed(null, role.getParent().getType());
            Assert.fail("topic.getRolesPlayed(null, type) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceFilter() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        Assert.assertEquals(0, topic.getOccurrences(type).size());
        Assert.assertEquals(0, topic.getOccurrences(type2).size());
        Assert.assertEquals(0, topic.getOccurrences(unusedType).size());
        final Occurrence occ = topic.createOccurrence(type, "Occurrence");
        Assert.assertEquals(1, topic.getOccurrences(type).size());
        Assert.assertTrue(topic.getOccurrences(type).contains(occ));
        Assert.assertEquals(0, topic.getOccurrences(type2).size());
        Assert.assertEquals(0, topic.getOccurrences(unusedType).size());
        occ.setType(type2);
        Assert.assertEquals(1, topic.getOccurrences(type2).size());
        Assert.assertTrue(topic.getOccurrences(type2).contains(occ));
        Assert.assertEquals(0, topic.getOccurrences(type).size());
        Assert.assertEquals(0, topic.getOccurrences(unusedType).size());
        occ.remove();
        Assert.assertEquals(0, topic.getOccurrences(type).size());
        Assert.assertEquals(0, topic.getOccurrences(type2).size());
        Assert.assertEquals(0, topic.getOccurrences(unusedType).size());
    }

    @Test
    public void testOccurrenceFilterIllegal() {
        final Occurrence occ = createOccurrence();
        final Topic parent = occ.getParent();
        try {
            parent.getOccurrences(null);
            Assert.fail("topic.getOccurrences(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testNameFilter() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        Assert.assertEquals(0, topic.getNames(type).size());
        Assert.assertEquals(0, topic.getNames(type2).size());
        Assert.assertEquals(0, topic.getNames(unusedType).size());
        final Name occ = topic.createName(type, "Name");
        Assert.assertEquals(1, topic.getNames(type).size());
        Assert.assertTrue(topic.getNames(type).contains(occ));
        Assert.assertEquals(0, topic.getNames(type2).size());
        Assert.assertEquals(0, topic.getNames(unusedType).size());
        occ.setType(type2);
        Assert.assertEquals(1, topic.getNames(type2).size());
        Assert.assertTrue(topic.getNames(type2).contains(occ));
        Assert.assertEquals(0, topic.getNames(type).size());
        Assert.assertEquals(0, topic.getNames(unusedType).size());
        occ.remove();
        Assert.assertEquals(0, topic.getNames(type).size());
        Assert.assertEquals(0, topic.getNames(type2).size());
        Assert.assertEquals(0, topic.getNames(unusedType).size());
    }

    @Test
    public void testNameFilterIllegal() {
        final Name name = createName();
        final Topic parent = name.getParent();
        try {
            parent.getNames(null);
            Assert.fail("topic.getNames(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationTypeString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertTrue(occ.getScope().isEmpty());
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertTrue(occ.getScope().isEmpty());
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value.getReference(), occ.getValue());
        Assert.assertEquals(value, occ.locatorValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertTrue(occ.getScope().isEmpty());
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeArrayString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, theme1, theme2);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeArrayURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, theme1, theme2);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value.getReference(), occ.getValue());
        Assert.assertEquals(value, occ.locatorValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeArrayExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt, theme1, theme2);
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeCollectionString() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#string");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, Arrays.asList(theme1, theme2));
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeCollectionURI() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final Locator value = createLocator("http://www.example.org/");
        final Locator dt = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, Arrays.asList(theme1, theme2));
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value.getReference(), occ.getValue());
        Assert.assertEquals(value, occ.locatorValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeScopeCollectionExplicitDatatype() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Occurrence";
        final Locator dt = createLocator("http://www.example.org/datatype");
        Assert.assertEquals(0, topic.getOccurrences().size());
        final Occurrence occ = topic.createOccurrence(type, value, dt, Arrays.asList(theme1, theme2));
        Assert.assertEquals(1, topic.getOccurrences().size());
        Assert.assertTrue(topic.getOccurrences().contains(occ));
        Assert.assertEquals(2, occ.getScope().size());
        Assert.assertTrue(occ.getScope().contains(theme1));
        Assert.assertTrue(occ.getScope().contains(theme2));
        Assert.assertEquals(type, occ.getType());
        Assert.assertEquals(value, occ.getValue());
        Assert.assertEquals(dt, occ.getDatatype());
        Assert.assertTrue(occ.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testOccurrenceCreationTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), (String)null);
            Assert.fail("createOccurrence(topic, (String)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationTypeIllegalURI() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), (Locator)null);
            Assert.fail("createOccurrence(topic, (Locator)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationTypeIllegalDatatype() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Locator)null);
            Assert.fail("createOccurrence(topic, \"Occurrence\", (Locator)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationIllegalType() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(null, "Occurrence");
            Assert.fail("createOccurrence(null, \"Occurrence\" is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Topic[])null);
            Assert.fail("createOccurrence(topic, \"Occurrence\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testOccurrenceCreationTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createOccurrence(createTopic(), "Occurrence", (Collection<Topic>)null);
            Assert.fail("createOccurrence(topic, \"Occurrence\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationType() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Name";
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value);
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertTrue(name.getScope().isEmpty());
        Assert.assertEquals(type, name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testNameCreationTypeScopeCollection() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final Topic theme = createTopic();
        final String value = "Name";
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value, Collections.singleton(theme));
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertEquals(1, name.getScope().size());
        Assert.assertTrue(name.getScope().contains(theme));
        Assert.assertEquals(type, name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testNameCreationTypeScopeArray() {
        final Topic topic = createTopic();
        final Topic type = createTopic();
        final String value = "Name";
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(type, value, theme1, theme2);
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertEquals(2, name.getScope().size());
        Assert.assertTrue(name.getScope().contains(theme1));
        Assert.assertTrue(name.getScope().contains(theme2));
        Assert.assertEquals(type, name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
    }

    @Test
    public void testNameCreationDefaultType() {
        final Topic topic = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value);
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertTrue(name.getScope().isEmpty());
        Assert.assertNotNull(name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        Assert.assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    @Test
    public void testNameCreationDefaultTypeScopeCollection() {
        final Topic topic = createTopic();
        final Topic theme = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value, Collections.singleton(theme));
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertEquals(1, name.getScope().size());
        Assert.assertTrue(name.getScope().contains(theme));
        Assert.assertNotNull(name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        Assert.assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    @Test
    public void testNameCreationDefaultTypeScopeArray() {
        final Topic topic = createTopic();
        final Topic theme1 = createTopic();
        final Topic theme2 = createTopic();
        final String value = "Name";
        final Locator loc = createLocator("http://psi.topicmaps.org/iso13250/model/topic-name");
        Assert.assertTrue(topic.getNames().isEmpty());
        final Name name = topic.createName(value, theme1, theme2);
        Assert.assertEquals(1, topic.getNames().size());
        Assert.assertTrue(topic.getNames().contains(name));
        Assert.assertEquals(2, name.getScope().size());
        Assert.assertTrue(name.getScope().contains(theme1));
        Assert.assertTrue(name.getScope().contains(theme2));
        Assert.assertNotNull(name.getType());
        Assert.assertEquals(value, name.getValue());
        Assert.assertTrue(name.getItemIdentifiers().isEmpty());
        Topic type = name.getType();
        Assert.assertTrue(type.getSubjectIdentifiers().contains(loc));
    }

    @Test
    public void testNameCreationTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), (String)null);
            Assert.fail("createName(topic, null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), "Name", (Topic[])null);
            Assert.fail("createName(topic, \"Name\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createName(createTopic(), "Name", (Collection<Topic>)null);
            Assert.fail("createName(topic, \"Name\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationDefaultTypeIllegalString() {
        final Topic topic = createTopic();
        try {
            topic.createName((String)null);
            Assert.fail("createName(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationDefaultTypeIllegalScopeArray() {
        final Topic topic = createTopic();
        try {
            topic.createName("Name", (Topic[])null);
            Assert.fail("createName(\"Name\", (Topic[])null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testNameCreationDefaultTypeIllegalScopeCollection() {
        final Topic topic = createTopic();
        try {
            topic.createName("Name", (Collection<Topic>)null);
            Assert.fail("createName(\"Name\", (Collection<Topic>)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

}
