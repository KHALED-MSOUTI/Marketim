package com.example.marketim.api;

import com.example.marketim.model.jsonList;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/")
    Observable<ArrayList<jsonList>> getData();
}
