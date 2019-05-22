package com.example.android.assignment4.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.assignment4.R;
import com.example.android.assignment4.Utilities.Constants;
import com.example.android.assignment4.activities.StudentListActivity;
import com.example.android.assignment4.activities.ViewStudentActivity;
import com.example.android.assignment4.adapters.RecyclerAdapter;
import com.example.android.assignment4.models.Student;

import java.util.ArrayList;
import java.util.Collections;


public class StudentListFragment extends Fragment implements RecyclerAdapter.RecyclerViewClickListener {

    /**
     * @param mRecyclerView is the object of RecyclerView
     * @param mAdapter is the object of class MyAdapter
     * @param mlayoutManager for setting the layout of the activity
     * @param noDataFound is the text view which becomes visible when there is no data in the recycler view
     * @param mAddStudent denotes the button Add Button
     * @param studentArrayList is the array list to store the name,rollno and class of student as an object
     * @param mRollList is the arraylist to store all the roll numbers that are added in the recycler view
     * @param mName,mRoll,mClassName denotes the values of name,rollno and class filled in the edit text views
     * @param mViewPosition stores the position where the adapter is present
     */


    private StudentListFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Student> studentArrayList=new ArrayList<>();
    private TextView noDataFound;
    private Button mAddStudent;
    private String mName,mRoll,mClassName;
    private int mViewPosition;
    private String result;
    Constants constants = new Constants();

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(studentArrayList,this);
        mRecyclerView.setAdapter(mAdapter);

        noDataFound = view.findViewById(R.id.fragment_tv_nodatafound);
        final Bundle bundle = new Bundle();

        mAddStudent=view.findViewById(R.id.studentList_btn_add);
        mAddStudent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getResult(getString(R.string.value_adding));
                bundle.putString(constants.KEY,getString(R.string.value_adding));
                mListener.studentFragment(bundle);
            }
        });

        return view;
    }

    //this is the method of interface RecyclerViewClickListener declared in recyclerAdapter
    public void onItemClicked(View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final Bundle bundle = new Bundle();
        mViewPosition=position;
        builder.setTitle(getString(R.string.builder_title));
        String[] options = {getString(R.string.dialog_btn_view),getString(R.string.dialog_btn_edit),getString(R.string.dialog_btn_delete)};
        //this is the onClick listener of dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        mName=studentArrayList.get(mViewPosition).getmName();
                        mRoll=studentArrayList.get(mViewPosition).getmRoll();
                        mClassName=studentArrayList.get(mViewPosition).getmClassName();
                        bundle.putString(constants.KEY,getString(R.string.value_viewing));
                        bundle.putString(constants.KEY_NAME,mName);
                        bundle.putString(constants.KEY_ROLL,mRoll);
                        bundle.putString(constants.KEY_CLASS,mClassName);
                        Intent intent = new Intent(getActivity(), ViewStudentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        mViewPosition=position;
                        mName=studentArrayList.get(mViewPosition).getmName();
                        mRoll=studentArrayList.get(mViewPosition).getmRoll();
                        mClassName=studentArrayList.get(mViewPosition).getmClassName();
                        bundle.putString(constants.KEY,getString(R.string.value_editing));
                        getResult(getString(R.string.value_editing));
                        bundle.putString(constants.KEY_NAME,mName);
                        bundle.putString(constants.KEY_ROLL,mRoll);
                        bundle.putString(constants.KEY_CLASS,mClassName);
                        mListener.studentFragment(bundle);
                        break;
                    case 2:
                        mViewPosition=position;
                        studentArrayList.remove(mViewPosition);
                        mAdapter.notifyItemRemoved(mViewPosition);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //for storing the key value i.e either editing or adding
    public void getResult(String result){
        this.result = result;
    }


    //to store the edited/added student into the arraylist,adapter simultaneously
    public void dataTransfer(Bundle bundle){
        mName = bundle.getString(constants.KEY_NAME);
        mRoll = bundle.getString(constants.KEY_ROLL);
        mClassName = bundle.getString(constants.KEY_CLASS);
        if(result.equals(getString(R.string.value_editing))) {
            studentArrayList.remove(mViewPosition);
            mAdapter.notifyItemRemoved(mViewPosition);
        }
        studentArrayList.add(new Student(mName,mRoll,mClassName));
        if(!studentArrayList.isEmpty()) {
            noDataFound.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void instantiateListener(StudentListFragmentListener mListener){
        this.mListener=mListener;
    }

    public interface StudentListFragmentListener {
        void studentFragment(Bundle bundle);
    }
}
