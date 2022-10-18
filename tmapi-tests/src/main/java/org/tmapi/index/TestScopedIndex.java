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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

    private ScopedIndex _scopedIdx;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _scopedIdx = _tm.getIndex(ScopedIndex.class);
        _scopedIdx.open();
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#tearDown()
     */
    @After
    @Override
    public void tearDown() throws Exception {
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
        Assert.assertTrue(_scopedIdx.getAssociations(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getAssociations(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getAssociationThemes().isEmpty());
        Association scoped = createAssociation();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getAssociations(null).size());
        Assert.assertTrue(_scopedIdx.getAssociations(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getAssociations(null).size());
        Assert.assertFalse(_scopedIdx.getAssociations(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociationThemes().isEmpty());
        Assert.assertEquals(1, _scopedIdx.getAssociationThemes().size());
        Assert.assertTrue(_scopedIdx.getAssociations(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getAssociations(null).size());
        Assert.assertFalse(_scopedIdx.getAssociations(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
    }

    public void testAssociationMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getAssociations(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getAssociations(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getAssociationThemes().isEmpty());
        Association scoped = createAssociation();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getAssociations(null).size());
        Assert.assertTrue(_scopedIdx.getAssociations(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociationThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getAssociationThemes().size());
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        Assert.assertEquals(2, _scopedIdx.getAssociationThemes().size());
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getAssociations(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociations(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getAssociations(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testAssociationMatchAllIllegal() {
        try {
            _scopedIdx.getAssociations(null, true);
            Assert.fail("getAssociations(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrence() {
        Topic theme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getOccurrences(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getOccurrences(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getOccurrenceThemes().isEmpty());
        Occurrence scoped = createOccurrence();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getOccurrences(null).size());
        Assert.assertTrue(_scopedIdx.getOccurrences(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getOccurrences(null).size());
        Assert.assertFalse(_scopedIdx.getOccurrences(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrenceThemes().isEmpty());
        Assert.assertEquals(1, _scopedIdx.getOccurrenceThemes().size());
        Assert.assertTrue(_scopedIdx.getOccurrences(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getOccurrences(null).size());
        Assert.assertFalse(_scopedIdx.getOccurrences(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
    }

    public void testOccurrenceMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getOccurrences(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getOccurrences(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getOccurrenceThemes().isEmpty());
        final Occurrence scoped = createOccurrence();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getOccurrences(null).size());
        Assert.assertTrue(_scopedIdx.getOccurrences(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrenceThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getOccurrenceThemes().size());
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        Assert.assertEquals(2, _scopedIdx.getOccurrenceThemes().size());
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getOccurrences(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrences(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getOccurrences(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testOccurrenceMatchAllIllegal() {
        try {
            _scopedIdx.getOccurrences(null, true);
            Assert.fail("getOccurrences(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testName() {
        Topic theme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getNames(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getNames(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getNameThemes().isEmpty());
        Name scoped = createName();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getNames(null).size());
        Assert.assertTrue(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNameThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getNames(null).size());
        Assert.assertFalse(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNameThemes().isEmpty());
        Assert.assertEquals(1, _scopedIdx.getNameThemes().size());
        Assert.assertTrue(_scopedIdx.getNames(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNameThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getNames(null).size());
        Assert.assertFalse(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNameThemes().contains(theme));
    }

    public void testName2() {
        Topic theme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getNames(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getNames(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getNameThemes().isEmpty());
        Name scoped = createTopic().createName("tinyTiM", Collections.singleton(theme));
        Assert.assertEquals(1, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getNames(null).size());
        Assert.assertFalse(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNameThemes().isEmpty());
        Assert.assertEquals(1, _scopedIdx.getNameThemes().size());
        Assert.assertTrue(_scopedIdx.getNames(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNameThemes().contains(theme));
        scoped.remove();
        _updateIndex();
        Assert.assertEquals(0, _scopedIdx.getNames(null).size());
        Assert.assertFalse(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertEquals(0, _scopedIdx.getNames(theme).size());
        Assert.assertFalse(_scopedIdx.getNameThemes().contains(theme));
    }

    public void testNameMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getNames(null).isEmpty());
        Assert.assertTrue(_scopedIdx.getNames(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getNameThemes().isEmpty());
        final Name scoped = createName();
        Assert.assertEquals(0, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getNames(null).size());
        Assert.assertTrue(_scopedIdx.getNames(null).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNameThemes().contains(theme));
        scoped.addTheme(theme);
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getNameThemes().size());
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        Assert.assertEquals(2, _scopedIdx.getNameThemes().size());
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme, unusedTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getNames(new Topic[]{theme2, unusedTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNames(new Topic[]{theme, unusedTheme}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getNames(new Topic[]{theme2, unusedTheme}, true).contains(scoped));
    }

    public void testNameMatchAllIllegal() {
        try {
            _scopedIdx.getNames(null, true);
            Assert.fail("getNames(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantIllegal() {
        try {
            _scopedIdx.getVariants(null);
            Assert.fail("getVariants(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantMatchAllIllegal() {
        try {
            _scopedIdx.getVariants(null, true);
            Assert.fail("getVariants(null, boolean) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariant() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createName();
        Assert.assertEquals(0, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        Assert.assertEquals("Unexpected variant's scope size", 1, scoped.getScope().size());
        _updateIndex();
        Assert.assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        Assert.assertEquals("Unexpected number of variant themes", 
                        1, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        // Add theme to name
        name.addTheme(theme2);
        Assert.assertEquals(1, name.getScope().size());
        Assert.assertEquals("The scope change of the parent is not reflected in the variant's scope",
                        2, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals("Change of the parent's scope is not reflected in the index",
                    2, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        Assert.assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Remove theme from name
        name.removeTheme(theme2);
        _updateIndex();
        Assert.assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        Assert.assertEquals("The scope change in the name is not reflected in variant",
                        1, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        scoped.addTheme(theme2);
        _updateIndex();
        Assert.assertEquals("Change of the variant's scope is not reflected in the index",
                        2, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        Assert.assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Add theme to name
        name.addTheme(theme2);
        _updateIndex();
        Assert.assertEquals("Adding a theme to the variant's parent is not reflected",
                        2, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        Assert.assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        // Remove theme from name
        name.removeTheme(theme2);
        _updateIndex();
        Assert.assertEquals("Removing the name's theme MUST NOT be reflected in the variant's scope",
                        2, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        Assert.assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        scoped.removeTheme(theme2);
        Assert.assertFalse(_scopedIdx.getVariantThemes().isEmpty());
        Assert.assertEquals(1, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
    }

    public void testVariant2() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name", theme2);
        Assert.assertEquals(1, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        Assert.assertEquals(2, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(2, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
        Assert.assertTrue(_scopedIdx.getVariants(theme2).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme2));
        name.removeTheme(theme2);
        Assert.assertEquals(0, name.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(theme).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariantThemes().contains(theme));
    }

    public void testVariantMatchAll() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name");
        Assert.assertEquals(0, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme);
        Assert.assertEquals(1, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(1, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        scoped.addTheme(theme2);
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme}, true).contains(scoped));
        final Topic nameTheme = createTopic();
        name.addTheme(nameTheme);
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, true).contains(scoped));
        name.removeTheme(nameTheme);
        _updateIndex();
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        scoped.removeTheme(theme);
        _updateIndex();
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
    }

    public void testVariantMatchAll2() {
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        final Topic unusedTheme = createTopic();
        final Topic nameTheme = createTopic();
        _updateIndex();
        Assert.assertTrue(_scopedIdx.getVariants(theme).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariants(theme2).isEmpty());
        Assert.assertTrue(_scopedIdx.getVariantThemes().isEmpty());
        final Name name = createTopic().createName("Name", nameTheme);
        Assert.assertEquals(1, name.getScope().size());
        final Variant scoped = name.createVariant("Variant", theme, theme2);
        Assert.assertEquals(3, scoped.getScope().size());
        _updateIndex();
        Assert.assertEquals(3, _scopedIdx.getVariantThemes().size());
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{nameTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, true).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, nameTheme}, false).contains(scoped));
        Assert.assertTrue(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, false).contains(scoped));
        Assert.assertFalse(_scopedIdx.getVariants(new Topic[]{theme, theme2, unusedTheme, nameTheme}, true).contains(scoped));
    }

}
