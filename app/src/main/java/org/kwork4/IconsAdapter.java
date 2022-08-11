package org.kwork4;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.kwork4.databinding.IconItemBinding;
import org.kwork4.network.Client;
import org.kwork4.network.ListBase;
import org.kwork4.network.ListItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.IconHolder> {

    private List<String> data, all;
    private String offset;
    private boolean stop;
    private Disposable disposable;

    public IconsAdapter(List<String> all, Activity activity) {
        data = new ArrayList<>();
        this.all = all;
        stop = true;
        offset = "";
        Completable completable = Completable.create(
                emitter -> {
            label: while(all.size()>0 && stop) {
                if(offset==null) break;
                if(!stop) {
                    emitter.onComplete();
                    break;
                }
                Call<ListBase> listBaseCall = Client.getApi().getListItems(Integer.MAX_VALUE, offset);
                try {
                    Response<ListBase> response = listBaseCall.execute();
                    if(response.body()==null) return;
                    ListBase base = response.body();
                    offset = base.getOffset();
                    Log.d("TAG","OFFSET LIST: "+base.getOffset()+" "+stop);
                    for(ListItem item:base.getRecords()) {
                        if(all.contains(item.getId()) && item.getField().getList()!=null) {
                            data.add(item.getField().getList().get(0).getUrl());
                            all.remove(item.getId());
                        }
                    }
                    Log.d("TAG","LIST SIZE: "+data.size());
                    activity.runOnUiThread(() -> notifyDataSetChanged());
                    if(!stop) {
                        emitter.onComplete();
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
        disposable = completable.subscribe();
    }

    public void stop() {
        disposable.dispose();
        this.stop = false;
    }

    @NonNull
    @Override
    public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IconHolder(IconItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class IconHolder extends RecyclerView.ViewHolder {

        private IconItemBinding binding;

        public IconHolder(IconItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String url) {
            Picasso.get().load(url).into(binding.imageView);
        }
    }
}
