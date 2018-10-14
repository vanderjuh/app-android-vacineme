package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.graphics.Color;
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

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleListViewAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;

public class PeopleActivity extends AppCompatActivity {

    private static ListView listViewPeople;
    private static PeopleListViewAdapter listViewpeopleAdapter;
    private ActionMode actionMode;
    private View selectedView;
    private int selectedPosition = -1;
    private int EDITOR_MODE = 1;

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
            switch (item.getItemId()) {
                case R.id.menuItemDelete:
                    MainActivity.removeAPerson(selectedPosition);
                    listViewpeopleAdapter.notifyDataSetChanged();
                    Toast.makeText(PeopleActivity.this, R.string.successfully_deleted, Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.menuItemChange:
                    intentMenu = new Intent(PeopleActivity.this, AddNewPersonActivity.class);
                    intentMenu.putExtra("ITEM_POSITION", selectedPosition);
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
        listViewpeopleAdapter = new PeopleListViewAdapter(this, MainActivity.getPeople());
        setListViewPeople();
    }

    private void setListViewPeople(){
        listViewPeople = findViewById(R.id.listViewPeople);
        listViewPeople.setAdapter(listViewpeopleAdapter);
        listViewPeople.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
        listViewpeopleAdapter.notifyDataSetChanged();
    }
}
