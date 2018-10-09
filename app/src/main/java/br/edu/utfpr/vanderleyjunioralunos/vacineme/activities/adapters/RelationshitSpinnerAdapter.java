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
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;

public class RelationshitSpinnerAdapter extends BaseAdapter {

    Context context;
    List<Relationship> relationships;

    private static class RelationshipHolder {
        public ImageView imageViewBandeira;
        public TextView textViewName;
    }

    public RelationshitSpinnerAdapter(Context context, List<Relationship> relationships) {

        this.context = context;
        this.relationships = relationships;
    }

    @Override
    public int getCount() {
        return relationships.size();
    }

    @Override
    public Object getItem(int i) {
        return relationships.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        RelationshipHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_relationships_spinner, viewGroup, false);

            holder = new RelationshipHolder();

            holder.imageViewBandeira = view.findViewById(R.id.imageViewRelationship);
            holder.textViewName = view.findViewById(R.id.textViewName);

            view.setTag(holder);

        }else{

            holder = (RelationshipHolder) view.getTag();
        }

        holder.imageViewBandeira.setImageDrawable(relationships.get(i).getIcon());
        holder.textViewName.setText(relationships.get(i).getDescription());

        return view;
    }
}