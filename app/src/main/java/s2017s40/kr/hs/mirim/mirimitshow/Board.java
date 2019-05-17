package s2017s40.kr.hs.mirim.mirimitshow;

public class Board {
    private String token;
    private String group_token;
    private Boolean isNotice;
    private String author;
    private String title;
    private String content;
    private String date;
    Board(String token, String group_token, Boolean isNotice, String author, String title
    ,String content, String date){
        this.token = token;
        this.group_token = group_token;
        this.isNotice = isNotice;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
