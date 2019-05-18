package s2017s40.kr.hs.mirim.mirimitshow;

public class Board {
    private String token;
    private String group_token;
    private String isNotice;
    private String author;
    private String title;
    private String content;
    private String date;
    Board(String token, String group_token, String isNotice, String author, String title
            ,String content, String date){
        this.token = token;
        this.group_token = group_token;
        this.isNotice = isNotice;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIsNotice(String isNotice) {
        this.isNotice = isNotice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGroup_token(String group_token) {
        this.group_token = group_token;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

    public String getIsNotice() {
        return isNotice;
    }

    public String getGroup_token() {
        return group_token;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
