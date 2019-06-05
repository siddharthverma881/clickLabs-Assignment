package com.example.assignment5.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment5.R;
import com.example.assignment5.models.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private ArrayList<Student> mStudentArrayList;
    private RecyclerViewClickListener mClickListener;

    public StudentAdapter(ArrayList<Student> studentArrayList, RecyclerViewClickListener clickListener){
        mStudentArrayList=studentArrayList;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.view_student,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder myViewHolder, int i) {
        Student student = mStudentArrayList.get(i);
        myViewHolder.tvName.setText(student.getmName());
        myViewHolder.tvRoll.setText(student.getmRoll());
        myViewHolder.tvClass.setText(student.getmClassName());
    }

    @Override
    public int getItemCount() {
        return mStudentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvRoll,tvClass;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRoll = itemView.findViewById(R.id.tv_roll);
            tvClass = itemView.findViewById(R.id.tv_class);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(v,getAdapterPosition());
                }
            });
        }
    }
    public interface RecyclerViewClickListener{
        void onItemClicked(View view,int position);
    }
}
