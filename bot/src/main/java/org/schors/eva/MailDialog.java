package org.schors.eva;

public class MailDialog {
    private String email;
    private MailState state;

    public MailDialog() {
    }

    public MailDialog(MailState state, String email) {
        this.state = state;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MailState getState() {
        return state;
    }

    public void setState(MailState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MailDialog{" +
                "email='" + email + '\'' +
                ", state=" + state +
                '}';
    }
}
