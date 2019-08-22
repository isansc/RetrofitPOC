package com.isansc.retrofitpoc.controller.callbacks;


import com.isansc.retrofitpoc.model.PullRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackPullRequestList implements Callback<List<PullRequest>> {

    public abstract void onResponse(List<PullRequest> response);

    @Override
    public final void onResponse(Call<List<PullRequest>> call, Response<List<PullRequest>> response) {
        if(response.isSuccessful()) {
            List<PullRequest> changesList = response.body();
            onResponse(changesList);
        } else {
            System.out.println(response.errorBody());
            onFailure(call, new InternalError(String.valueOf(response.errorBody())));
        }
    }
}
