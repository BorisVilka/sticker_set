package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Fields implements Serializable {

    @SerializedName("aftor link")
    @Expose
    String aftor_link;

    @SerializedName("vb")
    @Expose
    String vb;

    @SerializedName("ok")
    @Expose
    String ok;

    @SerializedName("Attachments")
    @Expose
    List<Attachment> attachments;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("aftor")
    @Expose
    String aftor;

    @SerializedName("tg")
    @Expose
    String tg;

    @SerializedName("kategorya")
    @Expose
    List<String> categories;

    @SerializedName("list")
    @Expose
    List<String> list;

    public String getAftor_link() {
        return aftor_link;
    }

    public void setAftor_link(String aftor_link) {
        this.aftor_link = aftor_link;
    }

    public String getVb() {
        return vb;
    }

    public void setVb(String vb) {
        this.vb = vb;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAftor() {
        return aftor;
    }

    public void setAftor(String aftor) {
        this.aftor = aftor;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
