package s2017s40.kr.hs.mirim.mirimitshow.DTO;

import java.util.ArrayList;

public class GroupDTO {
    private String token;
    private String name;
    private String member[];
    private String boards[];
    private String imgToken;
    private ArrayList<Object> arrayList;
    private String url;
    private String type;
    //기본 생성자
    GroupDTO(){}
    //Group1테스트 생성자
    GroupDTO(String boards[]){

    }
    //Group테스트 생성자
    GroupDTO(String imgToken, String url){
        this.imgToken = imgToken;
        this.url = url;
    }
    //이미지 이용시 생성자
    GroupDTO(String imgToken, ArrayList arrayList, String url, String type){
        this.imgToken = imgToken;
        this.arrayList = arrayList;
        this.url = url;
        this.type = type;
    }
    //리스트뷰 이용시 생성자
    GroupDTO(String token, String name, String member[], String url, String type){
        this.token = token;
        this.name = name;
        this.member = member;
        this.url = url;
        this.type = type;
    }
    //전체 생성자
    GroupDTO(String token, String name, String member[], String boards[], String imgToken, ArrayList arrayList, String url, String type){
        this.token = token;
        this.name = name;
        this.member = member;
        this.boards = boards;
        this.imgToken = imgToken;
        this.arrayList = arrayList;
        this.url = url;
        this.type = type;
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

    public void setMember(String[] member) {

        this.member = member;
    }

    public void setImgToken(String imgToken) {
        this.imgToken = imgToken;
    }

    public void setArrayList(ArrayList<Object> arrayList) {
        this.arrayList = arrayList;
    }

    public void setBoards(String[] boards) {
        this.boards = boards;
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

    public ArrayList<Object> getArrayList() {
        return arrayList;
    }

    public String[] getMember() {
        return member;
    }

    public String[] getBoards() {
        return boards;
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
