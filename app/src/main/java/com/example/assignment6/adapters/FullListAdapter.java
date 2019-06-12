package com.example.assignment6.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.models.User;

import java.util.List;

public class FullListAdapter extends RecyclerView.Adapter<FullListAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> mUserList;
    private RecyclerViewInterface mListener;

    public FullListAdapter(Context context,List<User> userList,RecyclerViewInterface mListener){
        mContext = context;
        mUserList = userList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mTvId.setText("" + mUserList.get(i).getId());
        myViewHolder.mTvName.setText(mUserList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvId,mTvName;
        private View view;

        public MyViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            mTvId = itemView.findViewById(R.id.view_list_id);
            mTvName = itemView.findViewById(R.id.view_list_username);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(mUserList,getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerViewInterface{
        void onItemClicked(List<User> userList,int position);
    }
}