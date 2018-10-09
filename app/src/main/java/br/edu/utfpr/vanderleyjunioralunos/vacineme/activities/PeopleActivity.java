package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleListViewAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;

public class PeopleActivity extends AppCompatActivity {

    private static ListView listViewPeople;
    private static PeopleListViewAdapter peopleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        setTitle(getString(R.string.people));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listViewPeople = findViewById(R.id.listViewPeople);
        peopleAdapter = new PeopleListViewAdapter(this, MainActivity.getPeople());
        listViewPeople.setAdapter(peopleAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intentMenu;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.itemMenuAddNewPerson:
                intentMenu = new Intent(this, AddNewPersonActivity.class);
                startActivity(intentMenu);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.people_menu, menu);
        return true;
    }

    public static void updateListView(){
        peopleAdapter.notifyDataSetChanged();
    }

}
