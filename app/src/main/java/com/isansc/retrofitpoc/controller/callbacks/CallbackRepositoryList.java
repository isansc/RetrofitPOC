package com.isansc.retrofitpoc.controller.callbacks;

import com.isansc.retrofitpoc.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackRepositoryList implements Callback<List<Repository>> {

    public abstract void onResponse(List<Repository> response);

    @Override
    public final void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
        if(response.isSuccessful()) {
            List<Repository> changesList = response.body();
            onResponse(changesList);
        } else {
            System.out.println(response.errorBody());
            onFailure(call, new InternalError(String.valueOf(response.errorBody())));
        }
    }
}
