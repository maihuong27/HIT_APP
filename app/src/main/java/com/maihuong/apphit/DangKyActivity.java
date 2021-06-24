package com.maihuong.apphit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maihuong.apphit.configs.Constant;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +     //có ít nhất một số
                    "(?=.*[a-z])" +     //có ít nhất một ký tự viết thường
                    "(?=.*[A-Z])" +     //có ít nhất một ký tự viết hoa
                    //"?=.*[a-zA-Z]" +
                    "(?=.*[@#$%^&+=])" +    //có ít nhất một ký tự đặc biệt
                    "(?=\\S+$)" +   //không có khoảng trắng
                    ".{6,}" +   // có ít nhất 6 ký tự
                    "$");
    EditText edtHoTen, edtMatKhau, edtTenDK, edtEmail, edtSoDT, edtNgaySinh;
    Button btnDangKy, btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        AnhXa();
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnHuy = (Button)   findViewById(R.id.btnHuy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTenInput = edtHoTen.getText().toString().trim();
                String soDTInput = edtSoDT.getText().toString().trim();
                String passwordInput = edtMatKhau.getText().toString().trim();
                String emailInput = edtEmail.getText().toString().trim();
                String nameInput = edtTenDK.getText().toString().trim();
                String birthdayInput = edtNgaySinh.getText().toString().trim();

                if (nameInput.isEmpty()){
                    edtTenDK.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (nameInput.length() > 15)  {
                    edtTenDK.setError("Username too long");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (passwordInput.isEmpty()) {
                    edtMatKhau.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                    edtMatKhau.setError("Password too weak");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if(hoTenInput.isEmpty()) {
                    edtHoTen.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }else if(birthdayInput.isEmpty()) {
                    edtNgaySinh.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (emailInput.isEmpty()){
                    edtEmail.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())  {
                    edtEmail.setError("Please enter a valid email address");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else if (soDTInput.isEmpty()) {
                    edtSoDT.setError("Field can't be empty");
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    kiemTraDK();
                    edtTenDK.setText(" ");
                    edtMatKhau.setText(" ");
                    edtHoTen.setText(" ");
                    edtNgaySinh.setText(" ");
                    edtEmail.setText(" ");
                    edtSoDT.setText(" ");
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenDK.setText(" ");
                edtMatKhau.setText(" ");
                edtHoTen.setText(" ");
                edtNgaySinh.setText(" ");
                edtEmail.setText(" ");
                edtSoDT.setText(" ");
            }
        });

    }



    public void AnhXa() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtTenDK = (EditText) findViewById(R.id.edtTenDK);
        edtSoDT = (EditText) findViewById(R.id.edtSoDT);
        edtNgaySinh = (EditText) findViewById(R.id.edtNgaySinh);
    }

    public void kiemTraDK() {
        RequestQueue requestQueue = Volley.newRequestQueue(DangKyActivity.this);

        JSONObject objectSignup = new JSONObject();
        try {
            objectSignup.put("username", edtTenDK.getText().toString());
            objectSignup.put("password", edtMatKhau.getText().toString());
            objectSignup.put("fullName", edtHoTen.getText().toString());
            objectSignup.put("birthday", edtNgaySinh.getText().toString());
            objectSignup.put("email", edtEmail.getText().toString());
            objectSignup.put("phone", edtSoDT.getText().toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, Constant.BASE_API + "/api/v1/auth/signup",
                objectSignup, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(loginRequest);
    }

}