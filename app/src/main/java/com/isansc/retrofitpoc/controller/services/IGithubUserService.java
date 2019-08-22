package com.isansc.retrofitpoc.controller.services;

import com.isansc.retrofitpoc.model.RepositorySearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGithubUserService {
    @GET("search/repositories")
    Call<RepositorySearchResult> getRepositories(@Query("q") String searchText, @Query("page") int page);
}
