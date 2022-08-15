package net.playlegend.grouppermission.api;

public class Group {

    public int groupId;
    public String name;
    public String prefix;

    public Group(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public Group(int groupId, String name, String prefix) {
        this(name, prefix);
        this.groupId = groupId;
    }

}
