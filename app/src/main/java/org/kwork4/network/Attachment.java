package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attachment implements Serializable {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("width")
    @Expose
    int width;

    @SerializedName("height")
    @Expose
    int height;

    @SerializedName("size")
    @Expose
    int size;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("filename")
    @Expose
    String filename;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("thumbnails")
    @Expose
    Thumbnails thumbnails;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
