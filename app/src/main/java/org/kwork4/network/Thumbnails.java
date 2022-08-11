package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnails implements Serializable {

    @SerializedName("small")

    Thumbnail small;

    @SerializedName("large")

    Thumbnail large;

    @SerializedName("full")

    Thumbnail full;

}
