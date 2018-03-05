package com.ssynhtn.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LYK on 2017/6/29.
 */

public class NotificationModel implements Parcelable {

    private String title;

    private String content;

    private String classType;

    private String josn;

    private int requestCode;


    public NotificationModel() {
    }

    public NotificationModel(String title, String content, String classType, String josn, int requestCode) {
        this.title = title;
        this.content = content;
        this.classType = classType;
        this.josn = josn;
        this.requestCode = requestCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getJosn() {
        return josn;
    }

    public void setJosn(String josn) {
        this.josn = josn;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", classType=" + classType +
                ", josn='" + josn + '\'' +
                ", requestCode=" + requestCode +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.classType);
        dest.writeString(this.josn);
        dest.writeInt(this.requestCode);
    }

    protected NotificationModel(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.classType = in.readString();
        this.josn = in.readString();
        this.requestCode = in.readInt();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel source) {
            return new NotificationModel(source);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };
}
