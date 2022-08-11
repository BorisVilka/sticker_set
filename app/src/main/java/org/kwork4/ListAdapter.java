package org.kwork4;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.kwork4.databinding.ListItemBinding;
import org.kwork4.network.Answer;
import org.kwork4.network.Client;
import org.kwork4.network.StickerPack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    private List<StickerPack> data, all;
    private ClickListener listener;
    private HashMap<String,String> choice;

    public ListAdapter(ClickListener listener, List<StickerPack> stickerPacks) {
        this.listener = listener;
        choice = new HashMap<>();
        all = new ArrayList<>();
        data = new ArrayList<>();
        all.addAll(stickerPacks);
    }

    public interface ClickListener {
        void click(StickerPack stickerPack);
    }
    public void setList(List<StickerPack> list) {
        all = list;
    }
    public void requestFilter(String id) {
        if(choice.containsKey(id)) {
            choice.remove(id);
        } else choice.put(id,null);
        if(choice.entrySet().isEmpty()) return;
        data.clear();
        Log.d("TAG",id+" "+choice.entrySet().size());
        for(StickerPack pack:all) {
            if(pack.getFields().getCategories()!=null) Log.d("TAG",pack.getFields().getCategories().toString());
            boolean tmp = true;
            label:for(Map.Entry<String,String> i:choice.entrySet()) {
                if(pack.getFields().getCategories()==null) {
                    tmp = false;
                    break;
                }
                Log.d("TAG",pack.getFields().getCategories().contains(i.getKey())+"|");
                for(String j:pack.getFields().getCategories()) {
                    if(j.equals(i.getKey())) {
                        continue label;
                    }
                }
                tmp = false;
            }
            if(tmp) data.add(pack);
        }
        Log.d("TAG",data.size()+" ||||");
        notifyDataSetChanged();
    }

    public void setText(String text) {
        data.clear();
        for(StickerPack i:all) if(i.getFields().getTitle().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
        || i.getFields().getAftor().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) data.add(i);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            holder.bind(data.get(position));
            holder.binding.getRoot().setOnClickListener(view -> listener.click(data.get(position)));
    }

    @Override
    public void onViewRecycled(@NonNull ListHolder holder) {
        holder.binding.getRoot().setOnClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {

        private ListItemBinding binding;

        public ListHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StickerPack stickerPack) {
            Log.d("TAG",stickerPack.getFields().getList()+" ||");
            binding.title.setText(stickerPack.getFields().getTitle());
            binding.author.setText(stickerPack.getFields().getAftor());
            if(stickerPack.getFields().getAttachments()!=null) Picasso.get().load(stickerPack.getFields().getAttachments().get(0).getUrl()).into(binding.logo);
        }
    }
}
