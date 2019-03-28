package s2017s40.kr.hs.mirim.mirimitshow;

public class SubGroup1DTO {
    String title;
    String sub;

    SubGroup1DTO(String title, String sub){
        this.title = title;
        this.sub = sub;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public String getSub() {
        return sub;
    }
}
