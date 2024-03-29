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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.TMAPITestCase;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

/**
 * Tests against the {@link LiteralInstanceIndex} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 65 $ - $Date: 2008-08-19 11:14:19 +0000 (Tue, 19 Aug 2008) $
 */
public class TestLiteralIndex extends TMAPITestCase {

    private LiteralIndex _litIdx;
    private Locator _xsdString;
    private Locator _xsdAnyURI;

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _litIdx = _tm.getIndex(LiteralIndex.class);
        _litIdx.open();
        final String XSD_BASE = "http://www.w3.org/2001/XMLSchema#";
        _xsdString = createLocator(XSD_BASE + "string");
        _xsdAnyURI = createLocator(XSD_BASE + "anyURI");
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#tearDown()
     */
    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        _litIdx.close();
        _litIdx = null;
    }

    private void _updateIndex() {
        if (!_litIdx.isAutoUpdated()) {
            _litIdx.reindex();
        }
    }

    public void testName() {
        final String value = "Value";
        final String value2 = "Value2";
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getNames(value).size());
        final Name name = createTopic().createName(value);
        _updateIndex();
        Assert.assertEquals(1, _litIdx.getNames(value).size());
        Assert.assertTrue(_litIdx.getNames(value).contains(name));
        name.setValue(value2);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getNames(value).size());
        Assert.assertEquals(1, _litIdx.getNames(value2).size());
        Assert.assertTrue(_litIdx.getNames(value2).contains(name));
        name.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getNames(value).size());
        Assert.assertEquals(0, _litIdx.getNames(value2).size());
    }

    public void testNameIllegalString() {
        try {
            _litIdx.getNames(null);
            Assert.fail("getNames(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrenceString() {
        final String value = "Value";
        final String value2 = "Value2";
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value, _xsdString).size());
        final Topic type = createTopic();
        final Occurrence occ = createTopic().createOccurrence(type, value);
        _updateIndex();
        Assert.assertEquals(1, _litIdx.getOccurrences(value).size());
        Assert.assertTrue(_litIdx.getOccurrences(value).contains(occ));
        Assert.assertEquals(1, _litIdx.getOccurrences(value, _xsdString).size());
        Assert.assertTrue(_litIdx.getOccurrences(value, _xsdString).contains(occ));
        occ.setValue(value2);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value, _xsdString).size());
        Assert.assertEquals(1, _litIdx.getOccurrences(value2).size());
        Assert.assertTrue(_litIdx.getOccurrences(value2).contains(occ));
        Assert.assertEquals(1, _litIdx.getOccurrences(value2, _xsdString).size());
        Assert.assertTrue(_litIdx.getOccurrences(value2, _xsdString).contains(occ));
        occ.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value, _xsdString).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2, _xsdString).size());
    }

    public void testOccurrenceURI() {
        final Locator value = createLocator("http://www.example.org/1");
        final Locator value2 = createLocator("http://www.example.org/2");
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value.getReference(), _xsdAnyURI).size());
        final Topic type = createTopic();
        final Occurrence occ = createTopic().createOccurrence(type, value);
        _updateIndex();
        Assert.assertEquals(1, _litIdx.getOccurrences(value).size());
        Assert.assertTrue(_litIdx.getOccurrences(value).contains(occ));
        Assert.assertEquals(1, _litIdx.getOccurrences(value.getReference(), _xsdAnyURI).size());
        Assert.assertTrue(_litIdx.getOccurrences(value.getReference(), _xsdAnyURI).contains(occ));
        occ.setValue(value2);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value.getReference(), _xsdAnyURI).size());
        Assert.assertEquals(1, _litIdx.getOccurrences(value2).size());
        Assert.assertTrue(_litIdx.getOccurrences(value2).contains(occ));
        Assert.assertEquals(1, _litIdx.getOccurrences(value2.getReference(), _xsdAnyURI).size());
        Assert.assertTrue(_litIdx.getOccurrences(value2.getReference(), _xsdAnyURI).contains(occ));
        occ.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value.getReference(), _xsdAnyURI).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2.getReference(), _xsdAnyURI).size());
    }

    public void testOccurrenceExplicitDatatype() {
        final String value = "http://www.example.org/1";
        final String value2 = "http://www.example.org/2";
        final Locator datatype = createLocator("http://www.example.org/datatype");
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value, datatype).size());
        final Topic type = createTopic();
        final Occurrence occ = createTopic().createOccurrence(type, value, datatype);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(1, _litIdx.getOccurrences(value, datatype).size());
        Assert.assertTrue(_litIdx.getOccurrences(value, datatype).contains(occ));
        occ.setValue(value2, datatype);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value, datatype).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2).size());
        Assert.assertEquals(1, _litIdx.getOccurrences(value2, datatype).size());
        Assert.assertTrue(_litIdx.getOccurrences(value2, datatype).contains(occ));
        occ.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getOccurrences(value2).size());
        Assert.assertEquals(0, _litIdx.getOccurrences(value2, datatype).size());
    }

    public void testOccurrenceIllegalString() {
        try {
            _litIdx.getOccurrences((String)null);
            Assert.fail("getOccurrences((String)null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrenceIllegalURI() {
        try {
            _litIdx.getOccurrences((Locator)null);
            Assert.fail("getOccurrences((Locator)null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testOccurrenceIllegalDatatype() {
        try {
            _litIdx.getOccurrences("value", null);
            Assert.fail("getOccurrences(\"value\", null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantString() {
        final String value = "Value";
        final String value2 = "Value2";
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value, _xsdString).size());
        final Topic theme = createTopic();
        final Variant variant = createName().createVariant(value, theme);
        _updateIndex();
        Assert.assertEquals(1, _litIdx.getVariants(value).size());
        Assert.assertTrue(_litIdx.getVariants(value).contains(variant));
        Assert.assertEquals(1, _litIdx.getVariants(value, _xsdString).size());
        Assert.assertTrue(_litIdx.getVariants(value, _xsdString).contains(variant));
        variant.setValue(value2);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value, _xsdString).size());
        Assert.assertEquals(1, _litIdx.getVariants(value2).size());
        Assert.assertTrue(_litIdx.getVariants(value2).contains(variant));
        Assert.assertEquals(1, _litIdx.getVariants(value2, _xsdString).size());
        Assert.assertTrue(_litIdx.getVariants(value2, _xsdString).contains(variant));
        variant.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value, _xsdString).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2, _xsdString).size());
    }

    public void testVariantURI() {
        final Locator value = createLocator("http://www.example.org/1");
        final Locator value2 = createLocator("http://www.example.org/2");
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value.getReference(), _xsdAnyURI).size());
        final Topic theme = createTopic();
        final Variant variant = createName().createVariant(value, theme);
        _updateIndex();
        Assert.assertEquals(1, _litIdx.getVariants(value).size());
        Assert.assertTrue(_litIdx.getVariants(value).contains(variant));
        Assert.assertEquals(1, _litIdx.getVariants(value.getReference(), _xsdAnyURI).size());
        Assert.assertTrue(_litIdx.getVariants(value.getReference(), _xsdAnyURI).contains(variant));
        variant.setValue(value2);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value.getReference(), _xsdAnyURI).size());
        Assert.assertEquals(1, _litIdx.getVariants(value2).size());
        Assert.assertTrue(_litIdx.getVariants(value2).contains(variant));
        Assert.assertEquals(1, _litIdx.getVariants(value2.getReference(), _xsdAnyURI).size());
        Assert.assertTrue(_litIdx.getVariants(value2.getReference(), _xsdAnyURI).contains(variant));
        variant.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value.getReference(), _xsdAnyURI).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2.getReference(), _xsdAnyURI).size());
    }

    public void testVariantExplicitDatatype() {
        final String value = "http://www.example.org/1";
        final String value2 = "http://www.example.org/2";
        final Locator datatype = createLocator("http://www.example.org/datatype");
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value, datatype).size());
        final Topic theme = createTopic();
        final Variant variant = createName().createVariant(value, datatype, theme);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(1, _litIdx.getVariants(value, datatype).size());
        Assert.assertTrue(_litIdx.getVariants(value, datatype).contains(variant));
        variant.setValue(value2, datatype);
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value).size());
        Assert.assertEquals(0, _litIdx.getVariants(value, datatype).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2).size());
        Assert.assertEquals(1, _litIdx.getVariants(value2, datatype).size());
        Assert.assertTrue(_litIdx.getVariants(value2, datatype).contains(variant));
        variant.remove();
        _updateIndex();
        Assert.assertEquals(0, _litIdx.getVariants(value2).size());
        Assert.assertEquals(0, _litIdx.getVariants(value2, datatype).size());
    }

    public void testVariantIllegalString() {
        try {
            _litIdx.getVariants((String)null);
            Assert.fail("getVariants((String)null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantIllegalURI() {
        try {
            _litIdx.getVariants((Locator)null);
            Assert.fail("getVariants((Locator)null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testVariantIllegalDatatype() {
        try {
            _litIdx.getVariants("value", null);
            Assert.fail("getVariants(\"value\", null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

}
