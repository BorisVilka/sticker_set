package org.kwork4.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("appkIw6x7RVjNg45O/stickers")
    Call<Answer> getList(@Query("maxRecords") int pageSize, @Query("offset") String offset);

    @GET("appkIw6x7RVjNg45O/kategory")
    Call<Filters> getFilters(@Query("maxRecords") int pageSize);

    @GET("appkIw6x7RVjNg45O/list")
    Call<ListBase> getListItems(@Query("maxRecords") int pageSize, @Query("offset") String offset);

}
