package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.VaccinesListViewAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class VaccinesActivity extends AppCompatActivity {

    private final List<Vaccine> Vaccines = new ArrayList<>();
    private ListView listViewVacinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines);
        setTitle(getString(R.string.vaccines));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewVacinas = findViewById(R.id.listViewVacinas);
        popularListaVacinas();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vaccines_menu, menu);
        return true;
    }*/

    private void popularListaVacinas(){
        Vaccines.add(new Vaccine(
                "Vaccine teste",
                " 234",
                "Testefab"
        ));
        Vaccines.add(new Vaccine(
                "Vaccine teste",
                " 234",
                "Testefab"
        ));
        Vaccines.add(new Vaccine(
                "Vaccine teste",
                " 234",
                "Testefab"
        ));
        VaccinesListViewAdapter vacinasListViewAdapter = new VaccinesListViewAdapter(this, Vaccines);
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
