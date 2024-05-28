package com.tourbus.tourrand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kakao.sdk.user.UserApiClient;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
//hi
    //hello
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView kakaoLoginButton = findViewById(R.id.btn_kakao_login);
        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
//                Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
//                    @Override
//                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                        if (oAuthToken != null) {
//                            // 로그인 성공
//                            Log.i(TAG, "로그인 성공: " + oAuthToken.getAccessToken());
//                            // 추가적으로 사용자 정보를 가져와 회원가입 처리를 할 수 있습니다.
//                            fetchUserInfo();
//                        }
//                        if (throwable != null) {
//                            // 로그인 실패
//                            Log.e(TAG, "로그인 실패", throwable);
//                        }
//                        return null;
//                    }
//                };
//
//                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) {
//                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
//                } else {
//                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
//                }
            }
        });
    }

//    private void fetchUserInfo() {
//        UserApiClient.getInstance().me((user, throwable) -> {
//            if (throwable != null) {
//                Log.e(TAG, "사용자 정보 요청 실패", throwable);
//            } else if (user != null) {
//                Log.i(TAG, "사용자 정보 요청 성공: " + user.toString());
//                // user 객체에 사용자 정보가 담겨 있습니다.
//                // 여기서 서버에 사용자 정보를 보내어 회원가입 처리를 할 수 있습니다.
//            }
//            return null;
//        });
//    }
}