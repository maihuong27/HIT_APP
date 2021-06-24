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

public class QuenMatKhauActivity extends AppCompatActivity {

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
                Intent iDangNhap = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
            }
        });
    }

    public void AnhXa() {
        edtmaXacNhan = (EditText) findViewById(R.id.edtmaXacNhan);
        edtMKMoi = (EditText) findViewById(R.id.edtMKMoi);
        edtXacNhan = (EditText) findViewById(R.id.edtXacNhan);
        btnXacNhan = (Button) findViewById((R.id.btnXacNhan));
    }

    public void doiMatKhau() {
        String confirmCode = edtmaXacNhan.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(QuenMatKhauActivity.this);

        JSONObject objectLogin = new JSONObject();
        try {
            objectLogin.put("newPassword", edtMKMoi.getText().toString());
            objectLogin.put("confirmPassword", edtXacNhan.getText().toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, Constant.BASE_API + "/api/v1/resetPassword?token=" + confirmCode,
                objectLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), DangKyActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(loginRequest);
    }

}