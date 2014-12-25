package org.schors.eva.commands;

import org.schors.eva.CommandContext;
import org.schors.eva.annotations.Command;
import org.schors.eva.annotations.CommandExecute;

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
 * Date: 25.12.2014
 * Time: 17:23
 * <p/>
 * $Id:
 */
@Command(
        dependsOn = {},
        group = "General",
        name = "Help",
        shortDescription = "",
        longDescription = "",
        prefixes = {"!?"}
)
public class HelpCmd {

    @CommandExecute
    public void execute(CommandContext ctx) {


    }

}
