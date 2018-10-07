package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.RelationshitSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;

public class addNewPersonActivity extends AppCompatActivity {

    private TextView nome;
    private RadioGroup genero;
    private Spinner spinnerParentesco;
    private DatePickerDialog dpdNascimento;
    private TextView dataNascimento;
    private Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);
        setTitle(getString(R.string.inserir_uma_nova_pessoa));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nome = findViewById(R.id.editTextNome);
        dataNascimento = findViewById(R.id.editTextNascimento);
        genero = findViewById(R.id.radioGroupGenero);
        spinnerParentesco = findViewById(R.id.spinnerParentesco);

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
                dpdNascimento = new DatePickerDialog(addNewPersonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int dayOfMonth) {
                        dataNascimento.setText(formatarData(dayOfMonth, mMonth, mYear));
                    }
                }, ano, mes, dia);
                dpdNascimento.show();
            }
        });
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
        String[] descs = getResources().getStringArray(R.array.relationship);
        TypedArray icons = getResources().obtainTypedArray(R.array.icones_parentesco);

        ArrayList<Relationship> relationships = new ArrayList();

        for (int cont = 0; cont < descs.length; cont++) {
            relationships.add(new Relationship(descs[cont], icons.getDrawable(cont)));
        }

        RelationshitSpinnerAdapter paisAdapter = new RelationshitSpinnerAdapter(this, relationships);

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
                Person p = new Person(
                        nome.getText().toString(),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dataNascimento.getText().toString()),
                        getGeneroSelecionado(genero.getCheckedRadioButtonId()),
                        (Relationship) spinnerParentesco.getSelectedItem()
                );
                MainActivity.addNovaPessoa(p);
                Toast.makeText(this, R.string.pessoa_cadastrada_sucesso, Toast.LENGTH_SHORT).show();
                PeopleActivity.updateListView();
                this.finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
