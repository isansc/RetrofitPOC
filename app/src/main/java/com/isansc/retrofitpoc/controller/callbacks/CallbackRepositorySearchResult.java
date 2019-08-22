package com.isansc.retrofitpoc.controller.callbacks;

import com.isansc.retrofitpoc.model.RepositorySearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackRepositorySearchResult implements Callback<RepositorySearchResult> {

    public abstract void onResponse(RepositorySearchResult response);

    @Override
    public final void onResponse(Call<RepositorySearchResult> call, Response<RepositorySearchResult> response) {
        if(response.isSuccessful()) {
            RepositorySearchResult result = response.body();
            onResponse(result);
        } else {
            System.out.println(response.errorBody());
            onFailure(call, new InternalError(String.valueOf(response.errorBody())));
        }
    }
}
