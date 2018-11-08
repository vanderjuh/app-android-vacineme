package br.edu.utfpr.vanderleyjunioralunos.vacineme.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;

public abstract class AlertsUtil {

    public static void confirmation(Context context, String msg, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmation);
        builder.setIcon(R.drawable.ic_round_report_problem_24px);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.yes, listener);
        builder.setNegativeButton(R.string.no, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void alert(Context context, String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.alert);
        builder.setIcon(R.drawable.ic_round_report_problem_24px);
        builder.setMessage(msg);

        builder.setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
