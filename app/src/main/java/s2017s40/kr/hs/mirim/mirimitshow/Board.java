package s2017s40.kr.hs.mirim.mirimitshow;

public class Board {
    private String group_token;
    private String token;
    private Boolean isNotice;
    private String author;
    private String title;
    private String content;
    private String date;
    private image image;
    public  Board(String author, String title){
        this.author = author;
        this.title = title;
    }
    public  Board(String group_token, Boolean isNotice, String author, String title,String content){
        this.group_token = group_token;
        this.isNotice = isNotice;
        this.author = author;
        this.title = title;
        this.content = content;
    }
    public Board(String group_token, Boolean isNotice, String author, String title ,String content ,String img) {
        this.group_token = group_token;
        this.isNotice = isNotice;
        this.author = author;
        this.title = title;
        this.content = content;
        this.image = new image(img);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setImage(s2017s40.kr.hs.mirim.mirimitshow.image image) {
        this.image = image;
    }

    public s2017s40.kr.hs.mirim.mirimitshow.image getImage() {
        return image;
    }

    public void setIsNotice(Boolean isNotice) {
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

    public String getTitle() {
        return title;
    }

    public Boolean getIsNotice() {
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
