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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Provides a test suite which contains all test cases for the .core package.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 129 $ - $Date: 2009-10-22 13:31:17 +0000 (Thu, 22 Oct 2009) $
 */
public class AllCoreTests extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestReifiable.class);
        suite.addTestSuite(TestScoped.class);
        suite.addTestSuite(TestTyped.class);
        suite.addTestSuite(TestLocator.class);
        suite.addTestSuite(TestTopicMerge.class);
        suite.addTestSuite(TestTopicMapMerge.class);
        suite.addTestSuite(TestTopicMergeDetectionAutomergeEnabled.class);
        suite.addTestSuite(TestTopicMergeDetectionAutomergeDisabled.class);
        suite.addTestSuite(TestTopicRemovableConstraint.class);
        suite.addTestSuite(TestItemIdentifierConstraint.class);
        
        suite.addTestSuite(TestTopicMapSystem.class);
        suite.addTestSuite(TestFeatureStrings.class);
        suite.addTestSuite(TestConstruct.class);
        suite.addTestSuite(TestTopicMap.class);
        suite.addTestSuite(TestTopic.class);
        suite.addTestSuite(TestAssociation.class);
        suite.addTestSuite(TestSameTopicMap.class);
        suite.addTestSuite(TestRole.class);
        suite.addTestSuite(TestOccurrence.class);
        suite.addTestSuite(TestName.class);
        suite.addTestSuite(TestVariant.class);
        return suite;
    }
}
