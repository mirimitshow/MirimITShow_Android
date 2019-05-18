package s2017s40.kr.hs.mirim.mirimitshow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<TimeTable> settimetable(
            @Body TimeTable timeTable);

    //User
    @GET("/getUser/")
    Call<String> getuser();
    @GET("/getUserGroups/{email}")
    Call<List<Group>> getusergroups(
            @Path("email") String email
    );

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