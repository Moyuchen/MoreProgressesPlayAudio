package com.moyuchen.moreprogressplayaudio.test;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.ImageView;


import com.moyuchen.moreprogressplayaudio.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 语音模拟Activity
 */
public class SpeechSimulationActivity extends AppCompatActivity {
    List<MediaEntity> mediaEntityList = new ArrayList<>();
    AutioControl control;
    private MyBaseAdapter myBaseAdapter;
    private RecyclerView recyclerView;
    private ImageView imgRight;
    private boolean isClickImgRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        initData();
        initView();


    }

    public void initView() {
        imgRight = findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickImgRight) {
                    isClickImgRight = false;
                    myBaseAdapter.update(isClickImgRight);
                } else {
                    isClickImgRight = true;
                    myBaseAdapter.update(isClickImgRight);
                }
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
         control = new AutioControl(this);
        myBaseAdapter = new MyBaseAdapter(mediaEntityList, control);
        myBaseAdapter.bindToRecyclerView(recyclerView);
    }

    public void initData() {
        mediaEntityList.add(new MediaEntity("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3", "00:00",
                "00:49", false, MediaEntity.RECIEVE, "你好啊，我是风雨"));
        mediaEntityList.add(new MediaEntity("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3", "00:00",
                "00:22", false, MediaEntity.RECIEVE, "nice to meet you !"));
        mediaEntityList.add(new MediaEntity("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3", "00:00",
                "00:49", false, MediaEntity.SEND, "nice to meet you too !"));
        mediaEntityList.add(new MediaEntity("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3", "00:00",
                "00:22", false, MediaEntity.SEND, "我是颤三"));
        mediaEntityList.add(new MediaEntity("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3", "00:00",
                "00:49", false, MediaEntity.RECIEVE, "那我叫你小三吧！"));
        mediaEntityList.add(new MediaEntity("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3", "00:00",
                "00:22", false, MediaEntity.SEND, "小三，不好挺，要不你叫我三哥吧！"));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        control.release();

    }
}
