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
 * Tests merge detection with feature "automerge" enabled.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 87 $ - $Date: 2008-11-12 13:15:50 +0000 (Wed, 12 Nov 2008) $
 */
public class TestTopicMergeDetectionAutomergeEnabled extends AbstractTestTopicMergeDetection {

    /* (non-Javadoc)
     * @see org.tmapi.core.TestTopicMergeDetection#getAutomergeEnabled()
     */
    @Override
    protected boolean getAutomergeEnabled() {
        return true;
    }

}
