package com.tourbus.tourrand;

import static android.util.Base64.encodeToString;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.AuthErrorCause;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView id, email;
    private Button logoutBtn;

    private ImageView kakaoLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kakaoLoginButton = findViewById(R.id.btn_kakao_login);
        logoutBtn = findViewById(R.id.logout);
        id = findViewById(R.id.semiText);
        email = findViewById(R.id.mainText);

        Log.d("getKeyHash",""+getKeyHash(MainActivity.this));

        Function2<OAuthToken,Throwable, Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드 ,
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG,"CallBack Method");
                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                    updateKakaoLoginUi();

                }else {
                    //로그인 실패
                    Log.e(TAG, "invoke: login fail" );
                }

                return null;
            }
        };
        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

//                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
//                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
//                }else{
//                    // 카카오톡이 설치되어 있지 않다면
//                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
//                }

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
    public static String getKeyHash(final Context context){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);
            if(packageInfo == null)
                return null;

            for(Signature signature : packageInfo.signatures){
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e){
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    private void showLoginFailedDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("로그인 실패")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void updateKakaoLoginUi() {

        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if (user != null) {

                    // 유저의 아이디
                    Log.d(TAG, "invoke: id =" + user.getId());
                    // 유저의 이메일
                    Log.d(TAG, "invoke: email =" + user.getKakaoAccount().getEmail());
                    // 유저의 닉네임
                    Log.d(TAG, "invoke: nickname =" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 성별
                    //Log.d(TAG, "invoke: gender =" + user.getKakaoAccount().getGender());
                    // 유저의 연령대
                    //Log.d(TAG, "invoke: age=" + user.getKakaoAccount().getAgeRange());


                    // 유저 닉네임 세팅해주기
                    id.setText("이름 : " + user.getId().toString());
                    // 유저 프로필 사진 세팅해주기
                    email.setText("이메일 : " + user.getKakaoAccount().getEmail());
                    Log.d(TAG, "invoke: profile = "+user.getKakaoAccount().getProfile().getThumbnailImageUrl());

                    // 로그인이 되어있으면
                    kakaoLoginButton.setVisibility(View.GONE);
                    logoutBtn.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                } else {
                    // 로그인 되어있지 않으면
                    //id.setText(null);
                    //email.setText(null);

                    kakaoLoginButton.setVisibility(View.VISIBLE);
                    logoutBtn.setVisibility(View.VISIBLE);


                }
                return null;
            }
        });
    }

}