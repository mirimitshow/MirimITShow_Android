package s2017s40.kr.hs.mirim.mirimitshow;

public class GroupDTO {
    String token;
    String name;
    String member[];
    String boards[];
    GroupDTO(){}
    GroupDTO(String token, String name, String member[], String boards[]){
        this.token = token;
        this.name = name;
        this.member = member;
        this.boards = boards;
    }
}
