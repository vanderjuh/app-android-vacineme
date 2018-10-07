package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class VacinasListViewAdapter extends BaseAdapter {

    Context context;
    List<Vacina> vacinas;

    private static class VacinasHolder {
        public ImageView imageView;
        public TextView textViewNome;
        public TextView textViewLaboratorio;
        public TextView textViewLote;
    }

    public VacinasListViewAdapter(Context context, List<Vacina> vacinas) {

        this.context = context;
        this.vacinas = vacinas;
    }

    @Override
    public int getCount() {
        return vacinas.size();
    }

    @Override
    public Object getItem(int i) {
        return vacinas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        VacinasHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_vacinas_listview, viewGroup, false);

            holder = new VacinasHolder();

            holder.imageView = view.findViewById(R.id.imageViewVacina);
            holder.textViewNome = view.findViewById(R.id.textViewNomeVacina);
            holder.textViewLaboratorio = view.findViewById(R.id.textViewLaboratorio);
            holder.textViewLote = view.findViewById(R.id.textViewLote);

            view.setTag(holder);

        }else{

            holder = (VacinasHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vacina));
        holder.textViewNome.setText(vacinas.get(i).toString());
        holder.textViewLaboratorio.setText(context.getResources().getText(R.string.laboratorio)+" "+vacinas.get(i).getLaboratorio());
        holder.textViewLote.setText(context.getResources().getText(R.string.lote)+" "+ vacinas.get(i).getLote());

        return view;
    }
}