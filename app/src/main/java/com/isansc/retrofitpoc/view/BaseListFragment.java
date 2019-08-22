//package com.isansc.retrofitpoc;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.isansc.retrofitpoc.dummy.DummyContent;
//import com.isansc.retrofitpoc.dummy.DummyContent.DummyItem;
//
//import java.util.ArrayList;
//
///**
// * A fragment representing a list of Items.
// * <p/>
// * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
// * interface.
// */
//public abstract class BaseListFragment<T> extends Fragment {
//
//    private OnListFragmentInteractionListener mListener;
//
//    private ArrayList<T> mItemsList;
//    private ViewGroup mGrpEmpty;
//    private ViewGroup mGrpError;
//    private ViewGroup mGrpLoading;
//    private TextView mTxtErrorMessage;
//
//    /*
//    * Method to return the recyclerview of the implemented fragment
//    */
//    protected abstract RecyclerView getRecyclerView();
//    protected abstract RecyclerView getRecyclerViewAdapter();
//
//    /**
//     * Mandatory empty constructor for the fragment manager to instantiate the
//     * fragment (e.g. upon screen orientation changes).
//     */
//    public BaseListFragment() {
//
//    }
//
////    // TODO: Customize parameter initialization
////    @SuppressWarnings("unused")
////    public static BaseListFragment newInstance() {
////        BaseListFragment fragment = new BaseListFragment();
////        Bundle args = new Bundle();
//////        args.putInt(ARG_COLUMN_COUNT, columnCount);
////        fragment.setArguments(args);
////        return fragment;
////    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
////        }
//
//        mGrpEmpty = getActivity().findViewById(R.id.grp_empty);
//        mGrpLoading = getActivity().findViewById(R.id.grp_loading);
//        mGrpError = getActivity().findViewById(R.id.grp_error);
//        mTxtErrorMessage = mGrpError.findViewById(R.id.txt_error_message);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        if(container != null && container instanceof RelativeLayout){
//            mGrpEmpty = (ViewGroup) inflater.inflate(R.layout.card_list_empty, container, false);
//            mGrpLoading =  (ViewGroup) inflater.inflate(R.layout.card_list_loading, container, false);
//            mGrpError = (ViewGroup) inflater.inflate(R.layout.card_list_error, container, false);
//            mTxtErrorMessage = mGrpError.findViewById(R.id.txt_error_message);
////            ViewGroup view = inflater.inflate(R.layout.fragment_repository_list, container, false);
//        }
//
//        // Set the adapter
//        if (getRecyclerView() != null) {
//            Context context = getRecyclerView().getContext();
//            getRecyclerView().setLayoutManager(new LinearLayoutManager(context));
//            getRecyclerView().setAdapter(new RepositoryRecyclerViewAdapter(DummyContent.ITEMS, mListener));
//            //recyclerView.addItemDecoration(new ColorDividerItemDecoration(2, 0xff363b54));
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
//                    ((LinearLayoutManager)getRecyclerView().getLayoutManager()).getOrientation());
//            getRecyclerView().addItemDecoration(dividerItemDecoration);
//
//        }
//        return view;
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public void onSearching(){
//        if(mItemsList == null || mItemsList.size() == 0){
//            // First loading
//            mRecyclerView.setVisibility(View.GONE);
//            mGrpEmpty.setVisibility(View.GONE);
//            mGrpLoading.setVisibility(View.VISIBLE);
//            mGrpError.setVisibility(View.GONE);
//        }
//        else{
//            // Loading page
//        }
//    }
//
//    public void onResult(){
//        if(mItemsList == null || mItemsList.size() == 0){
//            // Empty List
//            mRecyclerView.setVisibility(View.GONE);
//            mGrpEmpty.setVisibility(View.VISIBLE);
//            mGrpLoading.setVisibility(View.GONE);
//            mGrpError.setVisibility(View.GONE);
//        }
//        else{
//            mRecyclerView.setVisibility(View.VISIBLE);
//            mGrpEmpty.setVisibility(View.GONE);
//            mGrpLoading.setVisibility(View.GONE);
//            mGrpError.setVisibility(View.GONE);
//        }
//    }
//
//    public void onError(String message){
//        if(mItemsList == null || mItemsList.size() == 0){
//            // Empty List and error
//            mRecyclerView.setVisibility(View.GONE);
//            mGrpEmpty.setVisibility(View.GONE);
//            mGrpLoading.setVisibility(View.GONE);
//            mGrpError.setVisibility(View.VISIBLE);
//            mTxtErrorMessage.setText(message);
//        }
//        else{
//            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
//    }
//}
