package im.fir.module;

public class App {
    private String appPath;
    private String iconPath;
    private String name = "";
    private String bundleId = "";
    private String version = "";
    private String build = "";
    private String releaseType = "";
    private String appType;
    private String changeLog = "";

    public String getAppPath() {
        return this.appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getReleaseType() {
        return this.releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getChangeLog() {
        return this.changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "app path---- " + appPath + " icon path---- " + " name----" + name + "bundleId----"
                + bundleId + "version----" + version + " build----" + build + " releaseType----" + releaseType + " appType----" + appType + " changeLog----" + changeLog;
    }
}
