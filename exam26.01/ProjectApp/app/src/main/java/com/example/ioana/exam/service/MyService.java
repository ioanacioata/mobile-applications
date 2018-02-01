package com.example.ioana.exam.service;


import com.example.ioana.exam.domain.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ioana on 30/01/2018.
 */

/**
 * MyService is an interface that listens to a given url (endpoint) and retrieves data, using the
 * exposed APIs from the server.
 */
public interface MyService {
    String SERVICE_ENDPOINT = "http://192.168.2.111:4026"; //acasa
//    String SERVICE_ENDPOINT = "http://192.168.0.178:4026"; //mobil


    //APIs for idea section

    @GET("ideas")
    Call<List<Project>> getIdeas();


    @POST("returnProject")
    Call<Project> returnProject(@Body Project project);

    @POST("rentProject")
    Call<Project> rentProject(@Body Project project);

    //APIs for employee section

    @GET("all")
    Call<List<Project>> getAllProject();

    @POST("addProject")
    Call<Project> addProject(@Body Project project);

    @POST("removeProject")
//    @HTTP(method = "DELETE", path = "/removeProject", hasBody = true)
    Call<Project> deleteProject(@Body Project project);

    @POST("updateProject")
    Call<Project> updateProject(@Body Project project);

}
