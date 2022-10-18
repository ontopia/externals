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
import org.junit.Assert;
import org.junit.Test;

/** 
 * Tests against the {@link Name} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 162 $ - $Date: 2010-02-28 10:10:07 +0000 (Sun, 28 Feb 2010) $
 */
public class TestName extends TMAPITestCase {

    /**
     * Tests the parent/child relationship between topic and name.
     */
    @Test
    public void testParent() {
        final Topic parent = createTopic();
        Assert.assertTrue("Expected new topics to be created with no names",
                    parent.getNames().isEmpty());
        final Name name = parent.createName("Name");
        Assert.assertEquals("Unexpected name parent after creation",
                parent, name.getParent());
        Assert.assertEquals("Expected name set size to increment for topic",
                    1, parent.getNames().size());
        Assert.assertTrue("Name is not part of getNames()",
                    parent.getNames().contains(name));
        name.remove();
        Assert.assertTrue("Expected name set size to decrement for topic.",
                    parent.getNames().isEmpty());
    }

    @Test
    public void testValue() {
        final String value1 = "TMAPI Name";
        final String value2 = "A name";
        final Name name = createName();
        name.setValue(value1);
        Assert.assertEquals(value1, name.getValue());
        name.setValue(value2);
        Assert.assertEquals(value2, name.getValue());
        try {
            name.setValue(null);
            Assert.fail("setValue(null) is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
        // Old value kept.
        Assert.assertEquals(value2, name.getValue());
    }

    @Test
    public void testVariantCreationString() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator xsdString = createLocator("http://www.w3.org/2001/XMLSchema#string");
        final Variant variant = name.createVariant("Variant", theme);
        Assert.assertEquals("Variant", variant.getValue());
        Assert.assertEquals(xsdString, variant.getDatatype());
        Assert.assertEquals(1, variant.getScope().size());
        Assert.assertTrue(variant.getScope().contains(theme));
    }

    @Test
    public void testVariantCreationURI() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator xsdAnyURI = createLocator("http://www.w3.org/2001/XMLSchema#anyURI");
        final Locator value = createLocator("http://www.example.org/");
        final Variant variant = name.createVariant(value, theme);
        Assert.assertEquals(value.getReference(), variant.getValue());
        Assert.assertEquals(value, variant.locatorValue());
        Assert.assertEquals(xsdAnyURI, variant.getDatatype());
        Assert.assertEquals(1, variant.getScope().size());
        Assert.assertTrue(variant.getScope().contains(theme));
    }

    @Test
    public void testVariantCreationExplicitDatatype() {
        final Name name = createName();
        final Topic theme = createTopic();
        final Locator dt = createLocator("http://www.example.org/datatype");
        final Variant variant = name.createVariant("Variant", dt, theme);
        Assert.assertEquals("Variant", variant.getValue());
        Assert.assertEquals(dt, variant.getDatatype());
        Assert.assertEquals(1, variant.getScope().size());
        Assert.assertTrue(variant.getScope().contains(theme));
    }

    @Test
    public void testVariantCreationIllegalString() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant((String)null, theme);
            Assert.fail("Creation of a variant with (String) null value is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalLocator() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant((Locator)null, theme);
            Assert.fail("Creation of a variant with (Locator) null value is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalDatatype() {
        final Name name = createName();
        final Topic theme = createTopic();
        try {
            name.createVariant("Variant", (Locator)null, theme);
            Assert.fail("Creation of a variant with datatype == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalScope() {
        final Name name = createName();
        final Topic theme = createTopic();
        name.addTheme(theme);
        Assert.assertEquals(1, name.getScope().size());
        Assert.assertTrue(name.getScope().contains(theme));
        try {
            name.createVariant("Variant", theme);
            Assert.fail("The variant would be in the same scope as the parent");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalEmptyScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant", Collections.<Topic>emptySet());
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalNullScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant", (Topic[])null);
            Assert.fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            name.createVariant("Variant");
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithLocatorIllegalEmptyScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"), Collections.<Topic>emptySet());
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithLocatorIllegalNullScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"), (Topic[])null);
            Assert.fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithLocatorIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            name.createVariant(createLocator("http://tmapi.org"));
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithDataTypeIllegalEmptyScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt, Collections.<Topic>emptySet());
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithDataTypeIllegalNullScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt, (Topic[])null);
            Assert.fail("Creation of a variant with a null scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testVariantCreationWithDataTypeIllegalEmptyArrayScope() {
        final Name name = createName();
        try {
            final Locator dt = createLocator("http://www.example.org/datatype");
            name.createVariant("Variant", dt);
            Assert.fail("Creation of a variant with an empty scope is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
