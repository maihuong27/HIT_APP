package com.maihuong.apphit;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class GuiEmailMatKhauActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnGuiMa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guimaxacnhanmk);
        ActionBar actionBar = getSupportActionBar();
        AnhXa();

        btnGuiMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiEmail();
            }
        });
    }

    public void AnhXa() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnGuiMa = (Button) findViewById((R.id.btnGuiMa));
    }

    public void guiEmail() {
        String email = edtEmail.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(GuiEmailMatKhauActivity.this);

        JSONObject objectLogin = new JSONObject();
        try {
            objectLogin.put("email", email);
        } catch (Exception e) {
            System.out.println(e);
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, Constant.BASE_API + "/api/v1/forgetPassword/" + email,
                objectLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(), "Gửi mã thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), QuenMatKhauActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Gửi mã không thành công", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(loginRequest);
    }

}
