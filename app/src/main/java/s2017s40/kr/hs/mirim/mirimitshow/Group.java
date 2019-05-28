package s2017s40.kr.hs.mirim.mirimitshow;

import android.icu.text.TimeZoneFormat;
import android.media.Image;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import s2017s40.kr.hs.mirim.mirimitshow.image;
public class Group {
    private String token;
    private String name;
    private String id;
    private List<Members> members = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
    private image image;
    private timetable timetable;
    //기본 생성자
    Group(){}
    //Group1테스트 생성자
    Group(String boards[]){

    }
    //그룹 생성 생성자
    public Group(String token, String name) {
        this.token = token;
        this.name = name;
    }
    //그룹 생성 생성자
    public Group(String token, String name,  String member) {
        this.token = token;
        this.name = name;
        this.members.add(new Members(member));
    }
    //리스트 생성자
    public Group( String name, String image,  List<Members> member) {
        this.name = name;
        this.image = new image(image);
        this.members = member;
    }
    //리스트 생성자
    public Group(String token, String name, String image,  List<Members> member) {
        this.token = token;
        this.name = name;
        this.image = new image(image);
        this.members = member;
    }
    //전체 생성자
    Group(String token, String name, List<Members> members,  List<Board> boards, String image, String timetable){
        this.token = token;
        this.name = name;
        this.members = members;
        this.timetable =new timetable(timetable);
    }

    public void setImage(image image) {
        this.image = image;
    }

    public image getImage() {
        return image;
    }

    public List<Board> getBoards() {
        return boards;
    }


    public List<Members> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTimetable(timetable timetable) {
        this.timetable = timetable;
    }

    public timetable getTimetable() {
        return timetable;
    }
    public void setMembers(ArrayList<Members> members) {
        this.members = members;
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
