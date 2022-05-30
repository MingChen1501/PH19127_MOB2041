package com.example.ph19127_mob2041;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ph19127_mob2041.dao.ThuThuDAO;
import com.example.ph19127_mob2041.model.ThuThu;

public class LoginActivity extends AppCompatActivity {
    private static final String EMPTY = "empty id";
    private static final String INVALID = "invalid password";
    private ThuThuDAO mThuThuDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mThuThuDao = new ThuThuDAO(this);
        EditText etId, etPassword;
        CheckBox chkRemember;
        Button btnLogin, btnCancel;

        etId = findViewById(R.id.et_loginActivity_id);
        etPassword = findViewById(R.id.et_loginActivity_password);
        chkRemember = findViewById(R.id.chk_loginActivity_rememberPassword);
        btnLogin = findViewById(R.id.btn_loginActivity_login);
        btnCancel = findViewById(R.id.btn_loginActivity_cancel);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                try {
                    String id = etId.getText().toString();
                    String pasword = etPassword.getText().toString();
                    if (id.isEmpty()) throw new NullPointerException(EMPTY);
                    ThuThu accountRequest = mThuThuDao.getById(id);
                    Toast.makeText(LoginActivity.this, accountRequest.toString(), Toast.LENGTH_SHORT).show();
                    boolean isremember = chkRemember.isChecked();
                    if (id.equals("admin") && pasword.equals("admin")) {
                        intent.putExtra("name", "admin");
                        intent.putExtra("isAdmin", true);
                        startActivity(intent);
                        Log.d("start", "admin");
                        if (!isremember) {
                            etId.setText("");
                            etPassword.setText("");
                            //TODO tách method
                        }
                    } else if (id.equals(accountRequest.getMaThuThu())
                            && pasword.equals(accountRequest.getPassword())){
                        Log.d("start", "member");
                        intent.putExtra("id", accountRequest.getMaThuThu());
                        intent.putExtra("name", accountRequest.getHoTen());
                        intent.putExtra("isAdmin", false);
                        startActivity(intent);
                        if (!isremember) {
                            etId.setText("");
                            etPassword.setText("");
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "không đúng", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    if (e.getMessage().equals(EMPTY)) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Chưa tồn tại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}