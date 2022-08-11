package org.kwork4;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import org.kwork4.network.Answer;
import org.kwork4.network.Client;
import org.kwork4.network.StickerPack;

import java.security.Key;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagedDataSource extends PageKeyedDataSource<String, StickerPack> {

    private String offset, old;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String,StickerPack> callback) {
        Call<Answer> call = Client.getApi().getList(Integer.MAX_VALUE, "");
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                Answer answer = response.body();
                old = offset+"";
                offset = answer.getOffset();
                Log.d("TAG",offset+"");
                callback.onResult(answer.getList(),"",answer.getOffset());
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull  LoadCallback<String,StickerPack> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, StickerPack> callback) {
        Call<Answer> call = Client.getApi().getList(Integer.MAX_VALUE, offset);
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                Answer answer = response.body();
                old = offset+"";
                offset = answer.getOffset();
                callback.onResult(answer.getList(),answer.getOffset());
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {

            }
        });
    }
}
