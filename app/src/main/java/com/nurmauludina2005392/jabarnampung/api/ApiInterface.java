package com.nurmauludina2005392.jabarnampung.api;

import com.nurmauludina2005392.jabarnampung.model.login.Login;
import com.nurmauludina2005392.jabarnampung.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

}
