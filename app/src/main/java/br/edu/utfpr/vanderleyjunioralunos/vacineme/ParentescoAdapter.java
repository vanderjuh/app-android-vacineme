package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ParentescoAdapter extends BaseAdapter {

    Context context;
    List<Parentesco> parentescos;

    private static class ParentescoHolder {
        public ImageView imageViewBandeira;
        public TextView  textViewNome;
    }

    public ParentescoAdapter(Context context, List<Parentesco> parentescos) {

        this.context = context;
        this.parentescos  = parentescos;
    }

    @Override
    public int getCount() {
        return parentescos.size();
    }

    @Override
    public Object getItem(int i) {
        return parentescos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ParentescoHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_parentesco, viewGroup, false);

            holder = new ParentescoHolder();

            holder.imageViewBandeira = view.findViewById(R.id.imageViewBandeira);
            holder.textViewNome      = view.findViewById(R.id.textViewNome);

            view.setTag(holder);

        }else{

            holder = (ParentescoHolder) view.getTag();
        }

        holder.imageViewBandeira.setImageDrawable(parentescos.get(i).getIcon());
        holder.textViewNome.setText(parentescos.get(i).getDescricao());

        return view;
    }
}