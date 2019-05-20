package s2017s40.kr.hs.mirim.mirimitshow;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupList {
    @SerializedName("data")
    private List<Group> groups;
    GroupList(List<Group> group){
        this.groups = group;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
