package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VacinasActivity extends AppCompatActivity {

    private final List<Vacina> VACINAS = new ArrayList<>();
    private ListView listViewVacinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinas);
        setTitle(getString(R.string.vacinas));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewVacinas = findViewById(R.id.listViewVacinas);
        popularListaVacinas();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vacina_menu, menu);
        return true;
    }*/

    private void popularListaVacinas(){
        VACINAS.add(new Vacina(
                "Vacina teste",
                " 234",
                "Testefab"
        ));
        VACINAS.add(new Vacina(
                "Vacina teste",
                " 234",
                "Testefab"
        ));
        VACINAS.add(new Vacina(
                "Vacina teste",
                " 234",
                "Testefab"
        ));
        VacinasListViewAdapter vacinasListViewAdapter = new VacinasListViewAdapter(this, VACINAS);
        listViewVacinas.setAdapter(vacinasListViewAdapter);
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
