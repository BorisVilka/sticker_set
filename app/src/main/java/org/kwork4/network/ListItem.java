package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItem {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("fields")
    @Expose
    ListField field;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ListField getField() {
        return field;
    }

    public void setField(ListField field) {
        this.field = field;
    }
}
