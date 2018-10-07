package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class PessoasListViewAdapter extends BaseAdapter {

    Context context;
    List<Pessoa> pessoas;

    private static class PessoaHolder {
        public ImageView imageView;
        public TextView textViewNome;
        public TextView textViewNascimento;
        public TextView textViewGenero;
        public TextView textViewParentesco;
    }

    public PessoasListViewAdapter(Context context, List<Pessoa> pessoas) {

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
            view = inflater.inflate(R.layout.linha_pessoas_listview, viewGroup, false);

            holder = new PessoaHolder();

            holder.imageView = view.findViewById(R.id.imageViewIconParentesco);
            holder.textViewNome = view.findViewById(R.id.textViewNomePessoa);
            holder.textViewNascimento = view.findViewById(R.id.textViewDataNascimento);
            holder.textViewGenero = view.findViewById(R.id.textViewGenero);
            holder.textViewParentesco = view.findViewById(R.id.textViewParentesco);

            view.setTag(holder);

        }else{

            holder = (PessoaHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(pessoas.get(i).getParentesco().getIcon());
        holder.textViewNome.setText(pessoas.get(i).toString());
        holder.textViewNascimento.setText(context.getResources().getText(R.string.data_de_nascimento)+" "+new SimpleDateFormat(context.getString(R.string.formato_data)).format(pessoas.get(i).getNascimento()).toString());
        holder.textViewGenero.setText(context.getResources().getText(R.string.genero)+" "+pessoas.get(i).getGenero().toString());
        holder.textViewParentesco.setText(context.getResources().getText(R.string.parentesco2)+" "+pessoas.get(i).getParentesco().getDescricao().toString());

        return view;
    }
}