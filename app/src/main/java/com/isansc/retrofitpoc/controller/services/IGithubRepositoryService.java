package com.isansc.retrofitpoc.controller.services;

import com.isansc.retrofitpoc.model.PullRequest;
import com.isansc.retrofitpoc.model.RepositorySearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGithubRepositoryService {
    @GET("search/repositories")
    Call<RepositorySearchResult> getRepositories(@Query("q") String searchText, @Query("page") int page);

    @GET("repos/{ownerName}/{repositoryName}/pulls")
    Call<List<PullRequest>> getPulls(@Path("ownerName") String ownerName, @Path("repositoryName") String repositoryName, @Query("state") String state, @Query("page") int page);
}
