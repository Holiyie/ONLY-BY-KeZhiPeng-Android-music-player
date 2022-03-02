package com.example.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class MusicService {
    private static final File PATH = Environment.getExternalStorageDirectory();// ��ȡSD����Ŀ¼��
    public List<String> musicList;// ����ҵ�������mp3�ľ���·����
    public MediaPlayer player; // �����ý�����
    public int songNum; // ��ǰ���ŵĸ�����List�е��±�,flagΪ����
    public String songName; // ��ǰ���ŵĸ�����

    class MusicFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
        	if (name.endsWith(".mp3")){
        		return (name.endsWith(".mp3"));//���ص�ǰĿ¼������.mp3��β���ļ�
        	}
        	if(name.endsWith(".wav")){
        		return (name.endsWith(".wav"));//���ص�ǰĿ¼������.wav��β���ļ�
        	} 
        	
        	return false;
            
        }
    }

    public MusicService() {
        super();
        player = new MediaPlayer();
        musicList = new ArrayList<String>();
        try {
            File MUSIC_PATH = new File(PATH, "Music");//��ȡMusic�ļ��Ķ���Ŀ¼
            if (MUSIC_PATH.listFiles(new MusicFilter()).length > 0) {
                for (File file : MUSIC_PATH.listFiles(new MusicFilter())) {
                    musicList.add(file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            Log.i("TAG", "��ȡ�ļ��쳣");
        }
    }

    public void setPlayName(String dataSource) {
        File file = new File(dataSource);//����ΪD:\\mm.mp3
        String name = file.getName();//name=mm.mp3
        int index = name.lastIndexOf(".");//�ҵ����һ��.
        songName = name.substring(0, index);//��ȡΪmm
    }

    public void play() {
        try {
                player.reset(); //���ö�ý��
                String dataSource = musicList.get(songNum);//�õ���ǰ�������ֵ�·��
                setPlayName(dataSource);//��ȡ����
                // ָ������Ϊ��Ƶ�ļ�
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(dataSource);//Ϊ��ý��������ò���·��
                player.prepare();//׼������
                player.start();//��ʼ����
                //setOnCompletionListener ����ǰ��ý����󲥷����ʱ�������¼�
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer arg0) {
                        next();//�����ǰ�����������,�Զ�������һ��.
                    }
                });

        } catch (Exception e) {
            Log.v("MusicService", e.getMessage());
        }
    }

    //��������
    public  void goPlay(){
        int position = getCurrentProgress();
        player.seekTo(position);//���õ�ǰMediaPlayer�Ĳ���λ�ã���λ�Ǻ��롣
        try {
            player.prepare();//  ͬ���ķ�ʽװ����ý���ļ���
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.start();
    }
    // ��ȡ��ǰ����
    public int getCurrentProgress() {
        if (player != null & player.isPlaying()) {
            return player.getCurrentPosition();
        } else if (player != null & (!player.isPlaying())) {
            return player.getCurrentPosition();
        }
        return 0;
    }

    public void next() {
        songNum = songNum == musicList.size() - 1 ? 0 : songNum + 1;
        play();
    }

    public void last() {
        songNum = songNum == 0 ? musicList.size() - 1 : songNum - 1;
        play();
    }
    // ��ͣ����
    public void pause() {
        if (player != null && player.isPlaying()){
            player.pause();
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.reset();
        }
    }
}
