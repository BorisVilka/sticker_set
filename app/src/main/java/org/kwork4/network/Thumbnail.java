package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnail implements Serializable {

    @SerializedName("height")

    int height;

    @SerializedName("width")

    int width;

    @SerializedName("url")

    String url;
}
