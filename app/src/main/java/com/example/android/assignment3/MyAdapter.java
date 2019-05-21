package com.example.android.assignment3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ArrayList<Student> mStudent;
    private RecyclerViewClickListener mItemListener;

    MyAdapter(ArrayList<Student> student,final RecyclerViewClickListener mItemListener){
        mStudent=student;
        this.mItemListener= mItemListener;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName,tvRoll,tvClass;
        public View mView;

        public MyViewHolder(View view){
            super(view);
            final StudentListActivity dialog = new StudentListActivity();
            mView=view;
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvRoll = (TextView) view.findViewById(R.id.tv_roll);
            tvClass = (TextView) view.findViewById(R.id.tv_class);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onItemClicked(v,getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = (View) inflater.inflate(R.layout.student_list_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Student student = mStudent.get(i);
        myViewHolder.tvName.setText(student.getmName());
        myViewHolder.tvRoll.setText(""+student.getmRoll());
        myViewHolder.tvClass.setText(student.getmClass());
    }

    @Override
    public int getItemCount() {
        return mStudent.size();
    }

    public interface RecyclerViewClickListener{
         void onItemClicked(View view,int position);
    }

}
