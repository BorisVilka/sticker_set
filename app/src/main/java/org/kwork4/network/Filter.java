package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("createdTime")
    @Expose
    String createdTime;

    @SerializedName("fields")
    @Expose
    FiltersFields fields;

    private boolean choosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public FiltersFields getFields() {
        return fields;
    }

    public void setFields(FiltersFields fields) {
        this.fields = fields;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }
}
