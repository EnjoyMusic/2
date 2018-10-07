package com.ltm.litaimin;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import db_sqlite.MyOpenHelper;

public class MainActivity extends AppCompatActivity {

    private EditText viewById;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private  String selectresult;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOpenHelper = new MyOpenHelper(getApplicationContext());
//        打开或创建数据库
        db = myOpenHelper.getWritableDatabase();
        //       打开或创建数据库，如果磁盘满了返回一个只读的
//        SQLiteDatabase readableDatabase = myOpenHelper.getReadableDatabase();
        viewById = (EditText) findViewById(R.id.tv_select);
    }
    //add
    public void click1(View view) {
        //直接使用SQL语句控制数据库
//      db.execSQL("insert into info(name,phone) values(?,?)",new Object[]{"ltm","1521515231"});
      //google提供的方法
        values = new ContentValues();
        values.put("name","张三丰");
        values.put("phone","123456789");
        long info = db.insert("info", null, values);
        if (info>0) {
            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
            this.click4(this.viewById);
        }else{
            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
        }
    }
    //delete
    public void click2(View view) {
//        db.execSQL("delete from info where name=?",new Object[]{"ltm"});

        int info = db.delete("info", "name=?", new String[]{"ltm"});
        if (info>0) {
            Toast.makeText(getApplicationContext(),"删除了"+info+"条记录",Toast.LENGTH_SHORT).show();
            this.click4(this.viewById);
        }else{
            Toast.makeText(getApplicationContext(),"没有相关记录",Toast.LENGTH_SHORT).show();
        }
    }
    //update
    public void click3(View view) {
//   vie db.execSQL("update info set phone=?  where name=?",new  Object[]{"1388888888","ltm"});
//   this.click4(this.viewById);
        values = new ContentValues();
        values.put("phone","1388888888");
        int info = db.update("info", values, "name=?", new String[]{"ltm"});
        if (info>0) {
            Toast.makeText(getApplicationContext(),"更改了"+info+"条记录",Toast.LENGTH_SHORT).show();
            this.click4(this.viewById);
        }else{
            Toast.makeText(getApplicationContext(),"没有相关记录",Toast.LENGTH_SHORT).show();
        }
    }
    //select
    public void click4(View view) {
        Cursor cursor = db.query("info", null, null, null, null, null, null, null);
//        Cursor cursor = db.rawQuery("select * from info", null);
        if(cursor!=null&&cursor.getCount()>0){
            selectresult="";
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String phone=cursor.getString(2);
                selectresult+=id+":"+name+":"+phone+"       ";
            }
            viewById.setText(selectresult);
        }

    }

}
