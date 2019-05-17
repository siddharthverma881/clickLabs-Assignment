package com.example.android.assignment3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import Utilities.util;

public class StudentListActivity extends AppCompatActivity implements MyAdapter.RecyclerViewClickListener {

    /**
     * @param mRecyclerView is the object of RecyclerView
     * @param mAdapter is the object of class MyAdapter
     * @param layoutManager for setting the layout of the activity
     * @param mTvNoData is the text view which becomes visible when there is no data in the recycler view
     * @param btnAddCustomer denotes the button Add Button
     * @param mStudentList is the array list to store the name,rollno and class of student as an object
     * @param mRollList is the arraylist to store all the roll numbers that are added in the recycler view
     * @param tempname,stringtemproll,tempclass denotes the values of name,rollno and class filled in the edit text views
     * @param mImage is of type boolean to check if its value is false open grid view else open list view
     * @param mCode is the code value which is passed in startActivityForResult
     * @param mViewPosition stores the position where the adapter is present
     */

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    private TextView mTvNoData;
    protected Button btnAddCustomer;
    private ArrayList<Student> mStudentList=new ArrayList<>();
    private ArrayList<String> mRollList = new ArrayList<>();
    protected String tempname,stringtemproll,tempclass;
    private boolean mImage=false;
    private int mCode,mViewPosition;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //showing grid view/list view
            case R.id.menu_image :
                mImage = !mImage;
                mRecyclerView.setLayoutManager(mImage ? new GridLayoutManager(this,2) : new LinearLayoutManager(this));
                mAdapter.notifyDataSetChanged();
                break;
             //show the list sorted alphabetically
            case R.id.menu_sortByName :
                Collections.sort(mStudentList,util.getCompByName());
                mAdapter = new MyAdapter(mStudentList,this);
                mRecyclerView.setAdapter(mAdapter);
                break;
            //show teh list sorted according to roll no
            case R.id.menu_sortById :
                Collections.sort(mStudentList,util.getCompById());
                mAdapter = new MyAdapter(mStudentList,this);
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mTvNoData = (TextView) findViewById(R.id.tv_nodata);
        mTvNoData.setVisibility(View.INVISIBLE);

        if(mStudentList.isEmpty()){
            mCode=0;
            insertIntoAdapter(mStudentList,mCode);
        }

        btnAddCustomer = (Button) findViewById(R.id.student_list_btn_add_student);
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //calling the intent for adding a new student
                openAddStudentIntent();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestmCode, int resultmCode, Intent resultIntent) {
    //requestmCode is 1 if user enters a new student
        if(requestmCode == 1){
            if(resultmCode == RESULT_OK){
                tempname = resultIntent.getStringExtra(getString(R.string.intent_key_name));
                stringtemproll = resultIntent.getStringExtra(getString(R.string.intent_key_roll));
                if(mRollList.contains(stringtemproll)) {
                    Toast.makeText(this,getString(R.string.et_roll_exists),Toast.LENGTH_SHORT).show();
                    openAddStudentIntent();
                }
                else {
                //add the roll number to an arraylist consisting of only roll numbers
                    mRollList.add(stringtemproll);
                    tempclass = resultIntent.getStringExtra(getString(R.string.intent_key_class));
                    mStudentList.add(new Student(tempname, stringtemproll, tempclass));
                //inserting the updated list in the adapter and recycler view
                    insertIntoAdapter(mStudentList, resultmCode);
                }
            }
        }
    //requestmCode is 2 if user edits the details of an existing student
        if(requestmCode==2){
            mStudentList.remove(mViewPosition);
            mAdapter.notifyItemRemoved(mViewPosition);
            tempname = resultIntent.getStringExtra(getString(R.string.intent_key_name));
            stringtemproll = resultIntent.getStringExtra(getString(R.string.intent_key_roll));
            tempclass = resultIntent.getStringExtra(getString(R.string.intent_key_class));
            mStudentList.add(mViewPosition,new Student(tempname,stringtemproll,tempclass));
            insertIntoAdapter(mStudentList,resultmCode);
        }
    }
    public void insertIntoAdapter(ArrayList<Student> student,int mCode){
        if(mCode!=0) {
            mAdapter = new MyAdapter(student,this);
            mRecyclerView.setAdapter(mAdapter);
            mTvNoData.setVisibility(View.INVISIBLE);
        }
        else{
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }


    @Override

    /**
     * @param which denotes which item number is clicked in the dialog box
     *When clicked on a particular view in the recycler view,this function will open a dialog box corresponding to which further actions take place
     */
    public void onItemClicked(View view,final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final Bundle bundle = new Bundle();
        builder.setTitle(getString(R.string.builder_title));
        String[] options = {getString(R.string.dialog_btn_view),getString(R.string.dialog_btn_edit),getString(R.string.dialog_btn_delete)};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        bundle.putSerializable(getString(R.string.builder_key), getString(R.string.intent_key_view));
                        bundle.putSerializable(getString(R.string.dialog_view_name),tempname);
                        bundle.putSerializable(getString(R.string.dialog_view_roll),stringtemproll);
                        bundle.putSerializable(getString(R.string.dialog_view_class),tempclass);
                        Intent intent = new Intent(StudentListActivity.this,AddStudentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        mViewPosition=position;
                        openEditStudentIntent();
                        break;
                    case 2:
                        mStudentList.remove(position);
                        mRollList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        if(mStudentList.isEmpty()){
                            mCode=0;
                            insertIntoAdapter(mStudentList,mCode);
                        }
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void openAddStudentIntent(){
        mCode=1;
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.builder_key),getString(R.string.intent_key_add));
        Intent intent = new Intent(StudentListActivity.this,AddStudentActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,mCode);
    }
    public void openEditStudentIntent(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.builder_key), getString(R.string.intent_key_edit));
        bundle.putSerializable(getString(R.string.dialog_edit_name),tempname);
        bundle.putSerializable(getString(R.string.dialog_edit_roll),stringtemproll);
        bundle.putSerializable(getString(R.string.dialog_edit_class),tempclass);
        Intent intent = new Intent(StudentListActivity.this,AddStudentActivity.class);
        intent.putExtras(bundle);
        mCode=2;
        startActivityForResult(intent,mCode);
    }
}