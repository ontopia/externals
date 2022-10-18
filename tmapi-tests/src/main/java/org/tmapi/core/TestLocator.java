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
 * Tests against the {@link Locator} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 133 $ - $Date: 2010-01-10 17:23:12 +0000 (Sun, 10 Jan 2010) $
 */
public class TestLocator extends TMAPITestCase {

    @Test
    public void testNormalization() {
        final Locator loc = _tm.createLocator("http://www.example.org/test%20me/");
        Assert.assertEquals("http://www.example.org/test%20me/", loc.toExternalForm());
        final Locator loc2 = loc.resolve("./too");
        Assert.assertEquals("http://www.example.org/test%20me/too", loc2.toExternalForm());

    /* qs@2018-03-07

    The following disabled assertions are not legal:
    - Locators should not encode or decode during construction or as method result as en-/decoding is scheme specific
    - Locator contructor should not accept an illegal URI

        Assert.assertEquals("http://www.example.org/test me/", loc.getReference());
        Assert.assertEquals("http://www.example.org/test me/too", loc2.getReference());
        final Locator loc3 = _tm.createLocator("http://www.example.org/test me/");
        Assert.assertEquals("http://www.example.org/test me/", loc3.getReference());
        Assert.assertEquals("http://www.example.org/test%20me/", loc3.toExternalForm());
        Assert.assertEquals(loc, loc3);
    */
    }

    @Test
    public void testIllegalLocatorAddresses() {
        final String[] ILLEGAL = {"", "#fragment"};
        for (String addr: ILLEGAL) {
            try {
                _tm.createLocator(addr);
                Assert.fail("Expected an error TopicMap#createLocator() with the input '" + addr + "'");
            }
            catch (MalformedIRIException ex) {
                // noop.
            }
            try {
                _sys.createLocator(addr);
                Assert.fail("Expected an error TopicMapSystem#createLocator() with the input '" + addr + "'");
            }
            catch (MalformedIRIException ex) {
                // noop.
            }
        }
    }

    /**
     * Tests the examples from RFC 3986 -- 5.4.1. Normal Examples.
     */
    @Test
    public void test_RFC_3986__5_4_1_Normal_Examples() {
        String[][] IRIS = new String[][] {
                {"g:h", "g:h"},
                {"g", "http://a/b/c/g"},
                {"./g", "http://a/b/c/g"},
                {"/g", "http://a/g"},
                // Original: //g -> http://g
                // Changed to avoid problems with trailing slash normailzations
                {"//g/x", "http://g/x"},
                // Moved to TestRFC3986:
                // {"?y", "http://a/b/c/d;p?y"},
                {"g?y", "http://a/b/c/g?y"},
                {"#s", "http://a/b/c/d;p?q#s"},
                {"g#s", "http://a/b/c/g#s"},
                {"g?y#s", "http://a/b/c/g?y#s"},
                {";x", "http://a/b/c/;x"},
                {"g;x", "http://a/b/c/g;x"},
                {"g;x?y#s", "http://a/b/c/g;x?y#s"},
                // Moved to TestRFC3986:
                // {"", "http://a/b/c/d;p?q"},
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
        Assert.assertEquals(reference, base.toExternalForm());
        for (int i=0; i<IRIS.length; i++) {
            Assert.assertEquals("Unexpected result for " + IRIS[i][0],
                    IRIS[i][1], base.resolve(IRIS[i][0]).toExternalForm());
        }
    }

    /**
     * According to RFC 3986 an empty fragment / query has to be kept and
     * must not be stripped away from the address.
     */
    @Test
    public void testNormizationPreserveEmpty() {
        String ref = "http://www.tmapi.org/x?";
        Assert.assertEquals("http://www.tmapi.org/x?", _tm.createLocator(ref).toExternalForm());
        ref = "http://www.tmapi.org/x#";
        Assert.assertEquals("http://www.tmapi.org/x#", _tm.createLocator(ref).toExternalForm());
    }

}
