package com.isansc.retrofitpoc.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.isansc.retrofitpoc.R;
import com.isansc.retrofitpoc.controller.Logger;
import com.isansc.retrofitpoc.controller.RepositoryController;
import com.isansc.retrofitpoc.controller.callbacks.CallbackRepositorySearchResult;
import com.isansc.retrofitpoc.model.Repository;
import com.isansc.retrofitpoc.model.RepositorySearchResult;
import com.isansc.retrofitpoc.view.components.EndlessRecyclerViewScrollListener;
import com.isansc.retrofitpoc.view.components.RepositoryRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RepositoryListFragment extends Fragment {
    private static final String TAG = RepositoryListFragment.class.getSimpleName();

    private static final String SAVE_STATE_REPOSITORY_LIST = "SAVE_STATE_REPOSITORY_LIST";
    private static final String SAVE_STATE_REPOSITORY_HAS_MORE = "SAVE_STATE_REPOSITORY_HAS_MORE";
    private static final String SAVE_STATE_REPOSITORY_CURRENT_PAGE = "SAVE_STATE_REPOSITORY_CURRENT_PAGE";

    private enum Status { SEARCHING, RESULT, ERROR}

    private OnListFragmentInteractionListener mListener;

    private boolean mHasMore = false;
    private int mCurrentPage = 0;
    private ArrayList<Repository> mItemsList = new ArrayList<>();

    private ViewGroup mGrpEmpty;
    private ViewGroup mGrpError;
    private ViewGroup mGrpLoading;
    private ProgressBar mPgbLoading;
    private TextView mTxtErrorMessage;
    private RecyclerView mRcvList;
    private SwipeRefreshLayout mSwpRefresh = null;
    private RepositoryRecyclerViewAdapter mAdpList;
    private Status mCurrentState = Status.SEARCHING;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RepositoryListFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RepositoryListFragment newInstance(Bundle savedInstanceState) {
        RepositoryListFragment fragment = new RepositoryListFragment();
//        Bundle args = new Bundle();
//        args = savedInstanceState;
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(savedInstanceState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_list, container, false);

        mGrpLoading = view.findViewById(R.id.grp_loading);
        mGrpLoading.setVisibility(View.VISIBLE);
        mPgbLoading = view.findViewById(R.id.pgb_loading);
        mGrpEmpty = view.findViewById(R.id.grp_empty);
        mGrpError = view.findViewById(R.id.grp_error);
        mTxtErrorMessage = mGrpError.findViewById(R.id.txt_error_message);
        mAdpList = new RepositoryRecyclerViewAdapter(getContext(), mItemsList, mListener);
        mRcvList = view.findViewById(R.id.rcv_list);;
        LinearLayoutManager llmRepositories = new LinearLayoutManager(getContext());
        mRcvList.setLayoutManager(llmRepositories);
        mRcvList.setAdapter(mAdpList);
        //recyclerView.addItemDecoration(new ColorDividerItemDecoration(2, 0xff363b54));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), llmRepositories.getOrientation());
        mRcvList.addItemDecoration(dividerItemDecoration);
        mSwpRefresh = view.findViewById(R.id.swp_refresh);

        mRcvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(mCurrentPage, llmRepositories, mSwpRefresh) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Logger.d(TAG, this.getClass().getSimpleName(), "EndlessRecyclerViewScrollListener.onLoadMore", "Requesting loading. Current mCurrentPage: " + mCurrentPage);
                Logger.d(TAG, this.getClass().getSimpleName(), "EndlessRecyclerViewScrollListener.onLoadMore", "Requesting loading. Trying to retrieve page (mCurrentPage+1): " + (mCurrentPage + 1));
                searchRepositories(mCurrentPage + 1);
            }
        });

        if (mSwpRefresh != null) {
            mSwpRefresh.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            refresh();
                        }
                    }
            );
        }

        setStatus(Status.SEARCHING); // Initial State
        searchRepositories();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void searchRepositories(){
        searchRepositories(0);
    }
    private void searchRepositories(int page){
        if(page == 0){
            mHasMore = true;
            mCurrentPage = 0;
            mItemsList.clear();
            mAdpList.notifyDataSetChanged();
            //retrieveRepositories(mCurrentPage + 1);
        }

        setStatus(Status.SEARCHING);
        RepositoryController repositoryController = new RepositoryController();
        repositoryController.getRepositories("language:Java", page, new CallbackRepositorySearchResult() {
            @Override
            public void onResponse(RepositorySearchResult response) {
                if(response != null && response.getItems() != null && response.getItems().size() > 0) {

                    mCurrentPage = mCurrentPage+1;
                    mItemsList.addAll(response.getItems());
                    mAdpList.notifyDataSetChanged();
                }
                else{
                    mHasMore = false;
                }
                setStatus(Status.RESULT);
            }

            @Override
            public void onFailure(Call<RepositorySearchResult> call, Throwable t) {
                Log.e("Repository", "searchError", t);
                setStatusError(t.getMessage());
            }
        });
    }

    private void setStatusError(String message){
        setStatus(Status.ERROR, message);
    }
    private void setStatus(Status status) {
        if(isAdded()){
            setStatus(status, getString(R.string.message_error_communication_unknown));
        }
    }
    private void setStatus(Status status, String errorMessage){
        mPgbLoading.setVisibility(View.GONE);
        if (mSwpRefresh != null && mSwpRefresh.isRefreshing()) {
            mSwpRefresh.setRefreshing(false);
        }

        mCurrentState = status;
        switch (status) {
            case SEARCHING:
                if(mItemsList == null || mItemsList.size() == 0){
                    // First loading
                    if (mSwpRefresh != null && !mSwpRefresh.isRefreshing()) {
                        mSwpRefresh.setRefreshing(true);
                    }

                    mRcvList.setVisibility(View.GONE);
                    mGrpEmpty.setVisibility(View.GONE);
                    mGrpError.setVisibility(View.GONE);
                }
                else{
                    // Loading pagination
                    mPgbLoading.setVisibility(View.VISIBLE);
                    if (mSwpRefresh != null && !mSwpRefresh.isRefreshing()) {
                        mSwpRefresh.setRefreshing(true);
                    }
                }
                break;
            case RESULT:
                if(mItemsList == null || mItemsList.size() == 0){
                    // Empty List
                    mRcvList.setVisibility(View.GONE);
                    mGrpEmpty.setVisibility(View.VISIBLE);
                    mGrpLoading.setVisibility(View.GONE);
                    mGrpError.setVisibility(View.GONE);
                }
                else{
                    mRcvList.setVisibility(View.VISIBLE);
                    mGrpEmpty.setVisibility(View.GONE);
                    mGrpLoading.setVisibility(View.GONE);
                    mGrpError.setVisibility(View.GONE);
                }
                break;
            case ERROR:
                if(mItemsList == null || mItemsList.size() == 0){
                    // Empty List and error
                    mRcvList.setVisibility(View.GONE);
                    mGrpEmpty.setVisibility(View.GONE);
                    mGrpLoading.setVisibility(View.GONE);
                    mGrpError.setVisibility(View.VISIBLE);
                    mTxtErrorMessage.setText(errorMessage);
                }
                else{
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void refresh(){
        if(!mCurrentState.equals(Status.SEARCHING)){
            searchRepositories();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Repository item);
    }
}
