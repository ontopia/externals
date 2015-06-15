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

/**
 * Base interface for all indices.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 62 $ - $Date: 2008-08-08 12:00:50 +0000 (Fri, 08 Aug 2008) $
 */
public interface Index {

    /**
     * Open the index.
     * 
     * This method must be invoked before using any other method (aside from
     * {@link #isOpen()}) exported by this interface or derived interfaces. 
     */
    public void open();

    /**
     * Close the index.
     */
    public void close();

    /**
     * Indicates if the index is open.
     * 
     * @return <tt>true</tt> if index is already opened, <tt>false</tt> otherwise.
     */
    public boolean isOpen();

    /**
     * Indicates whether the index is updated automatically.
     * <p>
     * If the value is <tt>true</tt>, then the index is automatically kept
     * synchronized with the topic map as values are changed.
     * If the value is <tt>false</tt>, then the {@link Index#reindex()}
     * method must be called to resynchronize the index with the topic map
     * after values are changed.
     * </p>
     * 
     * @return <tt>true</tt> if index is updated automatically, <tt>false</tt> otherwise.
     */
    public boolean isAutoUpdated();

    /**
     * Synchronizes the index with data in the topic map.
     */
    public void reindex();

}
