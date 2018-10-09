package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.VaccinesListViewAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class VaccinesActivity extends AppCompatActivity {

    private static VaccinesListViewAdapter vaccinesListViewAdapter;
    private ListView listViewVaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines);
        setTitle(getString(R.string.vaccines));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewVaccines = findViewById(R.id.listViewVaccines);

        vaccinesListViewAdapter = new VaccinesListViewAdapter(this, MainActivity.getVaccines());
        listViewVaccines.setAdapter(vaccinesListViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intentMenu;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menuItemAddNewVaccine:
                intentMenu = new Intent(this, AddNewVaccineActivity.class);
                startActivity(intentMenu);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vaccines_menu, menu);
        return true;
    }

    public static void updateListView() {
        vaccinesListViewAdapter.notifyDataSetChanged();
    }
}
