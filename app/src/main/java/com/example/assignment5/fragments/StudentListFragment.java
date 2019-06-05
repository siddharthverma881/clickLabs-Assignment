package com.example.assignment5.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.assignment5.R;
import com.example.assignment5.activities.ViewStudentActivity;
import com.example.assignment5.adapters.StudentAdapter;
import com.example.assignment5.database.DatabaseHelper;
import com.example.assignment5.models.Student;
import com.example.assignment5.services.AsyncTaskHelper;
import com.example.assignment5.services.IntentServiceHelper;
import com.example.assignment5.services.ServiceHelper;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;

public class StudentListFragment extends Fragment implements StudentAdapter.RecyclerViewClickListener {
    private StudentListFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Student> mStudentArrayList=new ArrayList<>();
    private TextView mNoDataFound;
    private Button mAddStudent;
    private int mViewPosition;
    Constants constants = new Constants();
    private String mName,mRoll,mClassName,mResultForAsync,mResultOfButton = constants.BUTTON_ACTION_ASYNCTASK;
    private String mResult=constants.VALUE_ADDING;
    DatabaseHelper myDb;

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StudentAdapter(mStudentArrayList,this);
        mRecyclerView.setAdapter(mAdapter);

        mNoDataFound = view.findViewById(R.id.fragment_tv_nodatafound);

        final Bundle bundle = new Bundle();

        myDb = new DatabaseHelper(getContext());
        Cursor res = myDb.getAllData();
        while(res.moveToNext()){
            mStudentArrayList.add(new Student(res.getString(constants.DB_FIRST_COLUMN),res.getString(constants.DB_SECOND_COLUMN),res.getString(constants.DB_THIRD_COLUMN)));
            mAdapter = new StudentAdapter(mStudentArrayList,this);
            mRecyclerView.setAdapter(mAdapter);
        }

        checkEmptyView(constants.HOME_LIST_FILLED);

        mAddStudent=view.findViewById(R.id.studentList_btn_add);
        mAddStudent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getmResult(getString(R.string.value_adding));
                bundle.putString(constants.KEY,constants.VALUE_ADDING);
                mListener.studentFragment(bundle);
            }
        });

        return view;
    }

    //this is the method of interface RecyclerViewClickListener declared in StudentAdapter
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
                        mName=mStudentArrayList.get(mViewPosition).getmName();
                        mRoll=mStudentArrayList.get(mViewPosition).getmRoll();
                        mClassName=mStudentArrayList.get(mViewPosition).getmClassName();
                        bundle.putString(constants.KEY,constants.VALUE_VIEWING);
                        bundle.putString(constants.KEY_NAME,mName);
                        bundle.putString(constants.KEY_ROLL,mRoll);
                        bundle.putString(constants.KEY_CLASS,mClassName);
                        Intent intent = new Intent(getActivity(), ViewStudentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        mViewPosition=position;
                        mName=mStudentArrayList.get(mViewPosition).getmName();
                        mRoll=mStudentArrayList.get(mViewPosition).getmRoll();
                        mClassName=mStudentArrayList.get(mViewPosition).getmClassName();
                        bundle.putString(constants.KEY,constants.VALUE_EDITING);
                        getmResult(getString(R.string.value_editing));
                        bundle.putString(constants.KEY_NAME,mName);
                        bundle.putString(constants.KEY_ROLL,mRoll);
                        bundle.putString(constants.KEY_CLASS,mClassName);
                        mListener.studentFragment(bundle);
                        break;
                    case 2:
                        mViewPosition=position;
                        mName = mStudentArrayList.get(mViewPosition).getmName();
                        mRoll = mStudentArrayList.get(mViewPosition).getmRoll();
                        mClassName = mStudentArrayList.get(mViewPosition).getmClassName();
                        mStudentArrayList.remove(mViewPosition);
                        checkResultAndDelete();
                        mAdapter.notifyItemRemoved(mViewPosition);
                        mAdapter.notifyDataSetChanged();
                        checkEmptyView(constants.HOME_LIST_FILLED);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //for storing the key value i.e either editing or adding
    public void getmResult(String mResult){
        this.mResult = mResult;
    }

    //function for deleting selected view using the service which was selected when last entry was made
    public void checkResultAndDelete(){
        if(mResultOfButton.equals(constants.BUTTON_ACTION_ASYNCTASK)) {
            mResultForAsync = constants.VALUE_DELETING;
            AsyncTaskHelper asyncDelete = new AsyncTaskHelper(getContext());
            asyncDelete.execute(mResultForAsync,mName,mRoll,mClassName);
        }
        else if(mResultOfButton.equals(constants.BUTTON_ACTION_SERVICE)){
            Intent intent = new Intent(getContext(), ServiceHelper.class);
            deleteData(intent);
        }
        else if(mResultOfButton.equals(constants.BUTTON_ACTION_INTENTSERVICE)){
            Intent intent = new Intent(getContext(), IntentServiceHelper.class);
            deleteData(intent);
        }
    }

    public void deleteData(Intent intent){
        intent.putExtra(constants.KEY,constants.VALUE_DELETING);
        intent.putExtra(constants.KEY_NAME,mName);
        intent.putExtra(constants.KEY_ROLL,mRoll);
        intent.putExtra(constants.KEY_CLASS,mClassName);
        getContext().startService(intent);
    }


    //to store the edited/added student into the arraylist,adapter simultaneously
    public void dataTransfer(Bundle bundle){
        mResultOfButton= bundle.getString(constants.BUTTON_ACTION_KEY);
        if(mResultOfButton.equals(constants.BUTTON_ACTION_ASYNCTASK)) {
            if (mResult.equals(constants.VALUE_EDITING)) {
                mName = bundle.getString(constants.KEY_NAME);
                mClassName = bundle.getString(constants.KEY_CLASS);
                mResultForAsync = constants.VALUE_EDITING;
                AsyncTaskHelper asyncEdit = new AsyncTaskHelper(getContext());
                asyncEdit.execute(mResultForAsync, mName, mRoll, mClassName);
                mStudentArrayList.remove(mViewPosition);
                mStudentArrayList.add(mViewPosition, new Student(mName, mRoll, mClassName));
                mAdapter = new StudentAdapter(mStudentArrayList, this);
                mRecyclerView.setAdapter(mAdapter);
            }
            else {
                insertIntoArrayList(bundle);
            }
        }
        else if(mResultOfButton.equals(constants.BUTTON_ACTION_SERVICE)){
            if (mResult.equals(constants.VALUE_EDITING)) {
                mName = bundle.getString(constants.KEY_NAME);
                mClassName = bundle.getString(constants.KEY_CLASS);
                Intent intent = new Intent(getContext(), ServiceHelper.class);
                putDataInIntent(intent);
                getContext().startService(intent);
                mStudentArrayList.remove(mViewPosition);
                mStudentArrayList.add(mViewPosition, new Student(mName, mRoll, mClassName));
                mAdapter = new StudentAdapter(mStudentArrayList, this);
                mRecyclerView.setAdapter(mAdapter);

            }
            else {
                insertIntoArrayList(bundle);
            }
        }
        else if(mResultOfButton.equals(constants.BUTTON_ACTION_INTENTSERVICE)){
            if (mResult.equals(constants.VALUE_EDITING)) {
                mName = bundle.getString(constants.KEY_NAME);
                mClassName = bundle.getString(constants.KEY_CLASS);
                Intent intent = new Intent(getContext(), IntentServiceHelper.class);
                putDataInIntent(intent);
                getContext().startService(intent);
                mStudentArrayList.remove(mViewPosition);
                mStudentArrayList.add(mViewPosition, new Student(mName, mRoll, mClassName));
                mAdapter = new StudentAdapter(mStudentArrayList, this);
                mRecyclerView.setAdapter(mAdapter);

            }
            else {
                insertIntoArrayList(bundle);
            }
        }
        checkEmptyView(constants.HOME_LIST_EMPTY);
    }

    public void insertIntoArrayList(Bundle bundle){
        mName = bundle.getString(constants.KEY_NAME);
        mRoll = bundle.getString(constants.KEY_ROLL);
        mClassName = bundle.getString(constants.KEY_CLASS);
        mStudentArrayList.add(new Student(mName, mRoll, mClassName));
        mAdapter.notifyDataSetChanged();
    }

    public void putDataInIntent(Intent intent){
        intent.putExtra(constants.KEY,constants.VALUE_EDITING);
        intent.putExtra(constants.KEY_NAME,mName);
        intent.putExtra(constants.KEY_ROLL,mRoll);
        intent.putExtra(constants.KEY_CLASS,mClassName);
    }

    public void checkEmptyView(int value){
        if(value == constants.HOME_LIST_FILLED) {
            if (mStudentArrayList.isEmpty())
                mNoDataFound.setVisibility(View.VISIBLE);
            else
                mRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            if (!mStudentArrayList.isEmpty()) {
                mNoDataFound.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }

    }

    public void instantiateListener(StudentListFragmentListener mListener){
        this.mListener=mListener;
    }

    public interface StudentListFragmentListener {
        void studentFragment(Bundle bundle);
    }
}
