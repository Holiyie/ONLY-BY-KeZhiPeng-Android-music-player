package com.example.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {
		
    String _id;
	String namel;
	String pwdl;
	String sex;
	TextView messages;
	ImageView touxiang;
	ListView musiclist;
	
	private MusicService musicService = new MusicService();
	
	
	Intent intent;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		messages=(TextView) findViewById(R.id.messages);
		touxiang=(ImageView) findViewById(R.id.touxiang);
		musiclist=(ListView) findViewById(R.id.musiclist);		
		Intent intent1 =getIntent();
		_id=intent1.getStringExtra("_id");
		namel=intent1.getStringExtra("name");
		pwdl=intent1.getStringExtra("pwd");
		sex=intent1.getStringExtra("sex");
		messages.setText("昵称： "+namel+"\n性别： "+sex);
		if(sex.equals("男")){
			touxiang.setImageDrawable(getResources().getDrawable(R.drawable.boy));
		}else if(sex.equals("女")){
			touxiang.setImageDrawable(getResources().getDrawable(R.drawable.girl));
		}else{
			touxiang.setImageDrawable(getResources().getDrawable(R.drawable.notin));
		}
		inflateListView();
		touxiang.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
                intent = new Intent(ListActivity.this,SetMessageActivity.class);
				bundle = new Bundle();
                bundle.putString("_id",_id);
                bundle.putString("pwd",pwdl);
                bundle.putString("name",namel);
                bundle.putString("sex",sex);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
			}
			
		});	
		
		/*str = new String[musicService.musicList.size()];
        int i = 0;
        for (String path : musicService.musicList) {
            File file = new File(path);
            str[i++] = file.getName();
        }
        adapter = new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_1,
                str);
        //自动添加SD卡的Music文件下的音乐文件
        musiclist.setAdapter(adapter);*/
		
	
        
        //为ListView的列表项的单击事件绑定监听器
        musiclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                
            	Intent intent1=new Intent(ListActivity.this,PlayActivity.class );
                //用户单击了文件，进入播放界面
                startActivity(intent1);
            }    
        });
        //用户长按列表项删除歌曲
        musiclist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				//定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
				showNormalDialog(arg2);//删除对话框弹出
				return false;
				
				
			}
		});
        

		
		
		
	}
	
	@SuppressLint("ShowToast") 
	private void showNormalDialog(final int arg2){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = 
        new AlertDialog.Builder(ListActivity.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("你确定要删除吗？");
        normalDialog.setPositiveButton("确定", 
            new DialogInterface.OnClickListener() {
            @SuppressLint("ShowToast") @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	String music=musicService.musicList.get(arg2);
            	File file = new File(music);
                boolean ret=file.delete();
            	musicService.musicList.remove(arg2);
            	if(ret){
            		Toast.makeText(ListActivity.this, "删除成功！", Toast.LENGTH_SHORT);
            	}else{
            		Toast.makeText(ListActivity.this, "删除失败！", Toast.LENGTH_SHORT);
            	}
            	
            	inflateListView();
            }
        });
        normalDialog.setNegativeButton("取消", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            }
        });
        // 显示
        normalDialog.show();
    }
	
	private void inflateListView(){
        //创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (String path : musicService.musicList) {
        	Map<String, Object> listItem = new HashMap<String, Object>();
        	File file = new File(path);
            listItem.put("fileName", file.getName());
            //添加List项
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"fileName"}, new int[]{ R.id.file_name});
        //为ListView设置Adapter
        musiclist.setAdapter(simpleAdapter);    
    }
	
	
	
	
	protected void onActivityResult(int requestcode,int resultcode,Intent data){
		
		super.onActivityResult(requestcode, resultcode, data);
		if(requestcode==2){
			if(resultcode==1){
				namel=data.getStringExtra("name");
				sex=data.getStringExtra("sex");
				Log.i("name",namel);
				messages.setText("昵称： "+namel+"\n性别： "+sex);
				if(sex.equals("男")){
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.boy));
				}else if(sex.equals("女")){
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.girl));
				}else{
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.notin));
				}
			}
		}
		
	}

}
