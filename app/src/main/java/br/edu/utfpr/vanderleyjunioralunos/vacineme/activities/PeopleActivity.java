package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleListViewAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.VacinemeDatabase;

public class PeopleActivity extends AppCompatActivity {

    private static ListView listViewPeople;
    private static PeopleListViewAdapter listViewPeopleAdapter;
    private ActionMode actionMode;
    private View selectedView;
    private int selectedPosition = -1;
    private int EDITOR_MODE = 1;
    private int INSERT_MODE = 2;
    private String PERSON_ID = "PERSON_ID";

    private List<Person> peopleRoom;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            mode.setTitle(R.string.choose_an_option);
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Intent intentMenu;
            Person p = peopleRoom.get(selectedPosition);
            switch (item.getItemId()) {
                case R.id.menuItemDelete:
                    deletePersonAndReloadData(p);
                    Toast.makeText(PeopleActivity.this, R.string.successfully_deleted, Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.menuItemChange:
                    intentMenu = new Intent(PeopleActivity.this, AddNewPersonActivity.class);
                    intentMenu.putExtra(PERSON_ID, p.getId());
                    startActivityForResult(intentMenu, EDITOR_MODE);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(selectedView != null){
                selectedView.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            selectedView = null;
            selectedPosition = -1;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        setTitle(getString(R.string.people));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setListViewPeople();
    }

    private void deletePersonAndReloadData(final Person p){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                VacinemeDatabase.getDatabase(PeopleActivity.this).personDAO().delete(p);
            }
        });
        peopleRoom.remove(selectedPosition);
        loadDataFromPeople();
    }

    private void loadDataFromPeople(){
        PeopleActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //listViewPeopleAdapter.notifyDataSetChanged();
                listViewPeopleAdapter = new PeopleListViewAdapter(PeopleActivity.this, peopleRoom);
                listViewPeople.setAdapter(listViewPeopleAdapter);
            }
        });
    }

    private void setListViewPeople(){
        listViewPeople = findViewById(R.id.listViewPeople);
        listViewPeople.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                VacinemeDatabase database = VacinemeDatabase.getDatabase(PeopleActivity.this);
                peopleRoom = database.personDAO().queryAll();
                loadDataFromPeople();
            }
        });
        listViewPeople.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode!=null){
                    return false;
                }
                selectedView = view;
                selectedPosition = position;
                view.setBackgroundColor(Color.LTGRAY);
                view.setSelected(true);
                actionMode = startActionMode(actionModeCallback);
                return true;
            }
        });
        listViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentMenu;
                intentMenu = new Intent(PeopleActivity.this, AddNewPersonActivity.class);
                Person p = peopleRoom.get(position);
                intentMenu.putExtra(PERSON_ID, p.getId());
                if(actionMode!=null){
                    actionMode.finish();
                }
                startActivityForResult(intentMenu, EDITOR_MODE);
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
            case R.id.itemMenuAddNewPerson:
                intentMenu = new Intent(this, AddNewPersonActivity.class);
                startActivityForResult(intentMenu, INSERT_MODE);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.people_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && (requestCode == INSERT_MODE || requestCode == EDITOR_MODE)){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    peopleRoom = VacinemeDatabase.getDatabase(PeopleActivity.this).personDAO().queryAll();
                    loadDataFromPeople();
                }
            });
        }
    }
}
