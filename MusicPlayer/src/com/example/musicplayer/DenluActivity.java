package com.example.musicplayer;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DenluActivity extends Activity {
	
	EditText zhiddl;
	EditText pwddl;
	Button dl;
	
	private DBhelper dBhelper;//初始化DBhelper 对象
    private SQLiteDatabase db;// 初始化SQLiteDatabade 对象
    private final String TABLENAME = "db_User";// 用户信息表
    private User user;//声明User用户存储值
    
    private Cursor cursor;
    private ContentValues values;
    
    String mUserId;
    String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denlu);
        zhiddl=(EditText) findViewById(R.id.zhiddl);
        pwddl=(EditText) findViewById(R.id.pwddl);
        
        dl=(Button) findViewById(R.id.dl);
        dBhelper = new DBhelper(this);//创建并初始化DBhelper 对象
        
        dl.setOnClickListener(new OnClickListener(){
        	
        	

			@Override
			public void onClick(View v) {
				
				mUserId=zhiddl.getText().toString();
		        mPassword=pwddl.getText().toString();
				
				
	        	
	        	db = dBhelper.getReadableDatabase();// 初始化SQLiteDatabade 对象
	            cursor = db.rawQuery("select * from db_User where _id = ? ",new String[]{mUserId});
	        	
	            
	        	
	        	
	            if (cursor.moveToFirst()){
	            	if(cursor.getString(1).equals(mPassword)){
	            		user = new User();
		                while (!cursor.isAfterLast()){
		                    user.set_id(cursor.getString(0));
		                    user.setPassword(cursor.getString(1));
		                    user.setUser_name(cursor.getString(2));
		                    user.setSex(cursor.getString(3));
		                    cursor.moveToNext();

		                }
		                cursor.close();
		                
		                Intent intent = new Intent(DenluActivity.this,ListActivity.class);
						Bundle bundle = new Bundle();
		                bundle.putString("_id",user.get_id());
		                bundle.putString("pwd",user.getPassword());
		                bundle.putString("name",user.getUser_name());
		                bundle.putString("sex",user.getSex());
		                intent.putExtras(bundle);
		                startActivity(intent);
		                
	            	}else{
	            		Toast.makeText(DenluActivity.this, "该用户已存在，您的登录密码不正确！", Toast.LENGTH_LONG).show();
	            	}
	                
	                db.close();
	            }else{
	            	cursor.close();
	            	db = dBhelper.getWritableDatabase();// 初始化SQLiteDatabade 对象
	                values=new ContentValues();
	                values.put("_id", mUserId);
	                values.put("pwd", "123456");
	                values.put("name", "用户1");
	                values.put("sex", "男");
	                long k=db.insert(TABLENAME, null, values);
	                db.close();
	                user = new User();
	                user.set_id(mUserId);
	                user.setPassword("123456");
	                user.setUser_name("用户1");
	                user.setSex("男");
	                
	                Intent intent = new Intent(DenluActivity.this,MessageActivity.class);
					Bundle bundle = new Bundle();
	                bundle.putString("_id",user.get_id());
	                bundle.putString("pwd",user.getPassword());
	                bundle.putString("name",user.getUser_name());
	                bundle.putString("sex",user.getSex());
	                intent.putExtras(bundle);
//	                startActivityForResult(intent,1);
	                startActivity(intent);
	                
	            }
	            
	            
	            
				// TODO Auto-generated method stub
				
			}
        	
        });

    }
    /*protected void onActivityResult(int requestcode,int resultcode,Intent data){
		super.onActivityResult(requestcode, resultcode, data);
		if(requestcode==1){
			if(resultcode==1){
				pwddl.setText("");
			}
		}
		
	}
*/


}
