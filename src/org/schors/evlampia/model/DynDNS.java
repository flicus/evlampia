package org.schors.evlampia.model;

/**
 * Created with IntelliJ IDEA.
 * User: flicus
 * Date: 08.07.13
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
public class DynDNS {
    private String domainName;
    private String userName;
    private String password;

    public DynDNS(String domainName, String userName, String password) {
        this.domainName = domainName;
        this.userName = userName;
        this.password = password;
    }

    public DynDNS() {
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DynDNS{" +
                "domainName='" + domainName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
