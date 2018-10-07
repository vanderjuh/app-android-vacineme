package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PessoasAdapter extends BaseAdapter {

    Context context;
    List<Pessoa> pessoas;

    private static class PessoaHolder {
        public ImageView imageView;
        public TextView  textViewNome;
    }

    public PessoasAdapter(Context context, List<Pessoa> pessoas) {

        this.context = context;
        this.pessoas = pessoas;
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Object getItem(int i) {
        return pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        PessoaHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_pessoa, viewGroup, false);

            holder = new PessoaHolder();

            holder.imageView = view.findViewById(R.id.imageViewParentesco);
            holder.textViewNome = view.findViewById(R.id.textViewNome);

            view.setTag(holder);

        }else{

            holder = (PessoaHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(pessoas.get(i).getParentesco().getIcon());
        holder.textViewNome.setText(pessoas.get(i).toString());

        return view;
    }
}