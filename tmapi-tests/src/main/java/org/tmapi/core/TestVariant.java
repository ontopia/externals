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
 * Tests against the {@link Variant} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public class TestVariant extends AbstractTestDatatypeAware {

    public TestVariant(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TestDatatypeAware#getDatatypeAware()
     */
    @Override
    protected DatatypeAware getDatatypeAware() {
        return createVariant();
    }

    /**
     * Tests the parent/child relationship between name and variant.
     */
    public void testParent() {
        final Name parent = createName();
        assertTrue("Expected new name to be created with no variants",
                    parent.getVariants().isEmpty());
        final Variant variant = parent.createVariant("Variant", createTopic());
        assertEquals("Unexpected variant parent after creation",
                parent, variant.getParent());
        assertEquals("Expected variant set size to increment for name",
                    1, parent.getVariants().size());
        assertTrue("Variant is not part of getVariants()",
                    parent.getVariants().contains(variant));
        variant.remove();
        assertTrue("Expected variant set size to decrement for name.",
                    parent.getVariants().isEmpty());
    }

    /**
     * Tests if the variant's scope contains the name's scope.
     */
    public void testScopeProperty() {
        final Name name = createName();
        assertTrue(name.getScope().isEmpty());
        final Topic varTheme = createTopic();
        final Variant variant = name.createVariant("Variant", varTheme);
        assertNotNull(variant);
        assertEquals("Unexpected variant's scope", 
                        1, variant.getScope().size());
        assertTrue(variant.getScope().contains(varTheme));
        final Topic nameTheme = createTopic();
        name.addTheme(nameTheme);
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(nameTheme));
        assertEquals(2, variant.getScope().size());
        assertTrue(variant.getScope().contains(nameTheme));
        assertTrue(variant.getScope().contains(varTheme));
        name.removeTheme(nameTheme);
        assertTrue(name.getScope().isEmpty());
        assertEquals("Name's theme wasn't remove from the variant",
                        1, variant.getScope().size());
        assertTrue(variant.getScope().contains(varTheme));
    }

    /**
     * Tests if a variant's theme equals to a name's theme stays 
     * even if the name's theme is removed.
     */
    public void testScopeProperty2() {
        final Topic theme = createTopic();
        final Topic varTheme = createTopic();
        final Name name = createTopic().createName("Name", theme);
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(theme));
        final Variant variant = name.createVariant("Variant", theme, varTheme);
        assertNotNull(variant);
        assertEquals("Unexpected variant's scope", 
                        2, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
        assertTrue(variant.getScope().contains(varTheme));
        name.removeTheme(theme);
        assertEquals(0, name.getScope().size());
        assertEquals("Unexpected variant's scope after removal of 'theme' from name", 
                        2, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
        assertTrue(variant.getScope().contains(varTheme));
    }

    /**
     * Tests if a variant's theme equals to a name's theme stays 
     * even if the variant's theme is removed.
     */
    public void testScopeProperty3() {
        final Topic theme = createTopic();
        final Topic varTheme = createTopic();
        final Name name = createTopic().createName("Name", theme);
        assertEquals(1, name.getScope().size());
        assertTrue(name.getScope().contains(theme));
        final Variant variant = name.createVariant("Variant", theme, varTheme);
        assertNotNull(variant);
        assertEquals("Unexpected variant's scope", 
                        2, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
        assertTrue(variant.getScope().contains(varTheme));
        variant.removeTheme(theme);
        assertEquals("The parent still contains 'theme'", 
                        2, variant.getScope().size());
        assertTrue(variant.getScope().contains(theme));
        assertTrue(variant.getScope().contains(varTheme));
        name.removeTheme(theme);
        assertEquals(0, name.getScope().size());
        assertEquals("'theme' was removed from the name", 
                        1, variant.getScope().size());
        assertFalse(variant.getScope().contains(theme));
        assertTrue(variant.getScope().contains(varTheme));
    }

}
