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
 * Tests against the {@link Occurrence} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public class TestOccurrence extends AbstractTestDatatypeAware {

    /* (non-Javadoc)
     * @see org.tmapi.core.TestDatatypeAware#getDatatypeAware()
     */
    @Override
    protected DatatypeAware getDatatypeAware() {
        return createOccurrence();
    }

    /**
     * Tests the parent/child relationship between topic and occurrence.
     */
    @Test
    public void testParent() {
        final Topic parent = createTopic();
        Assert.assertTrue("Expected new topics to be created with no occurrences",
                    parent.getOccurrences().isEmpty());
        final Occurrence occ = parent.createOccurrence(createTopic(), "Occurrence");
        Assert.assertEquals("Unexpected occurrence parent after creation",
                parent, occ.getParent());
        Assert.assertEquals("Expected occurrence set size to increment for topic",
                    1, parent.getOccurrences().size());
        Assert.assertTrue("Occurrence is not part of getOccurrences()",
                    parent.getOccurrences().contains(occ));
        occ.remove();
        Assert.assertTrue("Expected occurrence set size to decrement for topic.",
                    parent.getOccurrences().isEmpty());
    }

}
