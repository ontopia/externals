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
 * Tests against the {@link Scoped} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestScoped extends TMAPITestCase {

    /**
     * Tests adding / removing themes.
     *
     * @param scoped The scoped Topic Maps construct to test.
     */
    private void _testScoped(final Scoped scoped) {
        int scopeSize = (scoped instanceof Variant) ? scoped.getScope().size() : 0;
        Assert.assertEquals(scopeSize, scoped.getScope().size());
        final Topic theme1 = createTopic();
        scoped.addTheme(theme1);
        scopeSize++;
        Assert.assertEquals(scopeSize, scoped.getScope().size());
        Assert.assertTrue(scoped.getScope().contains(theme1));
        final Topic theme2 = createTopic();
        Assert.assertFalse(scoped.getScope().contains(theme2));
        scoped.addTheme(theme2);
        scopeSize++;
        Assert.assertEquals(scopeSize, scoped.getScope().size());
        Assert.assertTrue(scoped.getScope().contains(theme1));
        Assert.assertTrue(scoped.getScope().contains(theme2));
        scoped.removeTheme(theme2);
        scopeSize--;
        Assert.assertEquals(scopeSize, scoped.getScope().size());
        Assert.assertTrue(scoped.getScope().contains(theme1));
        Assert.assertFalse(scoped.getScope().contains(theme2));
        scoped.removeTheme(theme1);
        scopeSize--;
        Assert.assertEquals(scopeSize, scoped.getScope().size());
        try {
            scoped.addTheme(null);
            Assert.fail("addTheme(null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    /**
     * Tests against an association.
     */
    @Test
    public void testAssociation() {
        _testScoped(createAssociation());
    }

    /**
     * Tests against an occurrence.
     */
    @Test
    public void testOccurrence() {
        _testScoped(createOccurrence());
    }

    /**
     * Tests against a name.
     */
    @Test
    public void testName() {
        _testScoped(createName());
    }

    /**
     * Tests against a variant.
     */
    @Test
    public void testVariant() {
        _testScoped(createVariant());
    }
}
