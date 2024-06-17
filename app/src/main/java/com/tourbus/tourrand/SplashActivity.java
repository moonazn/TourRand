package com.tourbus.tourrand;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // VideoView 설정
        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_white_72030);
        videoView.setVideoURI(videoUri);

        // 비디오 재생 시작
        videoView.start();

        // 3초 후 메인으로 이동
        moveMain(2.5);
    }

    private void moveMain(double sec) {
        int delayMillis = (int) (sec * 1000); // 초를 밀리초로 변환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 새로운 Intent 생성
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                // Intent에 명시된 액티비티로 이동
                startActivity(intent);
                overridePendingTransition(0, 0);

                // 현재 액티비티 종료
                finish();
            }
        }, delayMillis); // sec초 정도 딜레이를 준 후 시작
    }
}
