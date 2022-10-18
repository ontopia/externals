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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if the TMAPI feature strings are recognised.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 129 $ - $Date: 2009-10-22 13:31:17 +0000 (Thu, 22 Oct 2009) $
 */
public class TestFeatureStrings {

    private static final String _FEATURE_BASE = "http://tmapi.org/features/";
    
    private static final String 
        TYPE_INSTANCE_ASSOCS= _FEATURE_BASE + "type-instance-associations",
        READ_ONLY = _FEATURE_BASE + "readOnly",
        AUTOMERGE = _FEATURE_BASE + "automerge";
    
    private TopicMapSystemFactory _factory;

    @Before
    public void setUp() throws Exception {
        _factory = TopicMapSystemFactory.newInstance();
    }

    @After
    protected void tearDown() throws Exception {
        _factory = null;
    }

    /**
     * Creates a new {@link TopicMapSystem} with the current configuration
     * of the {@link #_factory} 
     *
     * @return A newly created TopicMapSystem.
     */
    private TopicMapSystem makeTopicMapSystem() {
        try {
            return _factory.newTopicMapSystem();
        }
        catch (TMAPIException ex) {
            throw new TMAPIRuntimeException("Cannot create TopicMapSystem", ex);
        }
    }
    
    /**
     * Tests the feature string "type-instance-associations".
     * 
     * @throws FeatureNotRecognizedException 
     *          If the Topic Maps engine is not TMAPI 2.0 compatible.
     */
    @Test
    public void testTypeInstanceAssociations() throws FeatureNotRecognizedException {
        _testFeature(TYPE_INSTANCE_ASSOCS);
    }

    /**
     * Tests the feature string "automerge".
     * 
     * @throws FeatureNotRecognizedException 
     *          If the Topic Maps engine is not TMAPI 2.0 compatible.
     */
    @Test
    public void testAutomerge() throws FeatureNotRecognizedException {
        _testFeature(AUTOMERGE);
    }

    /**
     * Tests the feature string "readOnly".
     *
     * @throws FeatureNotRecognizedException If the Topic Maps engine is not TMAPI 2.0 compatible.
     */
    @Test
    public void testReadOnly() throws FeatureNotRecognizedException {
        _testFeature(READ_ONLY);
    }

   /**
    * Tests the provided featureName. The featureName must be recognized by
    * the engine.
    * 
    * @throws FeatureNotRecognizedException 
    *           If the Topic Maps engine is not TMAPI 2.0 compatible.
    */
    private void _testFeature(final String featureName) throws FeatureNotRecognizedException {
        final boolean enabledInFactory = _factory.getFeature(featureName);
        try {
            _factory.setFeature(featureName, enabledInFactory);
        }
        catch (FeatureNotSupportedException ex) {
            Assert.fail("The engine does not allow to set the feature string to the default value returned by factory.getFeature(String)");
        }
        final TopicMapSystem sys = makeTopicMapSystem();
        final boolean enabledInSystem = sys.getFeature(featureName);
        Assert.assertEquals("The system has a different value of " + featureName + " than the factory", 
                        enabledInFactory, enabledInSystem);
    }
}
