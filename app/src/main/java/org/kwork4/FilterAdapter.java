package org.kwork4;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.kwork4.databinding.FilterItemBinding;
import org.kwork4.network.Client;
import org.kwork4.network.Filter;
import org.kwork4.network.Filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {

    private List<Filter> data;
    private HashMap<String,String> choice;
    private ClickListener listener;
    private Context context;

    public FilterAdapter(ClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        data = new ArrayList<>();
        choice = new HashMap<>();
        Call<Filters> filtersCall =  Client.getApi().getFilters(100);
        filtersCall.enqueue(new Callback<Filters>() {
           @Override
           public void onResponse(Call<Filters> call, Response<Filters> response) {
               data.addAll(response.body().getFilterList());
               notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<Filters> call, Throwable t) {

           }
       });
    }

    public interface ClickListener {

        void click(String id);
    }
    public boolean empty() {
        return choice.entrySet().isEmpty();
    }
    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterHolder(FilterItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHolder holder, int position) {
        holder.bind(data.get(position), context, choice);
        holder.binding.getRoot().setOnClickListener((view -> {
            if(choice.containsKey(data.get(position).getFields().getName())) {
                choice.remove(data.get(position).getFields().getName());
            } else choice.put(data.get(position).getFields().getName(),null);
            listener.click(data.get(position).getId());
            holder.bind(data.get(position), context, choice);
        }));
    }

    @Override
    public void onViewRecycled(@NonNull FilterHolder holder) {
        holder.binding.getRoot().setOnClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FilterHolder extends RecyclerView.ViewHolder {

        private FilterItemBinding binding;

        public FilterHolder(FilterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Filter filter, Context context, HashMap<String,String> choice) {
            //Log.d("TAG",filter.getFields().getName()+" ||");
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    new int[]{com.google.android.material.R.attr.colorOnPrimary, com.google.android.material.R.attr.colorOnSecondary}
            );
            binding.card.setCardBackgroundColor(a.getColor(choice.containsKey(filter.getFields().getName())==true ? 1 : 0,0));
            binding.filter.setTextColor(a.getColor(choice.containsKey(filter.getFields().getName())==true ? 0 : 1,0));
            a.recycle();
            binding.setFilter(filter);
            //binding.invalidateAll();
        }
    }
}
