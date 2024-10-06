package com.ssuclass.networkconnection.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssuclass.networkconnection.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";
    private ActivitySecondBinding binding;
    private FirebaseAuth mAuth;
    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        this.setAuthenticationButtonListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    /**
     * Firebase Auth SDK를 연결해서 SignUp & SignIn 비즈니스 로직
     */
    private void setAuthenticationButtonListener() {
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondActivity.this.getEmailAndPassword();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SecondActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail: success");
                                    Toast.makeText(SecondActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.w(TAG, "createUserWithEmail: failure");
                                    Toast.makeText(SecondActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondActivity.this.getEmailAndPassword();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SecondActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signinWithEmailAndPassword: success");
                                    Toast.makeText(SecondActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, "signinWithEmailAndPassword: failure");
                                    Toast.makeText(SecondActivity.this, "Sign In Failure", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    /**
     * EditText에서 입력된 데이터값을 가져오는 메소드
     */
    private void getEmailAndPassword() {
        email = binding.inputEmailEditText.getText().toString();
        password = binding.inputPasswordEditText.getText().toString();
    }
}
