package s2017s40.kr.hs.mirim.mirimitshow;

public class MyPaPerDTO {
    private String title;
    private String content;
    public MyPaPerDTO(String title, String content){
        this.content = content;
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
