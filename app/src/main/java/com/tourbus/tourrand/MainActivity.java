package com.tourbus.tourrand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.AuthErrorCause;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView id, email;
    private Button logoutBtn;
    private ImageView kakaoLoginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kakaoLoginButton = findViewById(R.id.btn_kakao_login);
        logoutBtn = findViewById(R.id.logout);
        id = findViewById(R.id.semiText);
        email = findViewById(R.id.mainText);
        progressBar = findViewById(R.id.progress_bar);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                if (throwable != null) {
                    Log.e(TAG, "로그인 실패", throwable);
                    showLoginFailedDialog("로그인에 실패했습니다. 다시 시도해주세요.");
                } else if (oAuthToken != null) {
                    Log.i(TAG, "로그인 성공");
                    updateKakaoLoginUi();
                }
                return null;
            }
        };

        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });

        updateKakaoLoginUi();
    }

    private void showLoginFailedDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("로그인 실패")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    id.setText("이름 : " + user.getId().toString());
                    email.setText("이메일 : " + user.getKakaoAccount().getEmail());
                    kakaoLoginButton.setVisibility(View.GONE);
                    logoutBtn.setVisibility(View.VISIBLE);

                    // 로그인 성공 시 다음 화면으로 이동
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    id.setText(null);
                    email.setText(null);
                    kakaoLoginButton.setVisibility(View.VISIBLE);
                    logoutBtn.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }
}