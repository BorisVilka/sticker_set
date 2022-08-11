package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListBase {

    @SerializedName("records")
    @Expose
    List<ListItem> records;


    @SerializedName("offset")
    String offset;

    public List<ListItem> getRecords() {
        return records;
    }

    public void setRecords(List<ListItem> records) {
        this.records = records;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
