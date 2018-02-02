package com.example.ioana.exam.service;


import com.example.ioana.exam.domain.Seat;
import com.example.ioana.exam.domain.Text;

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
    //    String SERVICE_ENDPOINT = "http://192.168.2.111:4026"; //acasa
    String SERVICE_ENDPOINT = "http://192.168.0.178:4021"; //mobil


    //APIs for client section

    @GET("seats")
    Call<List<Seat>> getSeats();

    @POST("reserve")
    Call<Seat> reserveSeat(@Body Seat seat);

    @GET("refresh/{id}")
    Call<List<Seat>> refreshSeat(@Path("id") int id);

    @POST("buy")
    Call<Seat> buySeat(@Body Seat seat);


    //APIs for theater section

    @GET("all")
    Call<List<Seat>> getAll();

    @POST("confirm")
    Call<Seat> confirmSeat(@Body Seat seat);


    @DELETE("clean")
    Call<Text> cleanSeats();

    //APIs for ADMIN section

    @GET("confirmed")
    Call<List<Seat>> getConfirmed();

    @GET("taken")
    Call<List<Seat>> getTaken();

    @DELETE("zap")
    Call<Text> deleteAll();


}
