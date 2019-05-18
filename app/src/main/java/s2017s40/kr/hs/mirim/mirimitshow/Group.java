package s2017s40.kr.hs.mirim.mirimitshow;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String token;
    private String name;
    private ArrayList<Members> members = new ArrayList<>();
    private ArrayList<Board> boards = new ArrayList<>();
    private String imgToken;
    private String url;
    private String type;
    //기본 생성자
    Group(){}
    //Group1테스트 생성자
    Group(String boards[]){

    }
    //그룹 생성 생성자
    public Group(String token, String name,  String member){
        this.token = token;
        this.name = name;
        this.members.add(new Members(member));
    }
    //리스트뷰 이용시 생성자
    Group(String token, String name, String member , String url, String type){
        this.token = token;
        this.name = name;
        this.url = url;
        this.type = type;
    }
    //전체 생성자
    Group(String token, String name, String member, String boards, String imgToken, ArrayList arrayList, String url, String type){
        this.token = token;
        this.name = name;
        this.imgToken = imgToken;
        this.url = url;
        this.type = type;
    }
    public void setMembers(ArrayList<Members> members) {
        this.members = members;
    }

    public ArrayList<Members> getMembers() {
        return members;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setImgToken(String imgToken) {
        this.imgToken = imgToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getImgToken() {
        return imgToken;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
