package com.example.ioana.gamestore.service;


import com.example.ioana.gamestore.domain.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ioana on 30/01/2018.
 */

/**
 * Service is an interface that listens to a given url (endpoint) and retrieves data, using the
 * exposed APIs from the server.
 */
public interface Service {
    String SERVICE_ENDPOINT = "http://192.168.2.111:4001"; //acasa
//    String SERVICE_ENDPOINT = "http://192.168.0.178:4001"; //mobil


    //APIs for client section

    @GET("games")
    Call<List<Game>> getGames();

    @POST("buyGame")
    Call<Game> buyGame(@Body Game game);

    @POST("returnGame")
    Call<Game> returnGame(@Body Game game);

    @POST("rentGame")
    Call<Game> rentGame(@Body Game game);

    //APIs for employee section

    @GET("all")
    Call<List<Game>> getAllGames();

    @POST("addGame")
    Call<Game> addGame(@Body Game game);

    @DELETE("removeGame")
    Call<Game> deleteGame(@Body Game game);

    @POST("updateGame")
    Call<Game> updateGame(@Body Game game);

}
