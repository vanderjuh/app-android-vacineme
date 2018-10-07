package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CadastrarPessoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pessoa);
        setTitle(getString(R.string.inserir_uma_nova_pessoa));
    }
}
