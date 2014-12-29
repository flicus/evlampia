/*
 * The MIT License (MIT)
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.facility;

import org.schors.eva.Application;
import org.schors.eva.configuration.AbstractConfiguration;
import org.schors.eva.configuration.ConfigurationListener;
import org.schors.eva.dialog.Dialog;

public abstract class AbstractFacility implements ConfigurationListener {

    protected FacilityStatus status = FacilityStatus.INITIAL;
    protected String statusMessage = "ok";
    protected Application application;


    public AbstractFacility(Application application) {
        this.application = application;
    }

    public <T extends AbstractFacility> T getFacility(Class<T> clazz) {
        return application.getFacilityManager().getFacilityForUsing(clazz);
    }

    public <T extends AbstractConfiguration> T getConfiguration(Class<T> clazz) {
        return application.getConfigurationManager().getSection(clazz);
    }

    public Dialog createDialog(String endpoint) {
        return application.getProtocolManager().createDialog(endpoint);
    }

    @Override
    public void onConfigurationChange() {
        //override if you need
    }

    public Application getApplication() {
        return application;
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public abstract void start();

    public abstract void stop();

}
