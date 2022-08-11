package org.kwork4;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;

import org.kwork4.network.StickerPack;

import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PagedViewModel extends ViewModel {

    private PagedAdapter adapter;

    public PagedViewModel() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(30)
                .build();
        PagedDataSource dataSource = new PagedDataSource();
        adapter = new PagedAdapter(new ItemCallback(),(pack)->{
            NavController navController = Navigation.findNavController(MainActivity.getInstance(),R.id.nav_host_fragment_activity_main);
            Bundle args = new Bundle();
            args.putSerializable("sticker",pack);
            navController.navigate(R.id.stickerFragment,args);
        });
        Single.create(emitter -> {
            PagedList<StickerPack> stickerPacks = new PagedList.Builder(dataSource,config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(new ListFragment.MainThreadExecutor())
                    .build();
            emitter.onSuccess(stickerPacks);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    adapter.submitList((PagedList<StickerPack>) o);
                });
    }


    public PagedAdapter getAdapter() {
        return adapter;
    }
}
