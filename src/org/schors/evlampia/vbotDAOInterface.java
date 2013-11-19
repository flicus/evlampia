/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schors.evlampia;

/**
 *
 * @author flic
 */
public interface vbotDAOInterface {

    public boolean store(long timestamp, String channel, String sender, String message, String msgType);
    public void flush();

}
