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
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Vaccine;

public class VaccinesListViewAdapter extends BaseAdapter {

    Context context;
    List<Vaccine> vaccines;

    private static class VaccinesHolder {
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewLaboratory;
        public TextView textViewLotNumber;
    }

    public VaccinesListViewAdapter(Context context, List<Vaccine> vaccines) {

        this.context = context;
        this.vaccines = vaccines;
    }

    @Override
    public int getCount() {
        return vaccines.size();
    }

    @Override
    public Object getItem(int i) {
        return vaccines.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        VaccinesHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_vaccines_listview, viewGroup, false);

            holder = new VaccinesHolder();

            holder.imageView = view.findViewById(R.id.imageViewVaccine);
            holder.textViewName = view.findViewById(R.id.textViewVaccineName);
            holder.textViewLaboratory = view.findViewById(R.id.textViewLaboratory);
            holder.textViewLotNumber = view.findViewById(R.id.textViewLotNumber);

            view.setTag(holder);

        }else{

            holder = (VaccinesHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vacina));
        holder.textViewName.setText(vaccines.get(i).toString().toUpperCase());
        holder.textViewLaboratory.setText(context.getResources().getText(R.string.laboratorio)+" "+ vaccines.get(i).getLaboratorio());
        holder.textViewLotNumber.setText(context.getResources().getText(R.string.lotNumber)+" "+ vaccines.get(i).getLotNumber());

        return view;
    }
}