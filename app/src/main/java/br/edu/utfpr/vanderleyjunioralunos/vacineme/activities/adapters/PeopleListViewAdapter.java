package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;

public class PeopleListViewAdapter extends BaseAdapter {

    Context context;
    List<Person> people;

    private static class PersonHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewRelationship;
    }

    public PeopleListViewAdapter(Context context, List<Person> people) {

        this.context = context;
        this.people = people;
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int i) {
        return people.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        PersonHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_people_listview, viewGroup, false);

            holder = new PersonHolder();

            holder.imageView = view.findViewById(R.id.imageViewIconRelationship);
            holder.textViewName = view.findViewById(R.id.textViewPersonName);
            holder.textViewRelationship = view.findViewById(R.id.textViewRelationship);

            view.setTag(holder);

        }else{

            holder = (PersonHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(Relationship.findIcon(people.get(i).getRelationship(), context));
        holder.textViewName.setText(people.get(i).toString());
        holder.textViewRelationship.setText(context.getResources().getText(R.string.parentesco2)+" "+ people.get(i).getRelationship());

        return view;
    }
}