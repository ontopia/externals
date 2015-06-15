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
package org.tmapi;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.tmapi.core.AllCoreTests;
import org.tmapi.index.AllIndexTests;

/**
 * Provides a test suite which contains all test cases for TMAPI 2.0.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Marius Garshol (larsga[at]garshol.priv.no) <a href="http:/www.garshol.priv.no/">LMG</a>
 * @version $Rev: 109 $ - $Date: 2009-06-30 15:53:17 +0200 (Tue, 30 Jun 2009) $
 */
public class AllTests extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(AllCoreTests.suite());
        suite.addTest(AllIndexTests.suite());
        return suite;
    }
}
