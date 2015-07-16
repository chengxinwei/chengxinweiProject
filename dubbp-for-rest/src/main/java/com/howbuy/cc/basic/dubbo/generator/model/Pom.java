package com.howbuy.cc.basic.dubbo.generator.model;

/**
 * Created by xinwei.cheng on 2015/7/10.
 */
public class Pom {

    private String version;
    private String groupId;
    private String artifactId;
    private String jarName;
    private String fullJarPath;
    private ClassLoader classLoader;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getFullJarPath() {
        return fullJarPath;
    }

    public void setFullJarPath(String fullJarPath) {
        this.fullJarPath = fullJarPath;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pom pom = (Pom) o;

        if (groupId != null ? !groupId.equals(pom.groupId) : pom.groupId != null) return false;
        return !(artifactId != null ? !artifactId.equals(pom.artifactId) : pom.artifactId != null);

    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        return result;
    }
}
