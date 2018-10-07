package br.edu.utfpr.vanderleyjunioralunos.vacineme;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Parentesco implements Parcelable {

    private String descricao;
    private Drawable icon;

    public Parentesco(String descricao) {
        this.descricao = descricao;
    }

    public Parentesco(String descricao, Drawable icon) {
        this.descricao = descricao;
        this.icon = icon;
        //Temporario.iconCopy = icon;
    }

    public Parentesco(Parcel in){
        this.descricao = in.readString();
        Bitmap bitmap = (Bitmap)in.readParcelable(getClass().getClassLoader());
        this.icon = new BitmapDrawable(bitmap);
        //Temporario.iconCopy = this.icon;
    }

    public static final Creator<Parentesco> CREATOR = new Creator<Parentesco>() {
        @Override
        public Parentesco createFromParcel(Parcel in) {
            return new Parentesco(in);
        }

        @Override
        public Parentesco[] newArray(int size) {
            return new Parentesco[size];
        }
    };

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.descricao);
        Bitmap bitmap = (Bitmap)((BitmapDrawable) this.icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
    }

}
