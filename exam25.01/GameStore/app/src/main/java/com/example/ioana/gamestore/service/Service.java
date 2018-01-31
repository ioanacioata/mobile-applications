package com.example.ioana.gamestore.service;


import com.example.ioana.gamestore.domain.Game;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Ioana on 30/01/2018.
 */

/**
 * Service is an interface that listens to a given url (endpoint) and retrieves data, using the
 * exposed APIs from the server.
 */
public interface Service {
        String SERVICE_ENDPOINT ="http://192.168.2.117:4001"; //acasa
//    String SERVICE_ENDPOINT = "http://192.168.0.178:4001"; //mobil


    //APIs for client section

    @GET("games")
    Observable<List<Game>> getGames();

    @POST("buyGame")
    Observable<Game> buyGame(@Body Game game);

    @POST("returnGame")
    Observable<Game> returnGame(@Body Game game);

    @POST("rentGame")
    Observable<Game> rentGame(@Body Game game);

    //APIs for employee section

    @GET("all")
    Observable<List<Game>> getAllGames();

    @POST("addGame")
    Observable<Game> addGame(@Body Game game);

    @DELETE("removeGame")
    Observable<Game> deleteGame(@Body Game game);

    @POST("updateGame")
    Observable<Game> updateGame(@Body Game game);

}
