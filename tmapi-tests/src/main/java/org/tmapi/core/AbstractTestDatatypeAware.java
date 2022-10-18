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

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract test against the {@link DatatypeAware} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 150 $ - $Date: 2010-02-26 19:19:42 +0000 (Fri, 26 Feb 2010) $
 */
public abstract class AbstractTestDatatypeAware extends TMAPITestCase {

    protected static final String _XSD = "http://www.w3.org/2001/XMLSchema#";
    protected static final String _XSD_STRING = _XSD + "string";
    protected static final String _XSD_INTEGER = _XSD + "integer";
    protected static final String _XSD_INT = _XSD + "int";
    protected static final String _XSD_FLOAT = _XSD + "float";
    protected static final String _XSD_DECIMAL = _XSD + "decimal";
    protected static final String _XSD_LONG = _XSD + "long";
    protected static final String _XSD_ANY_URI = _XSD + "anyURI";

    protected Locator _xsdString;
    protected Locator _xsdInteger;
    protected Locator _xsdInt;
    protected Locator _xsdFloat;
    protected Locator _xsdDecimal;
    protected Locator _xsdLong;
    protected Locator _xsdAnyURI;

    /**
     * Returns a {@link DatatypeAware} instance to run the tests against.
     *
     * @return A {@link DatatypeAware} instance.
     */
    protected abstract DatatypeAware getDatatypeAware();

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _xsdString = createLocator(_XSD_STRING);
        _xsdInteger = createLocator(_XSD_INTEGER);
        _xsdInt = createLocator(_XSD_INT);
        _xsdFloat = createLocator(_XSD_FLOAT);
        _xsdDecimal = createLocator(_XSD_DECIMAL);
        _xsdLong = createLocator(_XSD_LONG);
        _xsdAnyURI = createLocator(_XSD_ANY_URI);
    }

    @Test
    public void testString() {
        final DatatypeAware dt = getDatatypeAware();
        final String value = "a string";
        dt.setValue(value);
        Assert.assertEquals(value, dt.getValue());
        Assert.assertEquals(_xsdString, dt.getDatatype());
        assertFailInteger(dt);
        assertFailInt(dt);
        assertFailFloat(dt);
        assertFailLong(dt);
        assertFailDecimal(dt);
    }

    @Test
    public void testStringExplicit() {
        final DatatypeAware dt = getDatatypeAware();
        final String value = "a string";
        dt.setValue(value, _xsdString);
        Assert.assertEquals(value, dt.getValue());
        Assert.assertEquals(_xsdString, dt.getDatatype());
        assertFailInteger(dt);
        assertFailInt(dt);
        assertFailFloat(dt);
        assertFailLong(dt);
        assertFailDecimal(dt);
    }

    @Test
    public void testURI() {
        final DatatypeAware dt = getDatatypeAware();
        final String iri = "http://www.example.org/";
        final Locator value = createLocator(iri);
        dt.setValue(value);
        Assert.assertEquals(iri, dt.getValue());
        Assert.assertEquals(_xsdAnyURI, dt.getDatatype());
        Assert.assertEquals(value, dt.locatorValue());
        assertFailInteger(dt);
        assertFailInt(dt);
        assertFailFloat(dt);
        assertFailLong(dt);
        assertFailDecimal(dt);
    }

    @Test
    public void testURIExplicit() {
        final DatatypeAware dt = getDatatypeAware();
        final String iri = "http://www.example.org/";
        final Locator value = createLocator(iri);
        dt.setValue(iri, _xsdAnyURI);
        Assert.assertEquals(iri, dt.getValue());
        Assert.assertEquals(_xsdAnyURI, dt.getDatatype());
        Assert.assertEquals(value, dt.locatorValue());
        assertFailInteger(dt);
        assertFailInt(dt);
        assertFailFloat(dt);
        assertFailLong(dt);
        assertFailDecimal(dt);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testInteger() {
        final BigInteger value = BigInteger.TEN;
        final DatatypeAware dt = getDatatypeAware(); 
        dt.setValue(value);
        Assert.assertEquals(value.toString(), dt.getValue());
        Assert.assertEquals(_xsdInteger, dt.getDatatype());
        Assert.assertEquals(value, dt.integerValue());
        Assert.assertEquals(BigDecimal.TEN, dt.decimalValue());
        Assert.assertEquals(10L, dt.longValue());
        Assert.assertEquals(10, dt.intValue());
        Assert.assertEquals(10.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testIntegerExplicit() {
        final BigInteger value = BigInteger.TEN;
        final DatatypeAware dt = getDatatypeAware();
        dt.setValue(value.toString(), _xsdInteger);
        Assert.assertEquals(value.toString(), dt.getValue());
        Assert.assertEquals(_xsdInteger, dt.getDatatype());
        Assert.assertEquals(value, dt.integerValue());
        Assert.assertEquals(BigDecimal.TEN, dt.decimalValue());
        Assert.assertEquals(10L, dt.longValue());
        Assert.assertEquals(10, dt.intValue());
        Assert.assertEquals(10.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testDecimal() {
        final BigDecimal value = BigDecimal.TEN;
        final DatatypeAware dt = getDatatypeAware(); 
        dt.setValue(value);
        final String val = dt.getValue();
        Assert.assertTrue("Expected either '10' or the canonical representation '10.0'",
                "10".equals(val) || "10.0".equals(val));
        Assert.assertEquals(_xsdDecimal, dt.getDatatype());
        Assert.assertEquals(value, dt.decimalValue());
        Assert.assertEquals(BigInteger.TEN, dt.integerValue());
        Assert.assertEquals(10L, dt.longValue());
        Assert.assertEquals(10, dt.intValue());
        Assert.assertEquals(10.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testDecimalExplicit() {
        final BigDecimal value = BigDecimal.TEN;
        final DatatypeAware dt = getDatatypeAware();
        dt.setValue(value.toString(), _xsdDecimal);
        final String val = dt.getValue();
        Assert.assertTrue("Expected either '10' or the canonical representation '10.0'",
                "10".equals(val) || "10.0".equals(val));
        Assert.assertEquals(_xsdDecimal, dt.getDatatype());
        if (!value.equals(dt.decimalValue()) && 
                !new BigDecimal("10.0").equals(dt.decimalValue())) {
            Assert.fail("Expected either '10' or '10.0' as return value of 'decimalValue()'");
        }
        Assert.assertEquals(BigInteger.TEN, dt.integerValue());
        Assert.assertEquals(10L, dt.longValue());
        Assert.assertEquals(10, dt.intValue());
        Assert.assertEquals(10.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testInt() {
        final int value = 1976;
        final String strValue = "1976";
        final DatatypeAware dt = getDatatypeAware(); 
        dt.setValue(value);
        Assert.assertEquals(strValue, dt.getValue());
        Assert.assertEquals(_xsdInt, dt.getDatatype());
        Assert.assertEquals(new BigDecimal(value), dt.decimalValue());
        Assert.assertEquals(new BigInteger(strValue), dt.integerValue());
        Assert.assertEquals(1976L, dt.longValue());
        Assert.assertEquals(1976, dt.intValue());
        Assert.assertEquals(1976.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testLong() {
        final long value = 1976L;
        final String strValue = "1976";
        final DatatypeAware dt = getDatatypeAware(); 
        dt.setValue(value);
        Assert.assertEquals(strValue, dt.getValue());
        Assert.assertEquals(_xsdLong, dt.getDatatype());
        Assert.assertEquals(new BigDecimal(value), dt.decimalValue());
        Assert.assertEquals(new BigInteger(strValue), dt.integerValue());
        Assert.assertEquals(value, dt.longValue());
        Assert.assertEquals(1976, dt.intValue());
        Assert.assertEquals(1976.0F, dt.floatValue(), 0);
    }

    @SuppressWarnings("boxing")
    @Test
    public void testFloat() {
        final float value = 1976.0F;
        final String strValue = "1976.0";
        final DatatypeAware dt = getDatatypeAware(); 
        dt.setValue(value);
        Assert.assertEquals(strValue, dt.getValue());
        Assert.assertEquals(_xsdFloat, dt.getDatatype());
        Assert.assertTrue("Expected either BigDecimal(1976.0F).equals(dt.decimalValue()) or BigDecimal('1976.0').equals(dt.decimalValue())",
                    new BigDecimal(value).equals(dt.decimalValue()) || new BigDecimal(strValue).equals(dt.decimalValue()));
        Assert.assertEquals(new BigInteger("1976"), dt.integerValue());
        Assert.assertEquals(1976L, dt.longValue());
        Assert.assertEquals(1976, dt.intValue());
        Assert.assertEquals(value, dt.floatValue(), 0);
    }

    @Test
    public void testUserDatatype() {
        final Locator datatype = createLocator("http://www.example.org/datatype");
        final DatatypeAware dt = getDatatypeAware();
        final String value = "Value";
        dt.setValue(value, datatype);
        Assert.assertEquals(datatype, dt.getDatatype());
        Assert.assertEquals(value, dt.getValue());
        assertFailInteger(dt);
        assertFailInt(dt);
        assertFailFloat(dt);
        assertFailLong(dt);
        assertFailDecimal(dt);
    }

    @Test
    public void testIllegalDatatype() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue("value", null);
            Assert.fail("datatypeAware.setValue(\"value\", null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testIllegalStringValue() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue((String)null);
            Assert.fail("datatypeAware.setValue((String)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testIllegalStringValueExplicit() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue(null, _xsdString);
            Assert.fail("datatypeAware.setValue(null, datatype) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testIllegalLocatorValue() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue((Locator)null);
            Assert.fail("datatypeAware.setValue((Locator)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testIllegalIntegerValue() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue((BigInteger)null);
            Assert.fail("datatypeAware.setValue((BigInteger)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testIllegalDecimalValue() {
        final DatatypeAware dt = getDatatypeAware();
        try {
            dt.setValue((BigDecimal)null);
            Assert.fail("datatypeAware.setValue((BigDecimal)null) is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    protected void assertFailInteger(final DatatypeAware dt) {
        try {
            dt.integerValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'BigInteger'");
        }
        catch (NumberFormatException ex) {
            // noop.
        }
    }

    protected void assertFailInt(final DatatypeAware dt) {
        try {
            dt.intValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'int'");
        }
        catch (NumberFormatException ex) {
            // noop.
        }
    }

    protected void assertFailFloat(final DatatypeAware dt) {
        try {
            dt.floatValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'float'");
        }
        catch (NumberFormatException ex) {
            // noop.
        }
    }

    protected void assertFailDecimal(final DatatypeAware dt) {
        try {
            dt.decimalValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'BigDecimal'");
        }
        catch (NumberFormatException ex) {
            // noop.
        }
    }

    protected void assertFailLong(final DatatypeAware dt) {
        try {
            dt.longValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'long'");
        }
        catch (NumberFormatException ex) {
            // noop.
        }
    }

    protected void assertFailLocator(final DatatypeAware dt) {
        try {
            dt.locatorValue();
            Assert.fail("Expected a Assert.failure for converting the value to 'Locator'");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }
}
