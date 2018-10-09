package br.edu.utfpr.vanderleyjunioralunos.vacineme.entities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Relationship implements Parcelable {

    private String description;
    private Drawable icon;

    public Relationship(String description) {
        this.description = description;
    }

    public Relationship(String description, Drawable icon) {
        this.description = description;
        this.icon = icon;
    }

    public Relationship(Parcel in){
        this.description = in.readString();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(this.description);
        Bitmap bitmap = (Bitmap)((BitmapDrawable) this.icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
    }

}
