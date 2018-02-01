package com.example.ioana.exam.service;


import com.example.ioana.exam.domain.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("add")
    Call<Project> addIdea(@Body Project project);

    @DELETE("delete/{id}")
    Call<Project> deleteIdea(@Path("id") int id);

    //APIs for project section

    @GET("projects")
    Call<List<Project>> getProjects();

    @DELETE("remove/{id}")
    Call<Project> deleteProject(@Path("id") int id);

    @POST("promote")
    Call<Project> promoteIdea(@Body Project project);

    @POST("approve")
    Call<Project> approveProject(@Body Project project);

    @POST("discard")
    Call<Project> discardProject(@Body Project project);
}
