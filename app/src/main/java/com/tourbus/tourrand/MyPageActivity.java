package com.tourbus.tourrand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MyPageActivity extends AppCompatActivity {

    private TextView name, email;
    private ImageView profileImg;

    boolean isPopupOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        name = findViewById(R.id.welcome);
        email = findViewById(R.id.email);
        profileImg = findViewById(R.id.profileImg);

        name.setText(SplashActivity.currentUser.getName() + "님, 환영합니다!");
        email.setText("이메일 : " + SplashActivity.currentUser.getEmail());

        Glide.with(profileImg).load(SplashActivity.currentUser.getProfileImage())
                .circleCrop().into(profileImg);


    }
}