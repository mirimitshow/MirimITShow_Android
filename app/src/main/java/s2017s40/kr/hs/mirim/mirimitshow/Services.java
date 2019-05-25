package s2017s40.kr.hs.mirim.mirimitshow;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @Multipart
    @POST("/setGroup")
    Call<ResponseBody> setgroup(
            //Group group = new Group(groupCodeStr,  editGroupName.getText().toString(), email , "");
            @Part("token") RequestBody token,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part file);

    @GET("/getGroup/{token}")
    Call<Group> getgroup(
            @Path("token") String token);
    @POST("/joinGroup")
    Call<JoinGroup> joingroup(
            @Body JoinGroup join);
    @Multipart
    @POST("/setTimetable")
    Call<ResponseBody> settimetable(
            @Part("token") RequestBody description,
            @Part MultipartBody.Part file);

    //User
    @GET("/getUser/")
    Call<String> getuser();
    @GET("/getUserGroups/{email}")
    Call<List<Group>> getusergroups(
            @Path("email") String email);

    //Board
    @POST("/setBoard")
    Call<ResponseBody> setboard(
            @Part("token") RequestBody token,
            @Part("group_token") RequestBody group_token,
            @Part("isNotice") RequestBody isNotice,
            @Part("author") RequestBody author,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part MultipartBody.Part file);
    @GET("/getBoard/{token}")
    Call<Board> getboard(
            @Path("token") String boardToken
    );
    @GET("/getGroupBoards/{token}")
    Call<List<Board>> getgroupboards(
            @Path("token") String token);

    //Scan
    @Multipart
    @POST("/setScan")
    Call<Scan> setscan(
            @Part("email") RequestBody email,
            @Part("cartegory") RequestBody cartegory,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part file);
    @GET("/getScans/")
    Call<List<String>> getscans(

    );
}