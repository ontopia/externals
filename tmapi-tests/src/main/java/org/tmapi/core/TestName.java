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

import java.util.Collections;

/** 
 * Tests against the {@link Name} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 162 $ - $Date: 2010-02-28 10:10:07 +0000 (Sun, 28 Feb 2010) $
 */
public class TestName extends TMAPITestCase {

    public TestName(String name) {
        super(name);
    }

    /**
     * Tests the parent/child relationship between topic and name.
     */
    public void testParent() {
        final Topic parent = createTopic();
        assertTrue("Expected new topics to be created with no names",
                    parent.getNames().isEmpty());
        final Name name = parent.createName("Name");
        assertEquals("Unexpected name parent after creation",
                parent, name.getParent());
        assertEquals("Expected name set size to increment for topic",
                    1, parent.getNames().size());
        assertTrue("Name is not part of getNames()",
                    parent.getNames().contains(name));
        name.remove();
        assertTrue("Expected name set size to decrement for topic.",
                    parent.getNames().isEmpty());
    }

    public void testValue() {
        final String value1 = "TMAPI Name";
        final String value2 = "A name";
        final Name name = createName();
        name.setValue(value1);
        assertEquals(value1, name.getValue());
        name.setValue(value2);
        assertEquals(value2, name.getValue());
        try {
            name.setValue(null);
            fail("setValue(null) is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
        // Old value kept.
        assertEquals(value2, name.getValue());
    }

    public void testVariantCreationString() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator xsdString = createLocator("http://www.w3.org/2001/XMLSchema#string");
        final Variant variant = name.createVariant("Variant", theme);
        assertEquals("Variant", variant.getValue());
        assertEquals(xsdString, variant.getDatatype());
        assertEquals(1, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
    }

    public void testVariantCreationURI() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator xsdAnyURI = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        final Locator value = createLocator("http://www.example.org/");
        final Variant variant = name.createVariant(value, theme);
        assertEquals(value.getReference(), variant.getValue());
        assertEquals(value, variant.locatorValue());
        assertEquals(xsdAnyURI, variant.getDatatype());
        assertEquals(1, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
    }

    public void testVariantCreationExplicitDatatype() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator dt = createLocator("http://www.example.org/datatype");
        final Variant variant = name.createVariant("Variant", dt, theme);
        assertEquals("Variant", variant.getValue());
        assertEquals(dt, variant.getDatatype());
        assertEquals(1, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
    }

    public void testVariantCreationIllegalString() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant((String)null, theme);
            fail("Creation of a variant with (String) null value is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalLocator() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant((Locator)null, theme);
            fail("Creation of a variant with (Locator) null value is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalDatatype() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant("Variant", (Locator)null, theme);
            fail("Creation of a variant with datatype == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalScope() {
        final Name name = createName();
        final Topic theme = createTopic();
        name.addTheme(theme);
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(theme));
        try {
            name.createVariant("Variant", theme);
            fail("The variant would be in the same scope as the parent");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalEmptyScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant", Collections.<Topic>emptySet());
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalNullScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant", (Topic[])null);
            fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant");
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithLocatorIllegalEmptyScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"), Collections.<Topic>emptySet());
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithLocatorIllegalNullScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"), (Topic[])null);
            fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithLocatorIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"));
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithDataTypeIllegalEmptyScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt, Collections.<Topic>emptySet());
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithDataTypeIllegalNullScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt, (Topic[])null);
            fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testVariantCreationWithDataTypeIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt);
            fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
