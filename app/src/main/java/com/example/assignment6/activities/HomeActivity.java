package com.example.assignment6.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment6.retrofit.ApiClient;
import com.example.assignment6.interfaces.ApisInterface;
import com.example.assignment6.adapters.FullListAdapter;
import com.example.assignment6.R;
import com.example.assignment6.models.User;
import com.example.assignment6.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements FullListAdapter.RecyclerViewInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private TextView mTvId,mTvName;
    private Button mBtnShowPosts,mBtnMoreDetails;
    private ApiClient apiClient = new ApiClient();
    Constants constants = new Constants();
    public int userPosition=constants.USER_POSITION,adapterPosition;
    private String mName = "";
    boolean doubleBackToExitPressedOnce = false;
    private List<User> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTvId = findViewById(R.id.home_tv_id);
        mTvName = findViewById(R.id.home_tv_username);

        checkInternet();

        getSupportActionBar().hide();

        mProgressBar= findViewById(R.id.home_progress_bar);

            mBtnShowPosts = findViewById(R.id.home_btn_showposts);
            mBtnShowPosts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userPosition == constants.USER_POSITION)
                        Toast.makeText(HomeActivity.this,getString(R.string.home_no_user_selected),Toast.LENGTH_SHORT).show();
                    else {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(HomeActivity.this, ShowDetailsActivity.class);
                        bundle.putString(constants.KEY_ID,Integer.toString(userPosition));
                        bundle.putString(constants.KEY_NAME,mName);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });

            mBtnMoreDetails = findViewById(R.id.home_btn_more_details);
            mBtnMoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userPosition == constants.USER_POSITION) {
                        Toast.makeText(HomeActivity.this, getString(R.string.home_no_user_selected), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialog);
                        builder.setTitle(getString(R.string.dialog_title));
                        builder.setMessage(getString(R.string.dialog_msg_id) + mUserList.get(adapterPosition).getId() + "\n" +
                                getString(R.string.dialog_msg_name) + mUserList.get(adapterPosition).getName() + "\n" +
                                getString(R.string.dialog_msg_username) + mUserList.get(adapterPosition).getUserName() + "\n" +
                                getString(R.string.dialog_msg_phone) + mUserList.get(adapterPosition).getPhone() + "\n" +
                                getString(R.string.dialog_msg_email) + mUserList.get(adapterPosition).getEmail() + "\n" +
                                getString(R.string.dialog_msg_website) + mUserList.get(adapterPosition).getWebsite() + "\n" +
                                getString(R.string.dialog_msg_street) + mUserList.get(adapterPosition).getStreet() + "\n" +
                                getString(R.string.dialog_msg_suite) + mUserList.get(adapterPosition).getSuite() + "\n" +
                                getString(R.string.dialog_msg_city) + mUserList.get(adapterPosition).getCity() + "\n" +
                                getString(R.string.dialog_msg_zipcode) + mUserList.get(adapterPosition).getZipcode() + "\n" +
                                getString(R.string.dialog_msg_company_name) + mUserList.get(adapterPosition).getCompanyName() + "\n" +
                                getString(R.string.dialog_msg_catch_phrase) + mUserList.get(adapterPosition).getCompanyCatchPhrase() + "\n" +
                                getString(R.string.dialog_msg_company_bs) + mUserList.get(adapterPosition).getCompanyBs());
                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
                        dialog.show();
                    }
                }
            });
    }

//for checking the connectivity of internet and displaying dialogues if offline
    public void checkInternet(){
        if(!isOnline(HomeActivity.this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,R.style.AlertDialog);
            builder.setTitle(getString(R.string.no_internet_title));
            builder.setMessage(getString(R.string.no_internet_message));
            builder.setPositiveButton(getString(R.string.no_internet_btn),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkInternet();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                }
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
            dialog.show();
        }
        else{
            Retrofit retrofit = apiClient.getRetrofit();

            ApisInterface apis = retrofit.create(ApisInterface.class);

            Call<List<User>> call = apis.getAllData();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    generateDataList(response.body());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

//for checking if internet is on or not
    public boolean isOnline(Context context) {
        boolean result = false;
        if (context != null) {
            final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null) {
                    result = networkInfo.isConnected();
                }
            }
        }
        return result;
    }

//function for exiting the app when double back pressed
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.toast_msg), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, constants.BACK_PRESS_TIME);
    }

    /**
     *
     * @param userList contains the list of users fetched from api
     */
    public void generateDataList(List<User> userList){
        mRecyclerView = findViewById(R.id.home_recyler_view);
        mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FullListAdapter(HomeActivity.this,userList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     *
     * @param userList contains the id and name of the users
     * @param position is the adapter position sent from recycler view
     */
    @Override
    public void onItemClicked(final List<User> userList,final int position) {
        adapterPosition = position;
        userPosition = userList.get(position).getId();
        mName = userList.get(position).getName();
        mTvId.setText(""+userList.get(position).getId());
        mTvName.setText(userList.get(position).getName());

        mUserList = userList;
    }
}
