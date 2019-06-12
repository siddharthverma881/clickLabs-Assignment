package com.example.assignment6.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.assignment6.retrofit.ApiClient;
import com.example.assignment6.R;
import com.example.assignment6.adapters.FullListAdapter;
import com.example.assignment6.adapters.PostIdAdapter;
import com.example.assignment6.interfaces.ApisInterface;
import com.example.assignment6.models.PostId;
import com.example.assignment6.models.User;
import com.example.assignment6.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private ApiClient apiClient = new ApiClient();
    Constants constants = new Constants();
    private int mUserId;
    private String mStringId,mName;
    private TextView mTvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        mProgressBar = findViewById(R.id.show_details_progress_bar);
        mTvUserName = findViewById(R.id.show_details_tv_username);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStringId = bundle.getString(constants.KEY_ID);
        mName = bundle.getString(constants.KEY_NAME);
        mUserId = Integer.parseInt(mStringId);
        Retrofit retrofit = apiClient.getRetrofit();

        ApisInterface apis = retrofit.create(ApisInterface.class);

        Call<List<PostId>> call = apis.getComments(mUserId);
        call.enqueue(new Callback<List<PostId>>() {
            @Override
            public void onResponse(Call<List<PostId>> call, Response<List<PostId>> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<PostId>> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mTvUserName.setText(getString(R.string.show_tv_name) + mName);

    }

    /**
     *
      * @param usersList contains the list of user details fetched from api
     */
    public void generateDataList(List<PostId> usersList){
        mRecyclerView = findViewById(R.id.show_details_recycler_view);
        mLayoutManager = new LinearLayoutManager(ShowDetailsActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PostIdAdapter(ShowDetailsActivity.this,usersList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
