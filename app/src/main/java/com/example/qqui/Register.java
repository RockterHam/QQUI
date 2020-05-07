package com.example.qqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText et_rguser, et_rgpwd, et_rgpwdcfm;
    RadioGroup rg;
    CheckBox cb_rgread, cb_rggame, cb_rgtravel, cb_rgsport;
    Button bt_rgsubmit;
    MySQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new MySQLiteOpenHelper(this);
        getWidID();



        bt_rgsubmit.setOnClickListener((v) -> {
            String user = getUser();
            if (user.isEmpty()) return;

            String pwd = getPwd();
            if (pwd.isEmpty()) return;

            String gender = getGender();
            String hobby = getHobby();

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues value = new ContentValues();
            value.put("user", user);
            value.put("pwd", pwd);
            value.put("hobby", hobby);
            value.put("gender", gender);
            Log.v("Rockter", user + pwd + hobby + gender);
            long res = db.insert("account", null, value);
            db.close();

            if (res == -1){
                Toast.makeText(Register.this, "注册失败！", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();

            finish();
        });
    }

    public void getWidID(){
        et_rguser = findViewById(R.id.et_rguser);
        et_rgpwd = findViewById(R.id.et_rgpwd);
        et_rgpwdcfm = findViewById(R.id.et_rgpwdcfm);
        rg = findViewById(R.id.rg);
        cb_rgread = findViewById(R.id.cb_rgread);
        cb_rggame = findViewById(R.id.cb_rggame);
        cb_rgtravel = findViewById(R.id.cb_rgtravel);
        cb_rgsport = findViewById(R.id.cb_rgsport);
        bt_rgsubmit = findViewById(R.id.bt_rgsubmit);
    }

    private String getUser(){
        String user = et_rguser.getText().toString();
        if (user.isEmpty()){
            Toast.makeText(Register.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    private String getPwd(){
        String pwd = et_rgpwd.getText().toString();
        String pwdcfm = et_rgpwdcfm.getText().toString();

        if (pwd.isEmpty() || pwdcfm.isEmpty() || !pwd.equals(pwdcfm)){
            Toast.makeText(Register.this, "密码为空或两次密码不一致!", Toast.LENGTH_SHORT).show();
        }
        return pwd;
    }

    private String getGender(){
        int n = rg.getChildCount();
        String gender = "";
        for (int i = 0; i < n ; i++ ){
            RadioButton rb = (RadioButton) rg.getChildAt(i);
            if (rb.isChecked()){
                gender = rb.getText().toString();
                break;
            }
        }
        return gender;
    }

    private String getHobby(){
        String hobby = "";
        if (cb_rggame.isChecked()){
            hobby = hobby + cb_rggame.getText().toString() + "   ";
        }
        if (cb_rgread.isChecked()){
            hobby = hobby + cb_rgread.getText().toString() + "   ";
        }
        if (cb_rgsport.isChecked()){
            hobby = hobby + cb_rgsport.getText().toString() + "   ";
        }
        if (cb_rgtravel.isChecked()){
            hobby = hobby + cb_rgtravel.getText().toString() + "   ";
        }
        return hobby;
    }
}
