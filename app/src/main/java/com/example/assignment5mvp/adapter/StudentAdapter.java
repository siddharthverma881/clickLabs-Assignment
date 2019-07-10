package com.example.assignment5mvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.model.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private ArrayList<Student> mStudentList = new ArrayList<>();
    private StudentAdapterInterface mListener;

    public StudentAdapter(ArrayList<Student> studentArrayList, StudentAdapterInterface listener){
        mStudentList = studentArrayList;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.view_student,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTvName.setText(mStudentList.get(i).getName());
        viewHolder.mTvRoll.setText(mStudentList.get(i).getRoll());
        viewHolder.mTvClass.setText(mStudentList.get(i).getClassName());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView mTvName,mTvRoll,mTvClass;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvRoll = itemView.findViewById(R.id.tv_roll);
            mTvClass = itemView.findViewById(R.id.tv_class);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface StudentAdapterInterface{
        public void onItemClicked(int position);
    }
}
