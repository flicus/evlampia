package org.schors.eva.commands;

import org.schors.eva.CommandContext;
import org.schors.eva.annotations.Command;
import org.schors.eva.annotations.CommandExecute;
import org.schors.eva.facilities.TokenManager;

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
 * Time: 17:31
 * <p/>
 * $Id:
 */

@Command(
        dependsOn = {"token"},
        group = "General",
        longDescription = "",
        name = "GetToken",
        prefixes = {"!t"},
        shortDescription = ""
)
public class GetTokenCmd {

    @CommandExecute
    public void execute(CommandContext ctx) {
        TokenManager tokenManager = (TokenManager) ctx.getFacility(TokenManager.getName());
        String token = tokenManager.makeNewToken(ctx.getWho());
        System.out.println(token);
    }
}
