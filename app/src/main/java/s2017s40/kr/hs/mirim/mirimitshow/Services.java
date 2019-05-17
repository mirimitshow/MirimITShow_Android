package s2017s40.kr.hs.mirim.mirimitshow;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface Services {

    @POST("/signin")
    Call<User> signin(
            @Body User user);
    @POST("/signup")
    Call<Register> signup(
            @Body Register register);
    @POST("/setGroup")
    Call<Group> setgroup(
            @Body Group group);
    @GET("/getGroup/")
    Call<Group> getGroup(

    )
}