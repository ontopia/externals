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

import org.tmapi.core.TMAPIException;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

/**
 * Base class for all test factories.
 * 
 * @author TMAPI <a href="http://www.tmapi.org">tmapi.org</a>
 * @author Kal Ahmed
 * @version $Rev: 88 $ - $Date: 2008-11-12 13:28:22 +0000 (Wed, 12 Nov 2008) $
 */
public class TopicMapSystemFactoryTestBase extends TopicMapSystemFactory {

    TopicMapSystemFactoryTestBase() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean getFeature(String featureName)
            throws FeatureNotRecognizedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasFeature(String featureName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TopicMapSystem newTopicMapSystem() throws TMAPIException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFeature(String featureName, boolean enable)
            throws FeatureNotSupportedException, FeatureNotRecognizedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        // TODO Auto-generated method stub
        
    }

}
