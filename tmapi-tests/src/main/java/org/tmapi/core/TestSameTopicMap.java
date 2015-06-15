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

import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Reifiable;
import org.tmapi.core.Scoped;
import org.tmapi.core.TopicMap;
import org.tmapi.core.Typed;

/**
 * Checks the "same topic map" constraint.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 110 $ - $Date: 2009-06-30 16:18:20 +0000 (Tue, 30 Jun 2009) $
 */
public class TestSameTopicMap extends TMAPITestCase {

    private TopicMap _tm2;

    public TestSameTopicMap(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see org.tinytim.core.TinyTimTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _tm2 = _sys.createTopicMap("http://www.tmapi.org/same-topicmap");
    }

    public void testAssociationCreationIllegalType() {
        try {
            _tm.createAssociation(_tm2.createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationCreationIllegalScopeArray() {
        try {
            _tm.createAssociation(createTopic(), createTopic(), _tm2.createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationCreationIllegalScopeCollection() {
        try {
            _tm.createAssociation(createTopic(), Arrays.asList(createTopic(), _tm2.createTopic()));
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationIllegalType() {
        try {
            createTopic().createName(_tm2.createTopic(), "value");
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationIllegalScopeArray() {
        try {
            createTopic().createName(createTopic(), "value", createTopic(), _tm2.createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testNameCreationIllegalScopeCollection() {
        try {
            createTopic().createName(createTopic(), "value", Arrays.asList(createTopic(), _tm2.createTopic()));
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationIllegalType() {
        try {
            createTopic().createOccurrence(_tm2.createTopic(), "value");
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationIllegalScopeArray() {
        try {
            createTopic().createOccurrence(createTopic(), "value", createTopic(), _tm2.createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testOccurrenceCreationIllegalScopeCollection() {
        try {
            createTopic().createOccurrence(createTopic(), "value", Arrays.asList(createTopic(), _tm2.createTopic()));
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testRoleCreationIllegalType() {
        try {
            createAssociation().createRole(_tm2.createTopic(), createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testRoleCreationIllegalPlayer() {
        try {
            createAssociation().createRole(createTopic(), _tm2.createTopic());
            fail("Expected a model contraint violation");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
    
    private void _testIllegalTheme(Scoped scoped) {
        try {
            scoped.addTheme(_tm2.createTopic());
            fail("Adding a theme from another topic map shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationIllegalTheme() {
        _testIllegalTheme(createAssociation());
    }

    public void testOccurrenceIllegalTheme() {
        _testIllegalTheme(createOccurrence());
    }

    public void testNameIllegalTheme() {
        _testIllegalTheme(createName());
    }

    public void testVariantIllegalTheme() {
        _testIllegalTheme(createVariant());
    }

    private void _testIllegalType(Typed typed) {
        try {
            typed.setType(_tm2.createTopic());
            fail("Setting the type to a topic from another topic map shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationIllegalType() {
        _testIllegalType(createAssociation());
    }
    
    public void testRoleIllegalType() {
        _testIllegalType(createRole());
    }
    
    public void testOccurrenceIllegalType() {
        _testIllegalType(createOccurrence());
    }

    public void testNameIllegalType() {
        _testIllegalType(createName());
    }

    public void testRoleIllegalPlayer() {
        try {
            createRole().setPlayer(_tm2.createTopic());
            fail("Setting the player to a topic of another topic map shouldn't be allowed.");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    private void _testIllegalReifier(Reifiable reifiable) {
        try {
            reifiable.setReifier(_tm2.createTopic());
            fail("Setting the reifier to a topic of another topic map shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
    
    public void testTopicMapIllegalReifier() {
        _testIllegalReifier(_tm);
    }

    public void testAssociationIllegalReifier() {
        _testIllegalReifier(createAssociation());
    }

    public void testRoleIllegalReifier() {
        _testIllegalReifier(createRole());
    }

    public void testOccurrenceIllegalReifier() {
        _testIllegalReifier(createOccurrence());
    }

    public void testNameIllegalReifier() {
        _testIllegalReifier(createName());
    }

    public void testVariantIllegalReifier() {
        _testIllegalReifier(createVariant());
    }

    public void testIllegalTopicType() {
        try {
            createTopic().addType(_tm2.createTopic());
            fail("The type is not from the same topic map. Disallowed.");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
