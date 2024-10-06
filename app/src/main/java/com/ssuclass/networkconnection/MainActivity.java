package com.ssuclass.networkconnection;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ssuclass.networkconnection.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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

        CatFactService service = ApiClient.getClient().create(CatFactService.class);
        Call<CatFact> call = service.getCatFact();
        call.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(Call<CatFact> call, Response<CatFact> response) {
                if (response.isSuccessful()) {
                    CatFact catFact = response.body();
                    if (catFact != null) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 메모리 누수를 방지하기 위해 binding 해제
        binding = null;
    }
}