package com.tourbus.tourrand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kakao.sdk.common.model.AuthErrorCause;
import com.kakao.sdk.user.UserApiClient;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView kakaoLoginButton = findViewById(R.id.btn_kakao_login);
        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                finish();

                UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, (oAuthToken, error) -> {
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error);
                        if (error.toString().contains(AuthErrorCause.AccessDenied.toString())) {
                            showLoginFailedDialog("로그인 권한이 거부되었습니다.");
                        } else if (error.toString().contains(AuthErrorCause.InvalidClient.toString())) {
                            showLoginFailedDialog("유효하지 않은 앱입니다.");
                        } else if (error.toString().contains(AuthErrorCause.InvalidGrant.toString())) {
                            showLoginFailedDialog("인증 수단이 유효하지 않습니다.");
                        } else if (error.toString().contains(AuthErrorCause.InvalidRequest.toString())) {
                            showLoginFailedDialog("요청 파라미터 오류입니다.");
                        } else if (error.toString().contains(AuthErrorCause.InvalidScope.toString())) {
                            showLoginFailedDialog("유효하지 않은 scope ID입니다.");
                        } else if (error.toString().contains(AuthErrorCause.Misconfigured.toString())) {
                            showLoginFailedDialog("설정이 올바르지 않습니다.");
                        } else if (error.toString().contains(AuthErrorCause.ServerError.toString())) {
                            showLoginFailedDialog("서버 내부 에러입니다.");
                        } else if (error.toString().contains(AuthErrorCause.Unauthorized.toString())) {
                            showLoginFailedDialog("앱에 요청 권한이 없습니다.");
                        } else if (error.toString().contains("user cancelled.")) {
                            showLoginFailedDialog("사용자가 로그인을 취소했습니다.");
                        } else {
                            showLoginFailedDialog("알 수 없는 오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    } else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공");

                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                Log.i(TAG, "사용자 정보 요청 성공: " + user.getKakaoAccount().getProfile().getNickname());

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                            return null;
                        });
                    }
                    return null;
                });
            }
        });
    }
    private void showLoginFailedDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("로그인 실패")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}