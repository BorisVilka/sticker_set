package org.kwork4;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.kwork4.databinding.ListItemBinding;
import org.kwork4.network.StickerPack;

import java.util.List;

public class PagedAdapter extends PagedListAdapter<StickerPack, PagedAdapter.ListHolder> {

    private ClickListener listener;

    protected PagedAdapter(@NonNull DiffUtil.ItemCallback<StickerPack> diffCallback
    , ClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    public interface ClickListener {
        void click(StickerPack stickerPack);
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagedAdapter.ListHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  ListHolder holder, int position) {
        //Log.d("TAG",getItemCount()+"");
        holder.bind(getItem(position));
        holder.binding.getRoot().setOnClickListener(view -> listener.click(getItem(position)));
    }

    @Override
    public void onViewRecycled(@NonNull ListHolder holder) {
        holder.binding.getRoot().setOnClickListener(null);
        super.onViewRecycled(holder);
    }

    public static class ListHolder extends RecyclerView.ViewHolder {

        private ListItemBinding binding;

        public ListHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StickerPack stickerPack) {
          //  Log.d("TAG",stickerPack.getFields().getList()+" ||");
            binding.title.setText(stickerPack.getFields().getTitle());
            binding.author.setText(stickerPack.getFields().getAftor());
            if(stickerPack.getFields().getAttachments()!=null) {
                Picasso.get().load(stickerPack.getFields().getAttachments().get(0).getUrl()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("TAG",from.toString()+" "+from.name());
                        binding.logo.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                            binding.logo.setImageDrawable(placeHolderDrawable);
                    }
                });
            } else {
                binding.logo.setImageDrawable(null);
            }
        }
    }
}
