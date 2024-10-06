package com.ssuclass.networkconnection.networkservice;

import com.ssuclass.networkconnection.dto.CatFact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactService {
    @GET("fact")
    Call<CatFact> getCatFact();
}
