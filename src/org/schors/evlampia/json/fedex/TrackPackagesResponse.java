/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.schors.evlampia.json.fedex;

import java.util.List;

public class TrackPackagesResponse {
    private boolean successful;
    private boolean passedLoggedInCheck;
    private List<ErrorListItem> errorList;
    private List<PackageListItem> packageList;

    public TrackPackagesResponse() {
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isPassedLoggedInCheck() {
        return passedLoggedInCheck;
    }

    public void setPassedLoggedInCheck(boolean passedLoggedInCheck) {
        this.passedLoggedInCheck = passedLoggedInCheck;
    }

    public List<ErrorListItem> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorListItem> errorList) {
        this.errorList = errorList;
    }

    public List<PackageListItem> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageListItem> packageList) {
        this.packageList = packageList;
    }

    @Override
    public String toString() {
        return "TrackPackagesResponse{" +
                "successful=" + successful +
                ", passedLoggedInCheck=" + passedLoggedInCheck +
                ", errorList=" + errorList +
                ", packageList=" + packageList +
                '}';
    }
}
