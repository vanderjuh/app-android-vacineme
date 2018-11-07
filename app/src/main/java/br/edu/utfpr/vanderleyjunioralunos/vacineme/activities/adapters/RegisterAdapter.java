package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Register;

public class RegisterAdapter extends ArrayAdapter<Register> {

    private Context context;
    private List<Register> registers;

    public RegisterAdapter(Context c, List<Register> registers){
        super(c, R.layout.item_main_listview, R.id.textViewVaccineName, registers);
        this.context = c;
        this.registers = registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.item_main_listview, parent, false);
        ImageView image = row.findViewById(R.id.imageViewVaccine);
        TextView title = row.findViewById(R.id.textViewVaccineName);
        TextView dateOfApplication = row.findViewById(R.id.textViewDateOfApplication);
        TextView dateNextVaccine = row.findViewById(R.id.textViewNextDateVaccine);

        image.setImageResource(R.drawable.ic_vacina);
        title.setText(this.registers.get(position).getVaccine().getDescription().toUpperCase());
        dateOfApplication.setText(dateOfApplication.getText().toString()+" "+new SimpleDateFormat(context.getString(R.string.formato_data)).format(this.registers.get(position).getVaccineDate()).toString());
        dateNextVaccine.setText(dateNextVaccine.getText().toString()+" "+new SimpleDateFormat(context.getString(R.string.formato_data)).format(this.registers.get(position).getNextDateVaccine()).toString());
        return row;
    }
}