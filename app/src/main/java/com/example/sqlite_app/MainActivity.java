package com.example.sqlite_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText mbNumber;
    Button write;
    Button read;
    Button update;
    Button remove;
    TextView show;
    SQLiteDatabase db;
    MyHelper helper;
    ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new MyHelper(this);

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        mbNumber=(EditText)findViewById(R.id.mb_number);
        write=(Button)findViewById(R.id.write_btn);
        read=(Button)findViewById(R.id.read_btn);
        update=(Button)findViewById(R.id.update_btn);
        remove=(Button)findViewById(R.id.remove_btn);
        show=(TextView)findViewById(R.id.show);
    }

    public Boolean checkStr(String str1,String str2,String str3){
        if((str1.length()!=0)&&(str2.length()!=0)&&(str3.length()!=0))
            return true;
        else
            return false;
    }
    public void ONwrite(View view){
        db=helper.getWritableDatabase();
        values=new ContentValues();
        String sname=name.getText().toString();
        String semail=email.getText().toString();
        String snumber=mbNumber.getText().toString();
        if(checkStr(sname,semail,snumber)){
            Cursor cursor=db.rawQuery("select * from sqApp_data where mbNumber=?",new String[]{snumber});
            if(cursor.getCount()!=0){
                Toast.makeText(this, "you can't insert the same mobile number", Toast.LENGTH_SHORT).show();
            }else{
                values.put("name",sname);
                values.put("email",semail);
                values.put("mbNumber",snumber);
                db.insert("sqApp_data",null,values);
                Toast.makeText(this, "WRITE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }else{
            Toast.makeText(this, "please enter the information", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void ONread(View view){
        db=helper.getWritableDatabase();
        Cursor cursor=db.query("sqApp_data",null,null,null,null,null,null,null);
        if(cursor.getCount()==0){
            show.setText("");
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.moveToFirst();
            show.setText("name:"+cursor.getString(0)+"\n"+
                    "email:"+cursor.getString(1)+"\n"+
                    "mobile number:"+cursor.getString(2)+"\n");
        }while(cursor.moveToNext()){
            show.append("name:"+cursor.getString(0)+"\n"+
                    "email:"+cursor.getString(1)+"\n"+
                    "mobile number:"+cursor.getString(2)+"\n");
        }
        cursor.close();
        db.close();
    }
    public void ONupdate(View view){
        db=helper.getWritableDatabase();
        values=new ContentValues();
        String sname=name.getText().toString();
        String semail=email.getText().toString();
        String snumber=mbNumber.getText().toString();
        if(checkStr(sname,semail,snumber)){
            values.put("name",sname);
            values.put("email",semail);
            db.update("sqApp_data",values,"mbNumber=?",new String []{snumber});
            Toast.makeText(this, "UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            db.close();
        }else{
            Toast.makeText(this, "please enter the information", Toast.LENGTH_SHORT).show();
        }

    }
    public void ONremove(View view){
        db=helper.getWritableDatabase();
        db.delete("sqApp_data",null,null);
        Toast.makeText(this, "REMOVE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        db.close();
    }
}
