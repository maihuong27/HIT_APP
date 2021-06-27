package com.maihuong.apphit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maihuong.apphit.configs.Constant;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class QuenMatKhauActivity extends AppCompatActivity {
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

    EditText edtmaXacNhan, edtMKMoi, edtXacNhan;
    Button btnXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        AnhXa();

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeToken = edtmaXacNhan.getText().toString().trim();
                String newPass = edtMKMoi.getText().toString().trim();
                String confirmPass = edtXacNhan.getText().toString().trim();
                if (codeToken.isEmpty()){
                    edtmaXacNhan.setError("Field can't be empty");
                    Toast.makeText(QuenMatKhauActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                } else if (newPass.isEmpty()) {
                    edtMKMoi.setError("Field can't be empty");
                    Toast.makeText(QuenMatKhauActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                } else if (!PASSWORD_PATTERN.matcher(newPass).matches()) {
                    edtMKMoi.setError("Password too weak");
                    Toast.makeText(QuenMatKhauActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                } else if(newPass.compareTo(confirmPass) != 0) {
                    edtXacNhan.setError("Passwords must match");
                    Toast.makeText(QuenMatKhauActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    checkTocken();
                }
            }
        });
    }

    public void AnhXa() {
        edtmaXacNhan = (EditText) findViewById(R.id.edtmaXacNhan);
        edtMKMoi = (EditText) findViewById(R.id.edtMKMoi);
        edtXacNhan = (EditText) findViewById(R.id.edtXacNhan);
        btnXacNhan = (Button) findViewById((R.id.btnXacNhan));
    }

    public void checkTocken() {
        String confirmCode = edtmaXacNhan.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(QuenMatKhauActivity.this);

        JSONObject objectLogin = new JSONObject();

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, Constant.BASE_API + "/api/v1/check-token?token=" + confirmCode,
                objectLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                doiMatKhau(confirmCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(loginRequest);
    }

    public void doiMatKhau(String token) {
        String newPass = edtMKMoi.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(QuenMatKhauActivity.this);

        JSONObject objectNewPass = new JSONObject();
        try {
            objectNewPass.put("password", edtMKMoi.getText().toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        JsonObjectRequest newPassRequest = new JsonObjectRequest(Request.Method.POST, Constant.BASE_API + "/api/v1/resetPassword?token=" + token,
                objectNewPass, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), DangKyActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Đổi mật khẩu thất bại thành công", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(newPassRequest);
    }

}