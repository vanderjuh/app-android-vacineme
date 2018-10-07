package br.edu.utfpr.vanderleyjunioralunos.vacineme;

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

public class MainActivity extends AppCompatActivity {

    private List<Registro> registros;
    private List<Pessoa> pessoas;
    private List<Vacina> vacinas;
    private ListView listViewRegistros;
    private Spinner spinnerPessoas;
    private PessoasAdapter pessoasAdapter;
    private static final int ACTIVITY_PESSOA_R = 1;
    private static final int ACTIVITY_VACINA_R = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.bem_vindo_ao_vacineme));

        spinnerPessoas = findViewById(R.id.spinnerPessoas);
        listViewRegistros = findViewById(R.id.listViewRegistros);

        pessoas = new ArrayList<>();
        registros = new ArrayList<>();
        vacinas = new ArrayList<>();

        popularSpinnerPessoas();
        popularListViewRegistros();
    }

    private void popularSpinnerPessoas(){
        pessoasAdapter = new PessoasAdapter(this, pessoas);
        spinnerPessoas.setAdapter(pessoasAdapter);
    }

    private void popularListViewRegistros() {
        try {
            for(int i=0;i<4;i++){
                registros.add(new Registro(
                        new Vacina("Gripe", "Lote", "Laboratorio"),
                        new Pessoa(
                                "Vanderley",
                                new Date(System.currentTimeMillis()),
                                "Masculino",
                                new Parentesco("Pai")
                        ),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        R.drawable.ic_vacina
                ));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RegistrosAdapter registrosAdapter = new RegistrosAdapter(this, registros);
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
        ArrayList<Pessoa> listaPessoas = (ArrayList<Pessoa>) pessoas;
        switch (item.getItemId()) {
            case R.id.exibirPessoas:
                intentMenu = new Intent(this, PessoasActivity.class);
                intentMenu.putParcelableArrayListExtra("PESSOAS", listaPessoas);
                startActivityForResult(intentMenu, ACTIVITY_PESSOA_R);
                return true;
            case R.id.tiposVacinas:
                intentMenu = new Intent(this, VacinasActivity.class);
                startActivityForResult(intentMenu, ACTIVITY_VACINA_R);
                return true;
            case R.id.menuItemSobre:
                intentMenu = new Intent(this, SobreActivity.class);
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
                Pessoa p = data.getParcelableExtra("PESSOA");
                p.getParentesco().setIcon(recuperarIconeParentesco(p.getParentesco().getDescricao()));
                pessoas.add(p);
                pessoasAdapter.notifyDataSetChanged();
            }
        }
    }

    private Drawable recuperarIconeParentesco(String parentesco){
        int posicao = -1;
        String[] valores = getResources().getStringArray(R.array.parentesco);
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

    public class RegistrosAdapter extends ArrayAdapter<Registro>{

        private Context context;
        private List<Registro> registros;

        RegistrosAdapter(Context c, List<Registro> registros){
            super(c, R.layout.linha_main_listview, R.id.textViewNomeVacina, registros);
            this.context = c;
            this.registros = registros;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.linha_main_listview, parent, false);
            ImageView imagem = row.findViewById(R.id.imageViewRegistro);
            TextView titulo = row.findViewById(R.id.textViewNomeVacina);
            TextView dataVacinacao = row.findViewById(R.id.textViewDataVacinacao);
            TextView dataProxDose = row.findViewById(R.id.textViewDataProxDose);

            imagem.setImageResource(this.registros.get(position).getImagem());
            titulo.setText(this.registros.get(position).getVacina().getDescricao().toUpperCase());
            dataVacinacao.setText(dataVacinacao.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registros.get(position).getDataVacina()).toString());
            dataProxDose.setText(dataProxDose.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registros.get(position).getDataProxVacina()).toString());
            return row;
        }
    }

}
