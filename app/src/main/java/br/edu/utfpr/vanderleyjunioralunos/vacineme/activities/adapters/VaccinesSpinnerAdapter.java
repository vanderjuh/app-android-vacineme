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

public class VaccinesSpinnerAdapter extends BaseAdapter {

    Context context;
    List<Vaccine> vaccines;

    private static class VaccineHolder {
        public ImageView imageView;
        public TextView textViewVaccineDescription;
        public TextView textViewVaccineLaboratory;
        public TextView textViewVaccineLotNumber;
    }

    public VaccinesSpinnerAdapter(Context context, List<Vaccine> vaccines) {

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

        VaccineHolder holder;

        if (view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_vaccines_spinner, viewGroup, false);

            holder = new VaccineHolder();

            holder.imageView = view.findViewById(R.id.imageViewVaccine);
            holder.textViewVaccineDescription = view.findViewById(R.id.textViewVaccineName);
            holder.textViewVaccineLaboratory = view.findViewById(R.id.textViewLaboratory);
            holder.textViewVaccineLotNumber = view.findViewById(R.id.textViewLotNumber);

            view.setTag(holder);

        }else{

            holder = (VaccineHolder) view.getTag();
        }

        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vacina));
        holder.textViewVaccineDescription.setText(vaccines.get(i).getDescription().toUpperCase());
        holder.textViewVaccineLaboratory.setText(context.getResources().getText(R.string.laboratorio)+" "+vaccines.get(i).getLaboratorio());
        holder.textViewVaccineLotNumber.setText(context.getResources().getText(R.string.lotNumber)+" "+vaccines.get(i).getLotNumber());

        return view;
    }
}