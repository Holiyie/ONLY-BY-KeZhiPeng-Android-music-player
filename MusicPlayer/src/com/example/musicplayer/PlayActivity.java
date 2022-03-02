package com.example.musicplayer;

import java.io.File;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class PlayActivity extends Activity implements Runnable {
	
	private int flag = 1;//设置一个标志，供点击“开始/暂停”按钮使用
	private MusicService musicService = new MusicService();
	private Handler handler;// 处理改变进度条事件
    int UPDATE = 0x101;
	ImageView playimage;
	SeekBar seekbar;
	Button last;
	Button play;
	Button stop;
	Button next;
	
	Intent intent;
	String pathway;
	File file;

	@SuppressLint("HandlerLeak") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		last=(Button) findViewById(R.id.last);
		play=(Button) findViewById(R.id.play);
		stop=(Button) findViewById(R.id.pause);
		next=(Button) findViewById(R.id.next);
		seekbar=(SeekBar) findViewById(R.id.seekBar);
		playimage=(ImageView) findViewById(R.id.playimage);
		play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /**
                     * 引入flag作为标志，当flag为1 的时候，此时player内没有东西，所以执行musicService.play()函数
                     * 进行第一次播放，然后flag自增二不再进行第一次播放
                     * 当再次点击“开始/暂停”按钮次数即大于1 将执行暂停或继续播放goplay()函数
                     */
                    if (flag == 1) {
                        musicService.play();
                        playimage.setImageDrawable(getResources().getDrawable(R.drawable.play));
                        flag++;
                    } else {
                        if (!musicService.player.isPlaying()) {
                            musicService.goPlay();
                            playimage.setImageDrawable(getResources().getDrawable(R.drawable.play));
                        } else if (musicService.player.isPlaying()) {
                            musicService.pause();
                            playimage.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                        }
                    }
                } catch (Exception e) {
                    Log.i("LAT", "开始异常！");
                }

            }
        });
		stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicService.stop();
                    playimage.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    flag = 1;//当点击停止按钮时，flag置为1
                    seekbar.setProgress(0);
                } catch (Exception e) {
                    Log.i("LAT", "停止异常！");
                }

            }
        });
		
		last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicService.last();
                    playimage.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    seekbar.setProgress(0);
                } catch (Exception e) {
                    Log.i("LAT", "上一曲异常！");
                }

            }
        });
		next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicService.next();
                    playimage.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    seekbar.setProgress(0);
                } catch (Exception e) {
                    Log.i("LAT", "下一曲异常！");
                }

            }
        });
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//用于监听SeekBar进度值的改变
            	
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//用于监听SeekBar开始拖动

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {//用于监听SeekBar停止拖动  SeekBar停止拖动后的事件
                int progress = seekBar.getProgress();
                Log.i("TAG:", "" + progress + "");
                int musicMax = musicService.player.getDuration(); //得到该首歌曲最长秒数
                int seekBarMax = seekBar.getMax();
                musicService.player
                        .seekTo(musicMax * progress / seekBarMax);//跳到该曲该秒
            }
        });
		
		Thread t = new Thread(this);// 自动改变进度条的线程
        //实例化一个handler对象
        handler = new Handler() {
            @SuppressLint("HandlerLeak") @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == UPDATE) {
                    try {
                        seekbar.setProgress(msg.arg1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    seekbar.setProgress(0);
                }
            }
        };
        t.start();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int position, mMax, sMax;
        while (!Thread.currentThread().isInterrupted()) {
            if (musicService.player != null && musicService.player.isPlaying()) {
                position = musicService.getCurrentProgress();//得到当前歌曲播放进度(秒)
                mMax = musicService.player.getDuration();//最大秒数
                sMax = seekbar.getMax();//seekBar最大值，算百分比
                Message m = handler.obtainMessage();//获取一个Message
                m.arg1 = position * sMax / mMax;//seekBar进度条的百分比
                m.arg2 = position;
                m.what = UPDATE;
                handler.sendMessage(m);
                try {
                    Thread.sleep(1000);// 每间隔1秒发送一次更新消息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	

	
}
