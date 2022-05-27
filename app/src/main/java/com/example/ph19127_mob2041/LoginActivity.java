package com.example.ph19127_mob2041;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ph19127_mob2041.dao.ThuThuDAO;
import com.example.ph19127_mob2041.database.DBHelper;
import com.example.ph19127_mob2041.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private List<ThuThu> mThuThuList;
    private ThuThuDAO mthuThuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mThuThuList = new ArrayList<>();
        mthuThuDAO = new ThuThuDAO(this);
        mThuThuList.addAll(mthuThuDAO.getAll());
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
                boolean isCorrect = false;
                String id = etId.getText().toString();
                String pasword = etPassword.getText().toString();
                ThuThu thuThu = new ThuThu(id, pasword, "", "");
                boolean remember = chkRemember.isChecked();
                if (id.equals("admin") && pasword.equals("admin")) {
                    intent.putExtra("name", "admin");
                    intent.putExtra("isAdmin", true);
                    isCorrect = true;
                    startActivity(intent);
                    if (!remember) {
                        etId.setText("");
                        etPassword.setText("");
                    }
                } else {
                    for (ThuThu element : mThuThuList) {
                        if (element.equals(thuThu)) {
                            intent.putExtra("id", element.getMaThuThu());
                            intent.putExtra("password", element.getPassword());
                            intent.putExtra("name", element.getHoTen());
                            intent.putExtra("phoneNumber", element.getSoDienThoai());
                            intent.putExtra("isAdmin", false);
                            isCorrect = true;
                            startActivity(intent);
                            if (!remember) {
                                etId.setText("");
                                etPassword.setText("");
                            }
                            break;
                        }
                    }
                }
                if (!isCorrect)
                    Toast.makeText(LoginActivity.this
                            , "Tài khoản hoặc mật khẩu không hợp lệ"
                            , Toast.LENGTH_SHORT).show();
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