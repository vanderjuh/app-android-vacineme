package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Relationship implements Parcelable {

    private String descricao;
    private Drawable icon;

    public Relationship(String descricao) {
        this.descricao = descricao;
    }

    public Relationship(String descricao, Drawable icon) {
        this.descricao = descricao;
        this.icon = icon;
    }

    public Relationship(Parcel in){
        this.descricao = in.readString();
        Bitmap bitmap = (Bitmap)in.readParcelable(getClass().getClassLoader());
        this.icon = new BitmapDrawable(bitmap);
    }

    public static final Creator<Relationship> CREATOR = new Creator<Relationship>() {
        @Override
        public Relationship createFromParcel(Parcel in) {
            return new Relationship(in);
        }

        @Override
        public Relationship[] newArray(int size) {
            return new Relationship[size];
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
