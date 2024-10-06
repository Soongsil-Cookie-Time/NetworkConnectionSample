package com.ssuclass.networkconnection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ssuclass.networkconnection.databinding.ActivityMainBinding;
import com.ssuclass.networkconnection.dto.CatFact;
import com.ssuclass.networkconnection.dto.KoreanPhrase;
import com.ssuclass.networkconnection.networkservice.CatApiClient;
import com.ssuclass.networkconnection.networkservice.CatFactService;
import com.ssuclass.networkconnection.networkservice.KoreanPhraseApiClient;
import com.ssuclass.networkconnection.networkservice.KoreanPhraseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String tag = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.setOnGotoSecondActivityButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 메모리 누수를 방지하기 위해 binding 해제
        binding = null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.fetchAndDisplayCatData();
        this.fetchAndDisplayKoreanData();
    }

    /**
     * 데이터를 가져오는 메소드
     */
    private void fetchAndDisplayCatData() {
        CatFactService service = CatApiClient.getClient().create(CatFactService.class);
        Call<CatFact> call = service.getCatFact();
        call.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(@NonNull Call<CatFact> call, @NonNull Response<CatFact> response) {
                if (response.isSuccessful()) {
                    CatFact catFact = response.body();
                    if (catFact != null) {
                        Log.v(tag, catFact.getFact());
                        binding.catFactTextView.setText(catFact.getFact());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "데이터 가져오기 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CatFact> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "API 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 데이터를 가져오는 메소드
     */
    private void fetchAndDisplayKoreanData() {
        KoreanPhraseService service = KoreanPhraseApiClient.getClient().create(KoreanPhraseService.class);
        Call<KoreanPhrase> call = service.getKoreanPhrase();
        call.enqueue(new Callback<KoreanPhrase>() {
            @Override
            public void onResponse(@NonNull Call<KoreanPhrase> call, @NonNull Response<KoreanPhrase> response) {
                if (response.isSuccessful()) {
                    KoreanPhrase koreanPhrase = response.body();
                    if (koreanPhrase != null) {
                        Log.v(tag, koreanPhrase.getAuthor());
                        Log.v(tag, koreanPhrase.getAuthorProfile());
                        Log.v(tag, koreanPhrase.getMessage());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "API 연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<KoreanPhrase> call, @NonNull Throwable throwable) {
                Toast.makeText(MainActivity.this, "API 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnGotoSecondActivityButton() {
        binding.secondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}