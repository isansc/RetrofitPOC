package com.isansc.retrofitpoc.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.isansc.retrofitpoc.R;
import com.isansc.retrofitpoc.model.Repository;

/**
 * POC Created accessing Github API using Retrofit.
 * Code started based on example at Vogella's website:
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public class MainActivity extends BaseActivity implements RepositoryListFragment.OnListFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SAVE_STATE_CURRENT = "SAVE_STATE_REPOSITORY_LIST";

    private ViewGroup grpMainContainer;
    private RepositoryListFragment frgRepositoryList;
    private Fragment frgCurrentFragment;

    @Override
    public BaseActivity.TransitionType getTransitionType() {
        return BaseActivity.TransitionType.SLIDE_V;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponents(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_repository_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_action_refresh) {
            if(frgCurrentFragment instanceof RepositoryListFragment){
                ((RepositoryListFragment)frgCurrentFragment).refresh();
            }
            else{
                ((PullListFragment)frgCurrentFragment).refresh();
            }
        }
        if (id == R.id.btn_action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void setupComponents(Bundle savedInstanceState){
        grpMainContainer = findViewById(R.id.grp_main_container);
        frgRepositoryList = RepositoryListFragment.newInstance(null);
        frgCurrentFragment = frgRepositoryList;
        getSupportFragmentManager().beginTransaction().replace(R.id.grp_main_container, frgRepositoryList).commit();

        // Check whether we're recreating a previously destroyed instance
//        if (savedInstanceState != null) {
//            Logger.d(TAG, this.getClass().getSimpleName(), "setupComponents", "Restoring saved state");
//
//            // Restore value of members from saved state
//            mHasMore = savedInstanceState.getBoolean(SAVE_STATE_HAS_MORE);
//            mCurrentPage = savedInstanceState.getInt(SAVE_STATE_CURRENT_PAGE);
//            mRepositoriesList = (ArrayList<Repository>) savedInstanceState.getSerializable(SAVE_STATE_REPOSITORY_LIST);
//
//            Logger.d(TAG, this.getClass().getSimpleName(), "setupComponents", "Restored page number: " + mCurrentPage);
//        } else {
//            Logger.d(TAG, this.getClass().getSimpleName(), "setupComponents", "New instance loading");
//            // Probably initialize members with default values for a new instance
//            mHasMore = false;
//            mCurrentPage = 0;
//            mRepositoriesList = new ArrayList<>();
//
//            Logger.d(TAG, this.getClass().getSimpleName(), "setupComponents", "Initial Page number: " + mCurrentPage);
//        }
    }

    @Override
    public void onListFragmentInteraction(Repository item) {
        Toast.makeText(this, "Clicked on: " +item.getName(), Toast.LENGTH_SHORT).show();
    }

//
//    private void getRepositories(){
//        RepositoryController repositoryController = new RepositoryController();
//        repositoryController.getRepositories("language:Java", 0, new CallbackRepositorySearchResult() {
//            @Override
//            public void onResponse(RepositorySearchResult response) {
//                if(response != null && response.getItems() != null && response.getItems().size() > 0){
//                    for (Repository repository : response.getItems()) {
//                        Log.d("Repository", repository.getFullName());
//
//                        if(response.getItems().indexOf(repository) == response.getItems().size()-1){
//                            getPullrequests(repository);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RepositorySearchResult> call, Throwable t) {
//                Log.e("Repository", "searchError", t);
//            }
//        });
//    }

//    private void getPullrequests(Repository repository){
//        Log.d("Query Pull Request", "Owner: " + repository.getOwner().getLogin() + "Repository Name: " + repository.getName());
//        RepositoryController repositoryController = new RepositoryController();
//        repositoryController.getRepositoryPulls(repository.getOwner().getLogin(), repository.getName(), 0, new CallbackPullRequestList() {
//            @Override
//            public void onResponse(List<PullRequest> response) {
//                if(response != null && response.size() > 0){
//                    for (PullRequest pullRequest : response) {
//                        Log.d("Pull Request", pullRequest.getTitle());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PullRequest>> call, Throwable t) {
//
//            }
//        });
//    }
}
