package com.moyuchen.moreprogressplayaudio.test;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Util;
import com.moyuchen.moreprogressplayaudio.R;

import java.util.List;

public class MyBaseAdapter extends BaseQuickAdapter<MediaEntity, BaseViewHolder> implements AutioControl.AutioControlListener, BaseQuickAdapter.OnItemChildClickListener {


    private AutioControl autioControl;
    private boolean isShowPlay = false;
    private int isPlayingPosition;


    public MyBaseAdapter(@Nullable List<MediaEntity> data, AutioControl control) {
        super(R.layout.item_audio, data);
        autioControl = control;
        autioControl.setOnAutioControlListener(this);
        setOnItemChildClickListener(this);
    }

    public void update(boolean isShowPlay) {
        this.isShowPlay = isShowPlay;
        notifyDataSetChanged();

    }

    @Override
    protected void convert(BaseViewHolder helper, final MediaEntity item) {

        final TextView startTime = helper.getView(R.id.tv_start_time);
        TextView endTime = helper.getView(R.id.tv_end_time);
        TextView content = helper.getView(R.id.item_content);
        ImageView play = helper.getView(R.id.play);
        ImageView pause = helper.getView(R.id.pause);
        TextView sendContent = helper.getView(R.id.tv_send_layout_content);
        DefaultTimeBar progress = helper.getView(R.id.exo_progress);
        progress.setPosition(0);
        switch (item.type) {
            case MediaEntity.RECIEVE:
                helper.getView(R.id.receive_layout).setVisibility(View.VISIBLE);
                helper.getView(R.id.send_layout).setVisibility(View.GONE);
                content.setText(item.getContent());

                if (isShowPlay) {
                    startTime.setVisibility(View.VISIBLE);
                    endTime.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    if (item.playStatus) {
                        play.setVisibility(View.GONE);
                        pause.setVisibility(View.VISIBLE);
                    } else {
                        play.setVisibility(View.VISIBLE);
                        pause.setVisibility(View.GONE);
                    }
                } else {
                    startTime.setVisibility(View.GONE);
                    endTime.setVisibility(View.GONE);
                    play.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);

                }
                break;
            case MediaEntity.SEND:
                helper.getView(R.id.receive_layout).setVisibility(View.GONE);
                helper.getView(R.id.send_layout).setVisibility(View.VISIBLE);
                sendContent.setText(item.getContent());

                break;
        }


        helper.addOnClickListener(R.id.play);
        helper.addOnClickListener(R.id.pause);

        startTime.setText(item.getStartTime());
        endTime.setText(item.getEndTime());
        DefaultTimeBar timeBar = (DefaultTimeBar) helper.getView(R.id.exo_progress);
        timeBar.addListener(new TimeBar.OnScrubListener() {
            @Override
            public void onScrubStart(TimeBar timeBar, long position) {
            }

            @Override
            public void onScrubMove(TimeBar timeBar, long position) {
                if (startTime != null) {
                    if (autioControl.getPosition() == mData.indexOf(item)) {
                        startTime.setText(Util.getStringForTime(autioControl.getFormatBuilder(), autioControl.getFormatter(), position));
                    } else {
                        timeBar.setPosition(0);
                    }
                }
            }

            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                if (autioControl != null) {
                    if (autioControl.getPosition() == mData.indexOf(item)) {
                        autioControl.seekToTimeBarPosition(position);
                    } else {
                        timeBar.setPosition(0);
                    }
                }
            }
        });

    }

    @Override
    public void setCurPositionTime(int position, long curPositionTime) {
        Log.i(TAG, "setCurPositionTime: " + curPositionTime + ";position:" + position);
        DefaultTimeBar timeBar = (DefaultTimeBar) getViewByPosition(getRecyclerView(), position, R.id.exo_progress);
        if (position == isPlayingPosition) {
            timeBar.setPosition(curPositionTime);
        } else {
            timeBar.setPosition(0);
        }

    }

    @Override
    public void setDurationTime(int position, long durationTime) {
        DefaultTimeBar timeBar = (DefaultTimeBar) getViewByPosition(getRecyclerView(), position, R.id.exo_progress);
        timeBar.setDuration(durationTime);
    }

    @Override
    public void setBufferedPositionTime(int position, long bufferedPosition) {
        DefaultTimeBar timeBar = (DefaultTimeBar) getViewByPosition(getRecyclerView(), position, R.id.exo_progress);
        timeBar.setBufferedPosition(bufferedPosition);
    }

    @Override
    public void setCurTimeString(int position, String curTimeString) {
        TextView startTime = (TextView) getViewByPosition(getRecyclerView(), position, R.id.tv_start_time);
        MediaEntity mediaEntity = mData.get(position);
        if (position != isPlayingPosition) {
            startTime.setText("00:00");
            mediaEntity.setStartTime("00:00");
        } else {
            startTime.setText(curTimeString);
            mediaEntity.setStartTime(curTimeString);
        }

    }

    @Override
    public void isPlay(int position, boolean isPlay) {
        MediaEntity mediaEntity = mData.get(position);
        mediaEntity.setPlayStatus(isPlay);
        ImageView play = (ImageView) getViewByPosition(getRecyclerView(), position, R.id.play);
        ImageView pause = (ImageView) getViewByPosition(getRecyclerView(), position, R.id.pause);
        if (isPlay) {
            if (play != null) {
                play.setVisibility(View.INVISIBLE);
            }
            if (pause != null) {
                pause.setVisibility(View.VISIBLE);
            }
        } else {
            if (play != null) {
                play.setVisibility(View.VISIBLE);
            }
            if (pause != null) {
                pause.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void setDurationTimeString(int position, String durationTimeString) {
        TextView endTime = (TextView) getViewByPosition(getRecyclerView(), position, R.id.tv_end_time);
        MediaEntity mediaEntity = mData.get(position);
        mediaEntity.setEndTime(durationTimeString);
        endTime.setText(durationTimeString);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean playNClickIsSame = playNClickIsSame(autioControl.getPosition(), position);
        switch (view.getId()) {
            case R.id.play:
                initStatus(autioControl.getPosition(), position);
                autioControl.onPrepare(mData.get(position).getUri());
                autioControl.onStart(position);
                isPlayingPosition = position;
                break;
            case R.id.pause:
                if (playNClickIsSame) {
                    autioControl.onPause();
                }

                break;
        }
    }

    private boolean playNClickIsSame(int playIndex, int clickIndex) {
        return playIndex == clickIndex ? true : false;
    }

    private void initStatus(int playIndex, int clickIndex) {
        MediaEntity oldEntity = mData.get(playIndex);
//        MediaEntity newEntity = mData.get(clickIndex);
        oldEntity.setPlayStatus(false);
        oldEntity.setStartTime("00:00");

        DefaultTimeBar timeBar = (DefaultTimeBar) getViewByPosition(getRecyclerView(), playIndex, R.id.exo_progress);
        timeBar.setPosition(0);
        timeBar.setBufferedPosition(0);

        TextView startTime = (TextView) getViewByPosition(getRecyclerView(), playIndex, R.id.tv_start_time);
        startTime.setText(oldEntity.getStartTime());

        ImageView oldplay = (ImageView) getViewByPosition(getRecyclerView(), playIndex, R.id.play);

        oldplay.setVisibility(View.VISIBLE);
        ImageView oldpause = (ImageView) getViewByPosition(getRecyclerView(), playIndex, R.id.pause);
        oldpause.setVisibility(View.INVISIBLE);

        ImageView newplay = (ImageView) getViewByPosition(getRecyclerView(), clickIndex, R.id.play);
        newplay.setVisibility(View.INVISIBLE);
        ImageView onewpause = (ImageView) getViewByPosition(getRecyclerView(), clickIndex, R.id.pause);
        onewpause.setVisibility(View.VISIBLE);


    }
}
