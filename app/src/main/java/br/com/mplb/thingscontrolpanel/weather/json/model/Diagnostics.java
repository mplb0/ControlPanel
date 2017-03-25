
package br.com.mplb.thingscontrolpanel.weather.json.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diagnostics {

    @SerializedName("publiclyCallable")
    @Expose
    private String publiclyCallable;
    @SerializedName("url")
    @Expose
    private List<Url> url = null;
    @SerializedName("javascript")
    @Expose
    private Javascript javascript;
    @SerializedName("user-time")
    @Expose
    private String userTime;
    @SerializedName("service-time")
    @Expose
    private String serviceTime;
    @SerializedName("build-version")
    @Expose
    private String buildVersion;

    public String getPubliclyCallable() {
        return publiclyCallable;
    }

    public void setPubliclyCallable(String publiclyCallable) {
        this.publiclyCallable = publiclyCallable;
    }

    public List<Url> getUrl() {
        return url;
    }

    public void setUrl(List<Url> url) {
        this.url = url;
    }

    public Javascript getJavascript() {
        return javascript;
    }

    public void setJavascript(Javascript javascript) {
        this.javascript = javascript;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

}
