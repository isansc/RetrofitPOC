package com.isansc.retrofitpoc.controller;

import com.isansc.retrofitpoc.controller.base.GithubController;
import com.isansc.retrofitpoc.controller.callbacks.CallbackPullRequestList;
import com.isansc.retrofitpoc.controller.callbacks.CallbackRepositorySearchResult;
import com.isansc.retrofitpoc.controller.services.IGithubRepositoryService;
import com.isansc.retrofitpoc.model.PullRequest;
import com.isansc.retrofitpoc.model.RepositorySearchResult;

import java.util.List;

import retrofit2.Call;

public class RepositoryController  extends GithubController {
    private static final String PARAM_STATE_DEFAULT_VALUE = "all";

    private IGithubRepositoryService mService;

    public RepositoryController(){
        super();
        mService = super.getRetrofit().create(IGithubRepositoryService.class);
    }

    public void getRepositories(String searchText, int page, CallbackRepositorySearchResult callback){
        Call<RepositorySearchResult> call = mService.getRepositories(searchText, page);
        call.enqueue(callback);
    }

    public void getRepositoryPulls(String ownerName, String repositoryName, int page, CallbackPullRequestList callback){
        Call<List<PullRequest>> call = mService.getPulls(ownerName, repositoryName, PARAM_STATE_DEFAULT_VALUE, page);
        call.enqueue(callback);
    }
}
