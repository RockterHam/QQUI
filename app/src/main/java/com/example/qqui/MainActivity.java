package com.example.qqui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    EditText et_user, et_pwd;
    Button bt_submit, bt_reset;
    TextView tv_reg;
    MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS,
                Manifest.permission.RECEIVE_BOOT_COMPLETED}, 10);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);

        getWidID();

        bt_submit.setOnClickListener(v->{
            String user = et_user.getText().toString();
            if (user.isEmpty()){
                Toast.makeText(MainActivity.this, "请输入用户名!", Toast.LENGTH_SHORT).show();
                return;
            }

            String pwd = et_pwd.getText().toString();
            if (pwd.isEmpty()){
                Toast.makeText(MainActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("account", null, "user=? and pwd=?",
                    new String[]{user, pwd},null ,null,null);
            if (cursor.getCount() == 0){
                Toast.makeText(MainActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                return;
            }
            cursor.moveToFirst();

            String tStr = "您的性别是：" + cursor.getString(cursor.getColumnIndex("gender")) +
                    "\n您的爱好是：" + cursor.getString(cursor.getColumnIndex("hobby"));
            Toast.makeText(MainActivity.this, tStr, Toast.LENGTH_LONG).show();
        });

        bt_reset.setOnClickListener(v->{
            et_user.setText("");
            et_pwd.setText("");
        });

        tv_reg.setOnClickListener(v->{
            Intent intent = new Intent(this, Register.class);
            MainActivity.this.startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!(data == null) || !("".equals(data))){
            String user = data.getStringExtra("user");
            String pwd = data.getStringExtra("pwd");
            et_user.setText(user);
            et_pwd.setText(pwd);
        }
    }

    public void getWidID(){
        et_user = findViewById(R.id.et_user);
        et_pwd = findViewById(R.id.et_pwd);
        bt_submit = findViewById(R.id.bt_submit);
        bt_reset = findViewById(R.id.bt_reset);
        tv_reg = findViewById(R.id.tv_reg);
    }
}
