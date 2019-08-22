package com.isansc.retrofitpoc.view.components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.isansc.retrofitpoc.R;
import com.isansc.retrofitpoc.controller.base.GlideApp;
import com.isansc.retrofitpoc.model.Repository;
import com.isansc.retrofitpoc.view.RepositoryListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Repository} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<RepositoryRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Repository> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RepositoryRecyclerViewAdapter(Context context, ArrayList<Repository> items, OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_repository, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        // - get element from the dataset at this position
        final Repository repository = mValues.get(position);

        holder.mCardView.setClickable(true);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mRepository);
                }
            }
        });

//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PullRequestActivity.class);
//                intent.putExtra(PullRequestActivity.INTENT_EXTRA_REPOSITORY, repository);
//                mContext.startActivity(intent);
//            }
//        });


        holder.mRepository = repository;
        holder.mTxtRepoName.setText(repository.getFullName());
        holder.mTxtRepoDescription.setText(repository.getDescription());
        holder.mTxtRepoForksCount.setText(String.format(Locale.getDefault(), "%d", repository.getForks()));
        holder.mTxtRepoStarsCount.setText(String.format(Locale.getDefault(), "%d", repository.getScore()));
        holder.mTxtUserName.setText(repository.getOwner().getLogin());
        holder.mTxtUserType.setText(repository.getOwner().getType());

        GlideApp.with(mContext)
                .load(repository.getOwner().getAvatarUrl())
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_github)
                .into(holder.mImgUserPhoto);

//        holder.mImgUserPhoto.setDefaultImageResId(R.drawable.ic_github);
//        holder.mImgUserPhoto.setErrorImageResId(R.drawable.ic_github);
//        if(!TextUtils.isEmpty(repository.getOwner().getAvatarUrl())){
//            holder.mImgUserPhoto.setImageUrl(repository.getOwner().getAvatarUrl(), CommunicationManager.getInstance().getImageLoader());
//        }


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Repository mRepository;
        CardView mCardView;
        TextView mTxtRepoName;
        TextView mTxtRepoDescription;
        TextView mTxtRepoForksCount;
        TextView mTxtRepoStarsCount;
        TextView mTxtUserName;
        TextView mTxtUserType;
        ImageView mImgUserPhoto;

        ViewHolder(View view) {
            super(view);
            mCardView = itemView.findViewById(R.id.cdv_repository);
            mTxtRepoName = mCardView.findViewById(R.id.txt_card_title);
            mTxtRepoDescription = mCardView.findViewById(R.id.txt_card_description);
            mTxtRepoForksCount = mCardView.findViewById(R.id.txt_fork_count);
            mTxtRepoStarsCount = mCardView.findViewById(R.id.txt_star_count);
            mTxtUserName = mCardView.findViewById(R.id.txt_card_user_name);
            mTxtUserType = mCardView.findViewById(R.id.txt_card_user_type);
            mImgUserPhoto = mCardView.findViewById(R.id.img_photo);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
