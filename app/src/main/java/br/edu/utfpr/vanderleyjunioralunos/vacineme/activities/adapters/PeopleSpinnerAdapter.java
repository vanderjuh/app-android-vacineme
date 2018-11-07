package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Relationship;

public class PeopleSpinnerAdapter extends BaseAdapter {

    Context context;
    List<Person> people;

    private static class PersonHolder {
        public ImageView imageView;
        public TextView textViewName;
    }

    public PeopleSpinnerAdapter(Context context, List<Person> people) {

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
            view = inflater.inflate(R.layout.item_people_spinner, viewGroup, false);

            holder = new PersonHolder();

            holder.imageView = view.findViewById(R.id.imageViewRelationship);
            holder.textViewName = view.findViewById(R.id.textViewName);

            view.setTag(holder);

        }else{

            holder = (PersonHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(Relationship.findIcon(people.get(i).getRelationship(), context));
        holder.textViewName.setText(people.get(i).toString());

        return view;
    }

}