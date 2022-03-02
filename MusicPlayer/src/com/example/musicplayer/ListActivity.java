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
		messages.setText("�ǳƣ� "+namel+"\n�Ա� "+sex);
		if(sex.equals("��")){
			touxiang.setImageDrawable(getResources().getDrawable(R.drawable.boy));
		}else if(sex.equals("Ů")){
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
        //�Զ����SD����Music�ļ��µ������ļ�
        musiclist.setAdapter(adapter);*/
		
	
        
        //ΪListView���б���ĵ����¼��󶨼�����
        musiclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                
            	Intent intent1=new Intent(ListActivity.this,PlayActivity.class );
                //�û��������ļ������벥�Ž���
                startActivity(intent1);
            }    
        });
        //�û������б���ɾ������
        musiclist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				//����AlertDialog.Builder���󣬵������б����ʱ�򵯳�ȷ��ɾ���Ի���
				showNormalDialog(arg2);//ɾ���Ի��򵯳�
				return false;
				
				
			}
		});
        

		
		
		
	}
	
	@SuppressLint("ShowToast") 
	private void showNormalDialog(final int arg2){
        /* @setIcon ���öԻ���ͼ��
         * @setTitle ���öԻ������
         * @setMessage ���öԻ�����Ϣ��ʾ
         * setXXX��������Dialog������˿�����ʽ��������
         */
        final AlertDialog.Builder normalDialog = 
        new AlertDialog.Builder(ListActivity.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("ɾ��");
        normalDialog.setMessage("��ȷ��Ҫɾ����");
        normalDialog.setPositiveButton("ȷ��", 
            new DialogInterface.OnClickListener() {
            @SuppressLint("ShowToast") @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	String music=musicService.musicList.get(arg2);
            	File file = new File(music);
                boolean ret=file.delete();
            	musicService.musicList.remove(arg2);
            	if(ret){
            		Toast.makeText(ListActivity.this, "ɾ���ɹ���", Toast.LENGTH_SHORT);
            	}else{
            		Toast.makeText(ListActivity.this, "ɾ��ʧ�ܣ�", Toast.LENGTH_SHORT);
            	}
            	
            	inflateListView();
            }
        });
        normalDialog.setNegativeButton("ȡ��", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            }
        });
        // ��ʾ
        normalDialog.show();
    }
	
	private void inflateListView(){
        //����һ��List���ϣ�List���ϵ�Ԫ����Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (String path : musicService.musicList) {
        	Map<String, Object> listItem = new HashMap<String, Object>();
        	File file = new File(path);
            listItem.put("fileName", file.getName());
            //���List��
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"fileName"}, new int[]{ R.id.file_name});
        //ΪListView����Adapter
        musiclist.setAdapter(simpleAdapter);    
    }
	
	
	
	
	protected void onActivityResult(int requestcode,int resultcode,Intent data){
		
		super.onActivityResult(requestcode, resultcode, data);
		if(requestcode==2){
			if(resultcode==1){
				namel=data.getStringExtra("name");
				sex=data.getStringExtra("sex");
				Log.i("name",namel);
				messages.setText("�ǳƣ� "+namel+"\n�Ա� "+sex);
				if(sex.equals("��")){
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.boy));
				}else if(sex.equals("Ů")){
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.girl));
				}else{
					touxiang.setImageDrawable(getResources().getDrawable(R.drawable.notin));
				}
			}
		}
		
	}

}
