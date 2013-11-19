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
