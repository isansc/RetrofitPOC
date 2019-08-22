package com.isansc.retrofitpoc.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isansc.retrofitpoc.R;
import com.isansc.retrofitpoc.controller.RepositoryController;
import com.isansc.retrofitpoc.view.components.RepositoryRecyclerViewAdapter;
import com.isansc.retrofitpoc.controller.callbacks.CallbackRepositorySearchResult;
import com.isansc.retrofitpoc.model.PullRequest;
import com.isansc.retrofitpoc.model.RepositorySearchResult;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PullListFragment extends Fragment {

    private enum Status { SEARCHING, RESULT, ERROR}

    private OnListFragmentInteractionListener mListener;

    private ArrayList<PullRequest> mItemsList = new ArrayList<>();
    private ViewGroup mGrpEmpty;
    private ViewGroup mGrpError;
    private ViewGroup mGrpLoading;
    private TextView mTxtErrorMessage;
    private RecyclerView mRcvList;
    private RepositoryRecyclerViewAdapter mAdpList;
    private Status mCurrentState = Status.SEARCHING;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PullListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PullListFragment newInstance() {
        PullListFragment fragment = new PullListFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
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

        mGrpEmpty = view.findViewById(R.id.grp_empty);
        mGrpLoading = view.findViewById(R.id.grp_loading);
        mGrpError = view.findViewById(R.id.grp_error);
        mTxtErrorMessage = mGrpError.findViewById(R.id.txt_error_message);
        //mAdpList = new RepositoryRecyclerViewAdapter(getContext(), mItemsList, mListener);
        mRcvList = view.findViewById(R.id.rcv_list);;
        mRcvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcvList.setAdapter(mAdpList);
        //recyclerView.addItemDecoration(new ColorDividerItemDecoration(2, 0xff363b54));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                ((LinearLayoutManager)mRcvList.getLayoutManager()).getOrientation());
        mRcvList.addItemDecoration(dividerItemDecoration);
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
        setStatus(Status.SEARCHING);
        RepositoryController repositoryController = new RepositoryController();
        repositoryController.getRepositories("language:Java", page, new CallbackRepositorySearchResult() {
            @Override
            public void onResponse(RepositorySearchResult response) {
                if(response != null && response.getItems() != null && response.getItems().size() > 0) {
//                    if(mItemsList == null){
//                        mItemsList = new ArrayList<>();
//                    }

                    //mItemsList.addAll(response.getItems());
                    mAdpList.notifyDataSetChanged();
                    setStatus(Status.RESULT);
                }
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
        setStatus(status, getString(R.string.message_error_communication_unknown));
    }
    private void setStatus(Status status, String errorMessage){
        mCurrentState = status;
        switch (status) {
            case SEARCHING:
                if(mItemsList == null || mItemsList.size() == 0){
                    // First loading
                    mRcvList.setVisibility(View.GONE);
                    mGrpEmpty.setVisibility(View.GONE);
                    mGrpLoading.setVisibility(View.VISIBLE);
                    mGrpError.setVisibility(View.GONE);
                }
                else{
                    // Loading pagination
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
            mItemsList.clear();
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
        void onListFragmentInteraction(PullRequest item);
    }
}
