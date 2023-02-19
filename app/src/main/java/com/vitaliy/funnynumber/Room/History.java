package com.vitaliy.funnynumber.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "number")
    public String number;

    @ColumnInfo(name = "fact")
    public String fact;

    public History(String number, String fact) {
        this.number = number;
        this.fact = fact;
    }

    @Override
    public String toString() {
        return "History{" +
                "uid=" + uid +
                ", number='" + number + '\'' +
                ", fact='" + fact + '\'' +
                '}';
    }

    protected History(Parcel in) {
        uid = in.readInt();
        number = in.readString();
        fact = in.readString();
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(number);
        parcel.writeString(fact);
    }

}
