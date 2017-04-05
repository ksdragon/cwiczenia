package com.example.dragon.closeapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP ProDesk on 2017-04-05.
 */

public class Czas implements Parcelable {

    public int czas;
    public Czas(){
        czas =0;
    }
    // obowiązkowy konstruktor tworzy obiekt na podstawie paczki
    protected Czas(Parcel in) {
        // ważna kolejność
        // ..., 3, 2, 1 - odwrotnie niż przy zapisywaniu
        czas = in.readInt();
    }

    public static final Creator<Czas> CREATOR = new Creator<Czas>() {
        @Override
        public Czas createFromParcel(Parcel in) {
            return new Czas(in);
        }

        @Override
        public Czas[] newArray(int size) {
            return new Czas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    // zapisuje do obiekt do paczki
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // ważna kolejność
        // 1,2,3
        parcel.writeInt(czas);
    }
}
