package com.nurmauludina2005392.jabarnampung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nurmauludina2005392.jabarnampung.api.ApiClient;
import com.nurmauludina2005392.jabarnampung.api.ApiInterface;
import com.nurmauludina2005392.jabarnampung.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fillUsername, fillName, fillEmail, fillPassword;
    Button btnRegister;
    TextView tvLoginAccount;
    String username, name, email, password;
    ApiInterface apiInterface;

    public static final String regularExpression = "^[a-zA-Z][a-zA-Z0-9_]{7,19}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fillUsername = findViewById(R.id.fillUsername);
        fillName = findViewById(R.id.fillName);
        fillEmail = findViewById(R.id.fillEmail);
        fillPassword = findViewById(R.id.fillPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginAccount = findViewById(R.id.tvLoginAccount);

        btnRegister.setOnClickListener(this);
        tvLoginAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                username = fillUsername.getText().toString();
                name = fillName.getText().toString();
                email = fillEmail.getText().toString();
                password = fillPassword.getText().toString();
                if(TextUtils.isEmpty(username)){
                    fillUsername.setError("Username tidak boleh kosong");
                    return;
                }else if(username.contains(" ")){
                    fillUsername.setError("Username tidak boleh memakai spasi");
                    return;
                }else if(username.length() < 8){
                    fillUsername.setError("Username kurang dari 8 karakter");
                    return;
                }else if(!username.matches(regularExpression)){
                    fillUsername.setError("Username tidak sesuai dengan ketentuan");
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    fillName.setError("Nama tidak boleh kosong");
                    return;
                }

                if (!isValidEmail(email)){
                    fillEmail.setError("Email tidak sesuai dengan ketentuan");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    fillPassword.setError("Password tidak boleh kosong");
                    return;
                }
                register(username, name, email, password);
                break;
            case R.id.tvLoginAccount:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void register(String username, String name, String email, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(username, name, email, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}