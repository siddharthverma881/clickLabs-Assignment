package com.example.assignment6.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.models.PostId;

import java.util.List;

public class PostIdAdapter extends RecyclerView.Adapter<PostIdAdapter.MyViewHolder> {

    private Context mContext;
    private List<PostId> mCommentsList;

    public PostIdAdapter(Context context, List<PostId> comments){
        mContext = context;
        mCommentsList = comments;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.post_id_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mTvUserId.setText(mContext.getString(R.string.post_tv_user_id) + mCommentsList.get(i).getUserId());
        myViewHolder.mTvId.setText(mContext.getString(R.string.post_tv_id) + mCommentsList.get(i).getId());
        myViewHolder.mTvTitle.setText(mContext.getString(R.string.post_tv_title) + mCommentsList.get(i).getTitle());
        myViewHolder.mTvBody.setText(mContext.getString(R.string.post_tv_body) + mCommentsList.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvUserId,mTvId,mTvTitle,mTvBody;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvUserId = itemView.findViewById(R.id.pidl_tv_user);
            mTvId = itemView.findViewById(R.id.pidl_tv_id);
            mTvTitle = itemView.findViewById(R.id.pidl_tv_title);
            mTvBody = itemView.findViewById(R.id.pidl_tv_body);
        }
    }
}
