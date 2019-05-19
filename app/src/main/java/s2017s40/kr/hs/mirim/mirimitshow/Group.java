package s2017s40.kr.hs.mirim.mirimitshow;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String token;
    private String name;
    private ArrayList<String> members = new ArrayList<>();
    private ArrayList<String> boards = new ArrayList<>();
    private String image;
    private String timetable;
    //기본 생성자
    Group(){}
    //Group1테스트 생성자
    Group(String boards[]){

    }
    //그룹 생성 생성자
    public Group(String token, String name,  String member) {
        this.token = token;
        this.name = name;
        this.members.add(member);
    }
    //전체 생성자
    Group(String token, String name, ArrayList<String> members,  ArrayList<String> boards, String image, String timetable){
        this.token = token;
        this.name = name;
        this.members = members;
        this.boards = boards;
        this.image = image;
        this.timetable = timetable;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void setBoards(ArrayList<String> boards) {
        this.boards = boards;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getTimetable() {
        return timetable;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public ArrayList<String> getBoards() {
        return boards;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
