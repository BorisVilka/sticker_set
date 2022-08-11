package org.kwork4;


import androidx.recyclerview.widget.DiffUtil;

import org.kwork4.network.StickerPack;

public class ItemCallback extends DiffUtil.ItemCallback<StickerPack> {
    @Override
    public boolean areItemsTheSame(StickerPack oldItem, StickerPack newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(StickerPack oldItem, StickerPack newItem) {
        return oldItem.equals(newItem);
    }
}
