package org.kwork4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.kwork4.databinding.SpinnerItemBinding;
import org.kwork4.network.StickerPack;

import java.util.Locale;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private int[] colors;
    private StickerPack stickerPack;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects, StickerPack stickerPack) {
        super(context, resource, objects);
        this.stickerPack = stickerPack;
        colors = new int[]{R.color.vb,R.color.tg,R.color.ok};
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getView(getItem(position),parent,position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getView(getItem(position),parent,position);
    }


    private View getView(String txt,ViewGroup parent, int pos) {
        SpinnerItemBinding binding = SpinnerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        binding.button.setText(txt);
        if(getItem(pos).equals(getContext().getString(R.string.vb))) {
            binding.button.setBackgroundColor(getContext().getColor(colors[0]));
        } else if(getItem(pos).equals(getContext().getString(R.string.tg))) {
            binding.button.setBackgroundColor(getContext().getColor(colors[1]));
         } else {
            binding.button.setBackgroundColor(getContext().getColor(colors[2]));
         }
        binding.button.setOnClickListener(view -> {
            if(stickerPack.getFields().getVb()==null) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(getItem(pos).equals(getContext().getString(R.string.vb))) {
                if(stickerPack.getFields().getVb()==null) return;
                binding.button.setBackgroundColor(getContext().getColor(colors[0]));
                intent.setData(Uri.parse(stickerPack.getFields().getVb()));
            } else if(getItem(pos).equals(getContext().getString(R.string.tg))) {
                if(stickerPack.getFields().getTg()==null) return;
                binding.button.setBackgroundColor(getContext().getColor(colors[1]));
                intent.setData(Uri.parse(stickerPack.getFields().getTg()));
            } else {
                if(stickerPack.getFields().getOk()==null) return;
                binding.button.setBackgroundColor(getContext().getColor(colors[2]));
                intent.setData(Uri.parse(stickerPack.getFields().getOk()));
            }
            getContext().startActivity(intent);
        });
        return binding.getRoot();
    }
}
