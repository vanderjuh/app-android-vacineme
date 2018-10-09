package br.edu.utfpr.vanderleyjunioralunos.vacineme.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;

public abstract class DateUtil {

    public static String dateFormatter(int day, int month, int year, String dateFormat) {
        String data = "";
        if (day > 0 && day < 10) {
            data += "0" + day + "/";
        } else {
            data += day + "/";
        }
        if (month < 12) {
            month++;
            if (month < 10) {
                data += "0" + month + "/";
            } else {
                data += month + "/";
            }
        } else {
            data += month + "/";
        }
        data += year;
        try {
            Date dataFormatLang = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            return new SimpleDateFormat(dateFormat).format(dataFormatLang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
