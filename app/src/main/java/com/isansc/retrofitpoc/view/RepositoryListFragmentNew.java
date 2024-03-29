//package com.isansc.retrofitpoc.view;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.isansc.retrofitpoc.R;
//import com.isansc.retrofitpoc.dummy.DummyContent;
//import com.isansc.retrofitpoc.dummy.DummyContent.DummyItem;
//
///**
// * A fragment representing a list of Items.
// * <p/>
// * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
// * interface.
// */
//public class RepositoryListFragmentNew extends BaseListFragment<Repo> {
//
//    private OnListFragmentInteractionListener mListener;
//
//    /**
//     * Mandatory empty constructor for the fragment manager to instantiate the
//     * fragment (e.g. upon screen orientation changes).
//     */
//    public RepositoryListFragmentNew() {
//    }
//
//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static RepositoryListFragmentNew newInstance(int columnCount) {
//        RepositoryListFragmentNew fragment = new RepositoryListFragmentNew();
//        Bundle args = new Bundle();
////        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        if (getArguments() != null) {
////            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
////        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_repository_list, container, false);
//
//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setAdapter(new RepositoryRecyclerViewAdapter(DummyContent.ITEMS, mListener));
//            //recyclerView.addItemDecoration(new ColorDividerItemDecoration(2, 0xff363b54));
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                    ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation());
//            recyclerView.addItemDecoration(dividerItemDecoration);
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
