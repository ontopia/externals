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
package org.tmapi.index;

import java.util.Collections;

import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.TMAPITestCase;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

/**
 * Tests against the {@link ScopedIndex} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public class TestScopedIndex extends TMAPITestCase {

    public TestScopedIndex(String name) {
        super(name);
    }

    private ScopedIndex _scopedIdx;

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _scopedIdx = _tm.getIndex(ScopedIndex.class);
        _scopedIdx.open();
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        _scopedIdx.close();
        _scopedIdx = null;
    }

    private void _updateIndex() {
        if (!_scopedIdx.isAutoUpdated()) {
            _scopedIdx.reindex();
        }
    }

    public void testAssociation() {
        Topic theme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getAssociations(null).isEmpty());
        assertTrue(_scopedIdx.getAssociations(theme).isEmpty());
        assertTrue(_scopedIdx.getAssociationThemes().isEmpty());
        Association scoped = createAssociation();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getAssociations(null).size());
        assertTrue(_scopedIdx.getAssociations(null).contains(scoped));
        assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(0, _scopedIdx.getAssociations(null).size());
        assertFalse(_scopedIdx.getAssociations(null).contains(scoped));
        assertFalse(_scopedIdx.getAssociationThemes().isEmpty());
        assertEquals(1, _scopedIdx.getAssociationThemes().size());
        assertTrue(_scopedIdx.getAssociations(theme).contains(scoped));
        assertTrue(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        assertEquals(0, _scopedIdx.getAssociations(null).size());
        assertFalse(_scopedIdx.getAssociations(null).contains(scoped));
        assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
    }

    public void testAssociationMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getAssociations(null).isEmpty());
        assertTrue(_scopedIdx.getAssociations(theme).isEmpty());
        assertTrue(_scopedIdx.getAssociationThemes().isEmpty());
        Association scoped = createAssociation();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getAssociations(null).size());
        assertTrue(_scopedIdx.getAssociations(null).contains(scoped));
        assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(1, _scopedIdx.getAssociationThemes().size());
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        assertEquals(2, _scopedIdx.getAssociationThemes().size());
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getAssociations(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        assertFalse(_scopedIdx.getAssociations(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testAssociationMatchAllIllegal() {
        try {
            _scopedIdx.getAssociations(null, true);
            fail("getAssociations(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrence() {
        Topic theme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getOccurrences(null).isEmpty());
        assertTrue(_scopedIdx.getOccurrences(theme).isEmpty());
        assertTrue(_scopedIdx.getOccurrenceThemes().isEmpty());
        Occurrence scoped = createOccurrence();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getOccurrences(null).size());
        assertTrue(_scopedIdx.getOccurrences(null).contains(scoped));
        assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(0, _scopedIdx.getOccurrences(null).size());
        assertFalse(_scopedIdx.getOccurrences(null).contains(scoped));
        assertFalse(_scopedIdx.getOccurrenceThemes().isEmpty());
        assertEquals(1, _scopedIdx.getOccurrenceThemes().size());
        assertTrue(_scopedIdx.getOccurrences(theme).contains(scoped));
        assertTrue(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        assertEquals(0, _scopedIdx.getOccurrences(null).size());
        assertFalse(_scopedIdx.getOccurrences(null).contains(scoped));
        assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
    }

    public void testOccurrenceMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getOccurrences(null).isEmpty());
        assertTrue(_scopedIdx.getOccurrences(theme).isEmpty());
        assertTrue(_scopedIdx.getOccurrenceThemes().isEmpty());
        final Occurrence scoped = createOccurrence();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getOccurrences(null).size());
        assertTrue(_scopedIdx.getOccurrences(null).contains(scoped));
        assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(1, _scopedIdx.getOccurrenceThemes().size());
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        assertEquals(2, _scopedIdx.getOccurrenceThemes().size());
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getOccurrences(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        assertFalse(_scopedIdx.getOccurrences(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testOccurrenceMatchAllIllegal() {
        try {
            _scopedIdx.getOccurrences(null, true);
            fail("getOccurrences(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testName() {
        Topic theme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getNames(null).isEmpty());
        assertTrue(_scopedIdx.getNames(theme).isEmpty());
        assertTrue(_scopedIdx.getNameThemes().isEmpty());
        Name scoped = createName();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getNames(null).size());
        assertTrue(_scopedIdx.getNames(null).contains(scoped));
        assertFalse(_scopedIdx.getNameThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(0, _scopedIdx.getNames(null).size());
        assertFalse(_scopedIdx.getNames(null).contains(scoped));
        assertFalse(_scopedIdx.getNameThemes().isEmpty());
        assertEquals(1, _scopedIdx.getNameThemes().size());
        assertTrue(_scopedIdx.getNames(theme).contains(scoped));
        assertTrue(_scopedIdx.getNameThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        assertEquals(0, _scopedIdx.getNames(null).size());
        assertFalse(_scopedIdx.getNames(null).contains(scoped));
        assertFalse(_scopedIdx.getNameThemes().contains(theme));
    }

    public void testName2() {
        Topic theme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getNames(null).isEmpty());
        assertTrue(_scopedIdx.getNames(theme).isEmpty());
        assertTrue(_scopedIdx.getNameThemes().isEmpty());
        Name scoped = createTopic().createName("tinyTiM", Collections.singleton(theme));
        assertEquals(1, scoped.getScope().size());
        _updateIndex();
        assertEquals(0, _scopedIdx.getNames(null).size());
        assertFalse(_scopedIdx.getNames(null).contains(scoped));
        assertFalse(_scopedIdx.getNameThemes().isEmpty());
        assertEquals(1, _scopedIdx.getNameThemes().size());
        assertTrue(_scopedIdx.getNames(theme).contains(scoped));
        assertTrue(_scopedIdx.getNameThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        assertEquals(0, _scopedIdx.getNames(null).size());
        assertFalse(_scopedIdx.getNames(null).contains(scoped));
        assertEquals(0, _scopedIdx.getNames(theme).size());
        assertFalse(_scopedIdx.getNameThemes().contains(theme));
    }

    public void testNameMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getNames(null).isEmpty());
        assertTrue(_scopedIdx.getNames(theme).isEmpty());
        assertTrue(_scopedIdx.getNameThemes().isEmpty());
        final Name scoped = createName();
        assertEquals(0, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getNames(null).size());
        assertTrue(_scopedIdx.getNames(null).contains(scoped));
        assertFalse(_scopedIdx.getNameThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        assertEquals(1, _scopedIdx.getNameThemes().size());
        assertTrue(_scopedIdx.getNames(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        assertEquals(2, _scopedIdx.getNameThemes().size());
        assertTrue(_scopedIdx.getNames(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getNames(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getNames(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        assertFalse(_scopedIdx.getNames(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testNameMatchAllIllegal() {
        try {
            _scopedIdx.getNames(null, true);
            fail("getNames(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantIllegal() {
        try {
            _scopedIdx.getVariants(null);
            fail("getVariants(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantMatchAllIllegal() {
        try {
            _scopedIdx.getVariants(null, true);
            fail("getVariants(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariant() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createName();
        assertEquals(0, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        assertEquals("Unexpected variant's scope size", 1, scoped.getScope().size());
        _updateIndex();
        assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        assertEquals("Unexpected number of variant themes", 
                        1, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        // Add theme to name
        name.addTheme(theme2);
        assertEquals(1, name.getScope().size());
        assertEquals("The scope change of the parent is not reflected in the variant's scope",
                        2, scoped.getScope().size());
        _updateIndex();
        assertEquals("Change of the parent's scope is not reflected in the index",
                    2, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Remove theme from name
        name.removeTheme(theme2);
        _updateIndex();
        assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        assertEquals("The scope change in the name is not reflected in variant",
                        1, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        scoped.addTheme(theme2);
        _updateIndex();
        assertEquals("Change of the variant's scope is not reflected in the index",
                        2, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Add theme to name
        name.addTheme(theme2);
        _updateIndex();
        assertEquals("Adding a theme to the variant's parent is not reflected",
                        2, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Remove theme from name
        name.removeTheme(theme2);
        _updateIndex();
        assertEquals("Removing the name's theme MUST NOT be reflected in the variant's scope",
                        2, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        scoped.removeTheme(theme2);
        assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        assertEquals(1, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
    }

    public void testVariant2() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name", theme2);
        assertEquals(1, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        assertEquals(2, scoped.getScope().size());
        _updateIndex();
        assertEquals(2, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        name.removeTheme(theme2);
        assertEquals(0, name.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        assertTrue(_scopedIdx.getVariantThemes().contains(theme));
    }

    public void testVariantMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name");
        assertEquals(0, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        assertEquals(1, scoped.getScope().size());
        _updateIndex();
        assertEquals(1, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme}, true).contains(scoped));
        final Topic nameTheme = createTopic();
        name.addTheme(nameTheme);
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, true).contains(scoped));
        name.removeTheme(nameTheme);
        _updateIndex();
        assertFalse(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        scoped.removeTheme(theme);
        _updateIndex();
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
    }

    public void testVariantMatchAll2() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        final Topic nameTheme = createTopic();
        _updateIndex();
        assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name", nameTheme);
        assertEquals(1, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme, theme2);
        assertEquals(3, scoped.getScope().size());
        _updateIndex();
        assertEquals(3, _scopedIdx.getVariantThemes().size());
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, false).contains(scoped));
        assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, true).contains(scoped));
    }

}
