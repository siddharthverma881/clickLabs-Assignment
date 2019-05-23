package com.example.android.assignment4.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.assignment4.R;
import com.example.android.assignment4.models.Student;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Student> studentArrayList;
    public TextView tvName,tvRoll,tvClass;
    private RecyclerViewClickListener mClickListener;

    public RecyclerAdapter(ArrayList<Student> studentArrayList,RecyclerViewClickListener mClickListener){
        this.studentArrayList=studentArrayList;
        this.mClickListener=mClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
            tvRoll = itemView.findViewById(R.id.tv_roll);
            tvClass = itemView.findViewById(R.id.tv_class);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(v,getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.view_student,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Student student = studentArrayList.get(i);
        tvName.setText(student.getmName());
        tvRoll.setText(student.getmRoll());
        tvClass.setText(student.getmClassName());
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public interface RecyclerViewClickListener{
        void onItemClicked(View view,int position);
    }
}
