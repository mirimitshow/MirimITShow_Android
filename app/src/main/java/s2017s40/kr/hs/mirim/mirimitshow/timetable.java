package s2017s40.kr.hs.mirim.mirimitshow;

public class timetable {
    private String url;
    private String id;
    timetable(String url){
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
