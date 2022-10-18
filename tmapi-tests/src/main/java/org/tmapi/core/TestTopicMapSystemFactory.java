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

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests against the {@link TopicMapSystemFactory}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Kal Ahmed
 * @version $Rev: 108 $ - $Date: 2009-06-19 12:09:27 +0000 (Fri, 19 Jun 2009) $
 */
public class TestTopicMapSystemFactory {

    @Test
    public void testReadFromSystemProperty() throws Exception {
        System.setProperty("org.tmapi.core.TopicMapSystemFactory",
                TopicMapSystemFactoryA.class.getName());
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        Assert.assertNotNull(factory);
        Assert.assertTrue(factory instanceof TopicMapSystemFactoryA);
    }

    @Test
    public void testReadFromRuntimeProperty() throws Exception {
        System.getProperties().remove("org.tmapi.core.TopicMapSystemFactory");
        File javaHome = new File(System.getProperty("java.home"));
        File propsFile = new File(javaHome, "lib" + File.separator
                + "tmapi.properties");
        File tmpPropsFile = new File(javaHome, "lib" + File.separator
                + "tmapi.properties.tmp");
        boolean restoreProps = false;
        if (propsFile.exists()) {
            propsFile.renameTo(tmpPropsFile);
            restoreProps = true;
        }
        try {
            Writer writer = new FileWriter(propsFile);
            writer.write(TopicMapSystemFactory.class.getName() + "="
                    + TopicMapSystemFactoryB.class.getName());
            writer.close();

            TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
            boolean deleted = propsFile.delete();
            Assert.assertTrue(deleted);
            Assert.assertNotNull(factory);
            Assert.assertTrue(factory instanceof TopicMapSystemFactoryB);
        }
        finally {
            if (restoreProps) {
                tmpPropsFile.renameTo(propsFile);
            }
        }
    }

    @Test
    public void testReadFromResource() throws Exception {
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        Assert.assertNotNull(factory);
        Assert.assertTrue(factory instanceof TopicMapSystemFactoryC);
    }

}
