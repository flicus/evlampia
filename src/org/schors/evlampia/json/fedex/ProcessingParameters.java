package org.schors.evlampia.json.fedex;


import java.util.List;

public class ProcessingParameters {
    private boolean anonymousTransaction;
    private String clientId;
    private boolean returnDetailedErrors;
    private boolean returnLocalizedDateTime;

    public ProcessingParameters() {
    }

    public boolean isAnonymousTransaction() {
        return anonymousTransaction;
    }

    public void setAnonymousTransaction(boolean anonymousTransaction) {
        this.anonymousTransaction = anonymousTransaction;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isReturnDetailedErrors() {
        return returnDetailedErrors;
    }

    public void setReturnDetailedErrors(boolean returnDetailedErrors) {
        this.returnDetailedErrors = returnDetailedErrors;
    }

    public boolean isReturnLocalizedDateTime() {
        return returnLocalizedDateTime;
    }

    public void setReturnLocalizedDateTime(boolean returnLocalizedDateTime) {
        this.returnLocalizedDateTime = returnLocalizedDateTime;
    }

    @Override
    public String toString() {
        return "ProcessingParameters{" +
                "anonymousTransaction=" + anonymousTransaction +
                ", clientId='" + clientId + '\'' +
                ", returnDetailedErrors=" + returnDetailedErrors +
                ", returnLocalizedDateTime=" + returnLocalizedDateTime +
                '}';
    }
}
