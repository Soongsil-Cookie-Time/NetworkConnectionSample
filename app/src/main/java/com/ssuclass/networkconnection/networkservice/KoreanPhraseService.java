package com.ssuclass.networkconnection.networkservice;

import com.ssuclass.networkconnection.dto.KoreanPhrase;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KoreanPhraseService {
    @GET("advice")
    Call<KoreanPhrase> getKoreanPhrase();
}
