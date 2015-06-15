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

import junit.framework.TestCase;

import org.tmapi.core.TopicMapSystemFactory;

/**
 * Tests against the {@link TopicMapSystemFactory}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Kal Ahmed
 * @version $Rev: 108 $ - $Date: 2009-06-19 12:09:27 +0000 (Fri, 19 Jun 2009) $
 */
public class TestTopicMapSystemFactory extends TestCase {

    /**
     * Constructor for TopicMapSystemFactoryTest.
     * 
     * @param arg0
     */
    public TestTopicMapSystemFactory(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestTopicMapSystemFactory.class);
    }

    public void testReadFromSystemProperty() throws Exception {
        System.setProperty("org.tmapi.core.TopicMapSystemFactory",
                TopicMapSystemFactoryA.class.getName());
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        assertNotNull(factory);
        assertTrue(factory instanceof TopicMapSystemFactoryA);
    }

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
            assertTrue(deleted);
            assertNotNull(factory);
            assertTrue(factory instanceof TopicMapSystemFactoryB);
        }
        finally {
            if (restoreProps) {
                tmpPropsFile.renameTo(propsFile);
            }
        }
    }

    public void testReadFromResource() throws Exception {
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        assertNotNull(factory);
        assertTrue(factory instanceof TopicMapSystemFactoryC);
    }

}
