package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class MainActivity extends AppCompatActivity {

    private static List<Register> registers;
    private static List<Person> people;
    private static List<Vaccine> vaccines;
    private ListView listViewRegistros;
    private Spinner spinnerPessoas;
    private static PeopleSpinnerAdapter pessoasAdapter;
    private static final int ACTIVITY_PESSOA_R = 1;
    private static final int ACTIVITY_VACINA_R = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.bem_vindo_ao_vacineme));

        spinnerPessoas = findViewById(R.id.spinnerPessoas);
        listViewRegistros = findViewById(R.id.listViewRegistros);

        people = new ArrayList<>();
        registers = new ArrayList<>();
        vaccines = new ArrayList<>();

        popularSpinnerPessoas();
        popularListViewRegistros();
    }

    private void popularSpinnerPessoas(){
        pessoasAdapter = new PeopleSpinnerAdapter(this, people);
        spinnerPessoas.setAdapter(pessoasAdapter);
    }

    private void popularListViewRegistros() {
        try {
            for(int i=0;i<4;i++){
                registers.add(new Register(
                        new Vaccine("Gripe", "Lote", "Laboratorio"),
                        new Person(
                                "Vanderley",
                                new Date(System.currentTimeMillis()),
                                "Masculino",
                                new Relationship("Pai")
                        ),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        R.drawable.ic_vacina
                ));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RegistrosAdapter registrosAdapter = new RegistrosAdapter(this, registers);
        listViewRegistros.setAdapter(registrosAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentMenu;
        ArrayList<Person> listaPeople = (ArrayList<Person>) people;
        switch (item.getItemId()) {
            case R.id.menuItemPessoas:
                intentMenu = new Intent(this, PeopleActivity.class);
                intentMenu.putParcelableArrayListExtra("PESSOAS", listaPeople);
                startActivityForResult(intentMenu, ACTIVITY_PESSOA_R);
                return true;
            case R.id.menuItemVacinas:
                intentMenu = new Intent(this, VaccinesActivity.class);
                startActivityForResult(intentMenu, ACTIVITY_VACINA_R);
                return true;
            case R.id.menuItemSobre:
                intentMenu = new Intent(this, AboutActivity.class);
                startActivity(intentMenu);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_PESSOA_R) {
            if (resultCode == RESULT_OK) {
                Person p = data.getParcelableExtra("PESSOA");
                p.getRelationship().setIcon(recuperarIconeParentesco(p.getRelationship().getDescricao()));
                people.add(p);
                pessoasAdapter.notifyDataSetChanged();
            }
        }
    }

    private Drawable recuperarIconeParentesco(String parentesco){
        int posicao = -1;
        String[] valores = getResources().getStringArray(R.array.relationship);
        for(int i =0;i<valores.length;i++){
            if(valores[i].equalsIgnoreCase(parentesco)){
                posicao = i;
                break;
            }
        }
        if(posicao > -1){
            TypedArray icons = getResources().obtainTypedArray(R.array.icones_parentesco);
            return icons.getDrawable(posicao);
        }
        return null;
    }

    public class RegistrosAdapter extends ArrayAdapter<Register>{

        private Context context;
        private List<Register> registers;

        RegistrosAdapter(Context c, List<Register> registers){
            super(c, R.layout.item_main_listview, R.id.textViewNomeVacina, registers);
            this.context = c;
            this.registers = registers;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.item_main_listview, parent, false);
            ImageView imagem = row.findViewById(R.id.imageViewRegistro);
            TextView titulo = row.findViewById(R.id.textViewNomeVacina);
            TextView dataVacinacao = row.findViewById(R.id.textViewDataVacinacao);
            TextView dataProxDose = row.findViewById(R.id.textViewDataProxDose);

            imagem.setImageResource(this.registers.get(position).getImagem());
            titulo.setText(this.registers.get(position).getVaccine().getDescricao().toUpperCase());
            dataVacinacao.setText(dataVacinacao.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registers.get(position).getDataVacina()).toString());
            dataProxDose.setText(dataProxDose.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registers.get(position).getDataProxVacina()).toString());
            return row;
        }
    }

    public static void addNovaPessoa(Person p){
        people.add(p);
        pessoasAdapter.notifyDataSetChanged();
    }

    public static List<Register> getRegisters() {
        return registers;
    }

    public static List<Person> getPeople() {
        return people;
    }

    public static List<Vaccine> getVaccines() {
        return vaccines;
    }
}
