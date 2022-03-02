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
	
	private int flag = 1;//����һ����־�����������ʼ/��ͣ����ťʹ��
	private MusicService musicService = new MusicService();
	private Handler handler;// ����ı�������¼�
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
                     * ����flag��Ϊ��־����flagΪ1 ��ʱ�򣬴�ʱplayer��û�ж���������ִ��musicService.play()����
                     * ���е�һ�β��ţ�Ȼ��flag���������ٽ��е�һ�β���
                     * ���ٴε������ʼ/��ͣ����ť����������1 ��ִ����ͣ���������goplay()����
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
                    Log.i("LAT", "��ʼ�쳣��");
                }

            }
        });
		stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicService.stop();
                    playimage.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    flag = 1;//�����ֹͣ��ťʱ��flag��Ϊ1
                    seekbar.setProgress(0);
                } catch (Exception e) {
                    Log.i("LAT", "ֹͣ�쳣��");
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
                    Log.i("LAT", "��һ���쳣��");
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
                    Log.i("LAT", "��һ���쳣��");
                }

            }
        });
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//���ڼ���SeekBar����ֵ�ĸı�
            	
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//���ڼ���SeekBar��ʼ�϶�

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {//���ڼ���SeekBarֹͣ�϶�  SeekBarֹͣ�϶�����¼�
                int progress = seekBar.getProgress();
                Log.i("TAG:", "" + progress + "");
                int musicMax = musicService.player.getDuration(); //�õ����׸��������
                int seekBarMax = seekBar.getMax();
                musicService.player
                        .seekTo(musicMax * progress / seekBarMax);//������������
            }
        });
		
		Thread t = new Thread(this);// �Զ��ı���������߳�
        //ʵ����һ��handler����
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
                position = musicService.getCurrentProgress();//�õ���ǰ�������Ž���(��)
                mMax = musicService.player.getDuration();//�������
                sMax = seekbar.getMax();//seekBar���ֵ����ٷֱ�
                Message m = handler.obtainMessage();//��ȡһ��Message
                m.arg1 = position * sMax / mMax;//seekBar�������İٷֱ�
                m.arg2 = position;
                m.what = UPDATE;
                handler.sendMessage(m);
                try {
                    Thread.sleep(1000);// ÿ���1�뷢��һ�θ�����Ϣ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	

	
}
