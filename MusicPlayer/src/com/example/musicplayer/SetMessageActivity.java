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

public class SetMessageActivity extends Activity {

	TextView zhid;
	EditText pwd;
	EditText name;
	EditText sex;
	Button set;
	Button back;
	Intent intent;
	Intent intent1;
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
		setContentView(R.layout.setmessage);
		zhid=(TextView) findViewById(R.id.setzhid);
		pwd=(EditText) findViewById(R.id.setpwd);
		name=(EditText) findViewById(R.id.setname);
		sex=(EditText) findViewById(R.id.setsex);
		set=(Button) findViewById(R.id.setset);
		back=(Button) findViewById(R.id.setback);
		
		intent=getIntent();
		bundle=intent.getExtras();
		_id=bundle.getString("_id");
		zhid.setText("��    �ţ�   "+_id);
		pwdt=bundle.getString("pwd");
		pwd.setText(pwdt);
		namet=bundle.getString("name");
		name.setText(namet);
		sext=bundle.getString("sex");
		sex.setText(sext);
		
		
		
		
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
			}
			
		});
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                intent1=new Intent();
                bundle = new Bundle();
                bundle.putString("_id",_id);
                bundle.putString("pwd",pwdt);
                bundle.putString("name",namet);
                bundle.putString("sex",sext);
                intent1.putExtras(bundle);
                setResult(1,intent1);
                finish();
			}
			
		});
		
	}

}
