package org.kwork4.network;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class StickerPack implements Serializable {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("createdTime")
    @Expose
    String createdTime;

    @SerializedName("fields")
    @Expose
    Fields fields;

    @SerializedName("category")
    @Expose
    String category;

    @SerializedName("Attachments")
    @Expose
    List<Attachment> attachments;


    @NonNull
    @Override
    public String toString() {
        return fields.title+" "+id+" "+createdTime+" "+category+" "+fields.aftor+" "+fields.tg+" "+fields.vb;
    }

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

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
