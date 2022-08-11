package org.kwork4.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Answer {

    @SerializedName("records")
    List<StickerPack> list;

    @SerializedName("offset")
            @Expose
    String offset;

    public List<StickerPack> getList() {
        return list;
    }

    public void setList(List<StickerPack> list) {
        this.list = list;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
