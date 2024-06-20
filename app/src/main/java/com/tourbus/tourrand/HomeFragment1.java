package com.tourbus.tourrand;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private TripPlanAdapter adapter;
    private List<TripPlan> tripPlans;
    private TextView shakeText1;
    private TextView shakeText2;
    private Button logoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home1, container, false);

        // RecyclerView 초기화
        recyclerView = rootView.findViewById(R.id.recycler_view_trip_plans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 임시 데이터 생성 (실제 데이터는 네트워크 요청 또는 로컬 DB에서 가져와야 함)
        tripPlans = new ArrayList<>();
        tripPlans.add(new TripPlan("여행 이름 1", "2024-06-20", "2"));
        tripPlans.add(new TripPlan("여행 이름 2", "2024-07-15", "30"));
        tripPlans.add(new TripPlan("여행 이름 3", "2024-08-05", "50"));
        tripPlans.add(new TripPlan("여행 이름 4", "2024-09-10", "90"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));
        tripPlans.add(new TripPlan("여행 이름 5", "2024-10-20", "120"));

        // Adapter 설정
        adapter = new TripPlanAdapter(getActivity(), tripPlans);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        // 애니메이션 설정 및 시작
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        shakeText1 = rootView.findViewById(R.id.shakeText1);
        shakeText2 = rootView.findViewById(R.id.shakeText2);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                new Handler().postDelayed(() -> {
                    shakeText1.startAnimation(shake);
                    shakeText2.startAnimation(shake);
                }, 1000); // 1초 딜레이 추가
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        System.out.println("email = " + SplashActivity.currentUser.getEmail());

        logoutBtn = rootView.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        SplashActivity.currentUser.deleteUser();
                        System.out.println("currentUser deleted");


                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        finish();

                        return null;
                    }
                });
            }
        });

        return rootView;
    }
}
