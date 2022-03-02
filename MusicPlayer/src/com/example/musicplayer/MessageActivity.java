package com.example.musicplayer;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MessageActivity extends Activity {
	
	TextView zhid;
	EditText pwd;
	EditText name;
	EditText sex;
	Button set;
	Intent intent;
	Bundle bundle;
	
	String _id;
	String pwdt;
	String namet;
	String sext;
	
	private DBhelper dBhelper;//��ʼ��DBhelper ����
    private SQLiteDatabase db;// ��ʼ��SQLiteDatabade ����
    private final String TABLENAME = "db_User";// �û���Ϣ��
    private ContentValues values;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		zhid=(TextView) findViewById(R.id.zhid);
		pwd=(EditText) findViewById(R.id.pwd);
		name=(EditText) findViewById(R.id.name);
		sex=(EditText) findViewById(R.id.sex);
		set=(Button) findViewById(R.id.set);
		
		intent=getIntent();
		bundle=intent.getExtras();
		_id=bundle.getString("_id");
		zhid.setText("��    �ţ�   "+_id);
		pwd.setText(bundle.getString("pwd"));
		name.setText(bundle.getString("name"));
		sex.setText(bundle.getString("sex"));
		
		dBhelper = new DBhelper(this);//��������ʼ��DBhelper ����
		
		set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pwdt=pwd.getText().toString().trim();
				namet=name.getText().toString().trim();
				sext=sex.getText().toString().trim();
				
				db = dBhelper.getWritableDatabase();// ��ʼ��SQLiteDatabade ����
                values=new ContentValues();
                values.put("pwd", pwdt);
                values.put("name", namet);
                values.put("sex", sext);
                long k=db.update(TABLENAME, values, "_id=?", new String[]{_id});
                db.close();
                Intent intent1=new Intent(MessageActivity.this,DenluActivity.class);
                startActivity(intent1);
                
                	
                
             
			}
			
		});
		
	}
	

}
