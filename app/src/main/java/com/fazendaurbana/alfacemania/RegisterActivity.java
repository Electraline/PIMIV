package com.fazendaurbana.alfacemania;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNomeCompleto, etEmail, etLogin, etSenha;
    private Button btnRegister;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNomeCompleto = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etLogin = findViewById(R.id.etLogin);
        etSenha = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        apiService = AppClient.getApiClient().create(ApiService.class);
        ApiService apiService = (ApiService) AppClient.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCompleto = etNomeCompleto.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String login = etLogin.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();

                if (!nomeCompleto.isEmpty() && !email.isEmpty() && !login.isEmpty() && !senha.isEmpty()) {
                    registerUser(new Usuario(nomeCompleto, email, login, senha));
                } else {
                    Toast.makeText(RegisterActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(Usuario usuario) {
        Call<Usuario> call = apiService.createUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                // Redirecionar para a tela de login ou próxima tela
                if (response.isSuccessful())
                    Toast.makeText(RegisterActivity.this, "Registro bem-sucedido", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(RegisterActivity.this, "Erro no registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}