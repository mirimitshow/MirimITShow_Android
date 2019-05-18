package s2017s40.kr.hs.mirim.mirimitshow;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Services {
    //Auth
    @POST("/signin")
    Call<User> signin(
            @Body User user);
    @POST("/signup")
    Call<Register> signup(
            @Body Register register);

    //Group
    @POST("/setGroup")
    Call<Group> setgroup(
            @Body Group group);
    @GET("/getGroup/")
    Call<Group> getgroup();
    @POST("/joinGroup")
    Call<Group> joingroup();
    @POST("/setTimetable")
    Call<Group> settimetable();

    //User
    @GET("/getUser/")
    Call<String> getuser();
    @GET("/getUserGroups/")
    Call<String> getusergroups();

    //Board
    @GET("/getBoard/")
    Call<String> getboard();
    @GET("/getGroupBoards/")
    Call<String> getgroupboards();
    @POST("/setBoard")
    Call<Board> setbeard();

    //Scan
     @POST("/setScan")
    Call<Scan> setscan();
    @GET("/getScans/")
    Call<String> getscans();
}