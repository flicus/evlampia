package org.schors.evlampia.core;

import org.jivesoftware.smack.packet.Packet;

/**
 * Copyright (c) 2013 Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Amdocs jNetX. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs jNetX.
 * <p/>
 * User: Sergey Skoptsov (sskoptsov@amdocs.com)
 * Date: 25.11.13
 * Time: 15:33
 * <p/>
 * $Id:
 */

public interface PacketSender {

    public void send(Packet packet);
    public void send(String to, String body);
    public void send(String body);
}
