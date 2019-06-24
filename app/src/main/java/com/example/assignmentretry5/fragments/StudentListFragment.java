package com.example.assignmentretry5.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignmentretry5.R;
import com.example.assignmentretry5.activities.ViewStudentActivity;
import com.example.assignmentretry5.adapters.StudentAdapter;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.services.AsyncTaskHelper;
import com.example.assignmentretry5.utils.Constants;

import java.util.ArrayList;

public class StudentListFragment extends Fragment implements StudentAdapter.RecyclerViewClickListener {

    private StudentListFragmentListener mListener;
    private TextView noDataFound;
    private int mAdapterPosition;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Student> mStudentArrayList = new ArrayList<>();
    private Button mButton;
    Constants constants = new Constants();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new StudentAdapter(mStudentArrayList,this);
        mRecyclerView.setAdapter(mAdapter);

        mListener.studentListFragment(new Student("","",""),constants.ASYNC_KEY_GETALLDATA);

        mButton = view.findViewById(R.id.studentList_btn_add);
        noDataFound = view.findViewById(R.id.fragment_tv_nodatafound);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.studentListFragment(new Student("","",""),constants.KEY_ADD);
            }
        });

        return view;
    }

    public void getAllData(ArrayList<Student> studentArrayList){
        mStudentArrayList = studentArrayList;
        mAdapter = new StudentAdapter(mStudentArrayList,this);
        mRecyclerView.setAdapter(mAdapter);
        checkVisibility();
    }

    public void instantiateListener(StudentListFragmentListener mListener){
        this.mListener=mListener;
    }

    @Override
    public void onItemClicked(final View view, final int position) {
        final Student student = new Student(mStudentArrayList.get(position).getName(),
                                            mStudentArrayList.get(position).getRoll(),
                                            mStudentArrayList.get(position).getClassName());
        mAdapterPosition = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle( getString(R.string.dialog_title) );
        String[] option = { getString(R.string.dialog_message_view),getString(R.string.dialog_message_edit),getString(R.string.dialog_message_delete)};
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(getContext(), ViewStudentActivity.class);
                        intent.putExtra(constants.VIEW_INTENT_KEY,student);
                        startActivity(intent);
                        break;
                    case 1:
                        mListener.studentListFragment(student, constants.KEY_UPDATE);
                        break;
                    case 2:
                        mListener.studentListFragment(student,constants.KEY_DELETE);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//  adding the student data to the array list
    public void addDataToArrayList(Student student){
        mStudentArrayList.add(new Student(student.getName(),student.getRoll(),student.getClassName()));
        mAdapter.notifyDataSetChanged();
        checkVisibility();
    }

//  editing data of the array list
    public void editDataOfArrayList(Student student){
        mStudentArrayList.remove(mAdapterPosition);
        mAdapter.notifyItemRemoved(mAdapterPosition);
        mStudentArrayList.add(mAdapterPosition,new Student(student.getName(),student.getRoll(),student.getClassName()));
        mAdapter.notifyDataSetChanged();
    }

//  deleting data from the array list
    public void deleteDataFromArrayList(){
        mStudentArrayList.remove(mAdapterPosition);
        mAdapter.notifyItemRemoved(mAdapterPosition);
        checkVisibility();
    }

// for displaying/hiding recycler view/text view with text no data found
    private void checkVisibility(){
        if(mStudentArrayList.size() > constants.MIN_ITEMS_DELETED){
            mRecyclerView.setVisibility(View.VISIBLE);
            noDataFound.setVisibility(View.INVISIBLE);
        }
        else{
            noDataFound.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    public interface StudentListFragmentListener {
        void studentListFragment(Student student,String resultOfButton);
    }
}
