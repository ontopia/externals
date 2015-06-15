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
 * Tests against the {@link Locator} interface if the implementation is RFC 3986 compatible.
 * <p> See <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986</a></p>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 111 $ - $Date: 2009-07-04 17:52:31 +0000 (Sat, 04 Jul 2009) $
 */
public class TestRFC3986 extends TMAPITestCase {

    public TestRFC3986(String name) {
        super(name);
    }

    /**
     * Tests the examples from RFC 3986 -- 5.4.1. Normal Examples.
     */
    public void test_RFC_3986__5_4_1_Normal_Examples() {
        String[][] IRIS = new String[][] {
                {"g:h", "g:h"},
                {"g", "http://a/b/c/g"},
                {"./g", "http://a/b/c/g"},
                {"/g", "http://a/g"},
                // Original: //g -> http://g
                // Changed to avoid problems with trailing slash normailzations
                {"//g/x", "http://g/x"},
                {"?y", "http://a/b/c/d;p?y"},
                {"g?y", "http://a/b/c/g?y"},
                {"#s", "http://a/b/c/d;p?q#s"},
                {"g#s", "http://a/b/c/g#s"},
                {"g?y#s", "http://a/b/c/g?y#s"},
                {";x", "http://a/b/c/;x"},
                {"g;x", "http://a/b/c/g;x"},
                {"g;x?y#s", "http://a/b/c/g;x?y#s"},
                {"", "http://a/b/c/d;p?q"},
                {".", "http://a/b/c/"},
                {"./", "http://a/b/c/"},
                {"..", "http://a/b/"},
                {"../", "http://a/b/"},
                {"../g", "http://a/b/g"},
                {"../..", "http://a/"},
                {"../../", "http://a/"},
                {"../../g", "http://a/g"}
        };
        final String reference = "http://a/b/c/d;p?q";
        final Locator base = _tm.createLocator(reference);
        assertEquals(reference, base.toExternalForm());
        for (int i=0; i<IRIS.length; i++) {
            assertEquals("Unexpected result for " + IRIS[i][0],
                    IRIS[i][1], base.resolve(IRIS[i][0]).toExternalForm());
        }
    }

}
