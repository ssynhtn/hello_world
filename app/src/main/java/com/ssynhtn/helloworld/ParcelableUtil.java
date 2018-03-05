package com.ssynhtn.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangtongnao on 2017/11/1.
 */

public class ParcelableUtil {
    public static byte[] marshall(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unMarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    public static <T> T unMarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unMarshall(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}
