package org.schors.evlampia.convertor;

import org.jdom2.Element;

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
 * Date: 26.09.2014
 * Time: 17:09
 * <p/>
 * $Id:
 */

public interface Convertable {

    public Element toElement();

}
