package com.example.assignment5mvp.ui.fragment.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.adapter.StudentAdapter;
import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.util.Constants;

import java.util.ArrayList;

public class StudentListFragment extends Fragment implements StudentAdapter.StudentAdapterInterface,StudentListView {

    private StudentFragmentListener mListener;
    private RecyclerView.Adapter mAdapter;
    public ArrayList<Student> mStudentList = new ArrayList<>();
    private StudentListPresenter mPresenter;
    private int mPosition;
    Constants constants = new Constants();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new StudentListPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new StudentAdapter(mStudentList,this);
        mRecyclerView.setAdapter(mAdapter);

        Button mButton = view.findViewById(R.id.list_btn_add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.studentListFragment(new Student("","",""),constants.VALUE_BTN_ADD);
            }
        });

        return view;
    }

    public void addStudent(Student student){
        mStudentList.add(student);
        mAdapter.notifyDataSetChanged();
    }

    public void editStudent(Student student){
        mStudentList.remove(mPosition);
        mAdapter.notifyItemRemoved(mPosition);
        mStudentList.add(mPosition,student);
        mAdapter.notifyDataSetChanged();
    }

    public void instantiateListener(StudentFragmentListener mListener){
        this.mListener=mListener;
    }

    @Override
    public void onItemClicked(final int position) {
        mPosition = position;
        final Student student = mStudentList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_title);
        String option[] = {getString(R.string.dialog_message_view),getString(R.string.dialog_message_edit),getString(R.string.dialog_message_delete)};
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.viewTapped(student,which);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onViewPressed(Student student) {
        mListener.studentListFragment(student,constants.DIALOG_ACTION_VIEW);
    }

    @Override
    public void onEditPressed(Student student) {
        mListener.studentListFragment(student,constants.DIALOG_ACTION_EDIT);
    }

    @Override
    public void onDeletePressed() {
        mStudentList.remove(mPosition);
        mAdapter.notifyItemRemoved(mPosition);
    }

    public interface StudentFragmentListener {
        void studentListFragment(Student student,String action);
    }
}
