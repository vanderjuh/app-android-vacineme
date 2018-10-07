package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PessoasActivity extends AppCompatActivity {

    private TextView nome;
    private RadioGroup genero;
    private Spinner spinnerParentesco;
    private DatePickerDialog dpdNascimento;
    private TextView dataNascimento;
    private Calendar calendario;
    private ListView listViewPessoas;
    private List<Pessoa> listaPessoas;
    private PessoasListViewAdapter pessoasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoas);
        setTitle(getString(R.string.pessoas));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nome = findViewById(R.id.editTextNome);
        dataNascimento = findViewById(R.id.editTextNascimento);
        genero = findViewById(R.id.radioGroupGenero);
        spinnerParentesco = findViewById(R.id.spinnerParentesco);
        listViewPessoas = findViewById(R.id.listViewPessoas);

        Intent intent = getIntent();
        listaPessoas = intent.getParcelableArrayListExtra("PESSOAS");
        pessoasAdapter = new PessoasListViewAdapter(this, listaPessoas);
        listViewPessoas.setAdapter(pessoasAdapter);

        popularSpinnerParentesco();
        datePickerEvent();
    }

    private void datePickerEvent() {
        dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int ano = calendario.get(Calendar.YEAR);
                dpdNascimento = new DatePickerDialog(PessoasActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int dayOfMonth) {
                        dataNascimento.setText(formatarData(dayOfMonth, mMonth, mYear));
                    }
                }, ano, mes, dia);
                dpdNascimento.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intentMenu;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.itemMenuCadPessoa:
                intentMenu = new Intent(this, CadastrarPessoaActivity.class);
                startActivity(intentMenu);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pessoa_menu, menu);
        return true;
    }

    private String formatarData(int dia, int mes, int ano) {
        String data = "";
        if (dia > 0 && dia < 10) {
            data += "0" + dia + "/";
        } else {
            data += dia + "/";
        }
        if (mes < 12) {
            mes++;
            if (mes < 10) {
                data += "0" + mes + "/";
            } else {
                data += mes + "/";
            }
        } else {
            data += mes + "/";
        }
        data += ano;
        try {
            Date dataFormatLang = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            return new SimpleDateFormat(getString(R.string.formato_data)).format(dataFormatLang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void popularSpinnerParentesco() {
        String[] descs = getResources().getStringArray(R.array.parentesco);
        TypedArray icons = getResources().obtainTypedArray(R.array.icones_parentesco);

        ArrayList<Parentesco> parentescos = new ArrayList();

        for (int cont = 0; cont < descs.length; cont++) {
            parentescos.add(new Parentesco(descs[cont], icons.getDrawable(cont)));
        }

        ParentescoAdapter paisAdapter = new ParentescoAdapter(this, parentescos);

        spinnerParentesco.setAdapter(paisAdapter);
    }

    private String getGeneroSelecionado(int id) {
        switch (id) {
            case R.id.radioButtonFeminino:
                return getString(R.string.feminino);
            case R.id.radioButtonMasculino:
                return getString(R.string.masculino);
        }
        return "";
    }

    private void limparCampos() {
        nome.setText("");
        dataNascimento.setText("");
        genero.clearCheck();
        spinnerParentesco.setSelection(0);
    }

    public void salvar(View view) {
        if (verificarFormulario()) {
            try {
                Pessoa p = new Pessoa(
                        nome.getText().toString(),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dataNascimento.getText().toString()),
                        getGeneroSelecionado(genero.getCheckedRadioButtonId()),
                        (Parentesco) spinnerParentesco.getSelectedItem()
                );
                listaPessoas.add(p);
                pessoasAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("PESSOA", p);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, R.string.pessoa_cadastrada_sucesso, Toast.LENGTH_SHORT).show();
                limparCampos();
            } catch (ParseException e) {
                Toast.makeText(this, R.string.verifique_a_data_de_nascimento, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean verificarFormulario() {
        if (nome.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.informe_o_nome_da_pessoa, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dataNascimento.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.informe_a_data_de_nascimento, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (genero.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this, R.string.informe_o_genero_da_pessoa, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerParentesco.getSelectedItemPosition() == -1) {
            Toast.makeText(this, R.string.informe_o_parentesco, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
