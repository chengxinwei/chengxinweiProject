package model;

/**
 * Created by xinwei.cheng on 2015/6/1.
 */

public class HelloKitty {
    private String id;

    private String name;

    @Override
    public String toString() {
        return "HelloKitty[" + "id=" + id + ", name=" + name + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
