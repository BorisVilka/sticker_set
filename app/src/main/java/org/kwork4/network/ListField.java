package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListField {


    @SerializedName("Attachments")
    @Expose
    List<Attachment> list;


    public List<Attachment> getList() {
        return list;
    }

    public void setList(List<Attachment> list) {
        this.list = list;
    }
}
