package com.example.android.recyclertry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<User> mDataset;   

    /**
     * @param myDataset for getting the arraylist passed from the main activity
     */
    public MyAdapter(ArrayList<User> myDataset) {
        mDataset = myDataset;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameText;
        public TextView ageText;
        public TextView phnoText;
        public Button likeBtn;
        public RatingBar ratingBar;
        public TextView likeCount;
        public ImageView deleteImage;
        public ImageView picassoImage;
        public MyViewHolder(View v) {
            super(v);
            nameText = (TextView) v.findViewById(R.id.list_layout_name);
            ageText = (TextView) v.findViewById(R.id.list_layout_age);
            phnoText = (TextView) v.findViewById(R.id.list_layout_phno);
            ratingBar =(RatingBar) v.findViewById(R.id.list_layout_rating_bar);
            likeBtn = (Button) v.findViewById(R.id.list_layout_btn);
            likeCount = (TextView) v.findViewById(R.id.list_layout_like_tv);
            deleteImage = (ImageView) v.findViewById(R.id.list_layout_cross_btn);
            picassoImage = (ImageView) v.findViewById(R.id.list_layout_photo_iv);

        //event listener for setting the value of stars when user clicks on the stars
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        User u = mDataset.get(getAdapterPosition());
                        u.setStars(rating);
                    notifyItemChanged(getAdapterPosition());
                    }
                }
            });

        //calling the event listener when the like button is pressed
            likeBtn.setOnClickListener(this);
        //calling the event listener when the cross image is pressed to delete the particular view
            deleteImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.list_layout_btn :
                    User u = mDataset.get(getAdapterPosition());
                    u.setLikes(u.getLikes()+1);
                    notifyItemChanged(getAdapterPosition());
                    break;
                case R.id.list_layout_cross_btn :
                //u is the object of class user
                    u = mDataset.get(getAdapterPosition());
                    mDataset.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    //creating a view which is declared in the list_layout activity
        View view = (View) inflater.inflate(R.layout.list_layout,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,int position) {
        /**
         * @param currentData is the object of user to get a particular view and its respective details from the arraylist
          */
        User currentData = mDataset.get(position);
        holder.nameText.setText(currentData.getName());
        holder.ageText.setText(currentData.getAge());
        holder.phnoText.setText(currentData.getPhno());
    //for getting the image online
        Picasso.with(holder.itemView.getContext()).load("http://i.imgur.com/DvpvklR.png").into(holder.picassoImage);
        holder.likeCount.setText(""+currentData.getLikes());
        holder.ratingBar.setRating(currentData.getStars());
    }

    @Override
    public int getItemCount() {
    //returns the size of the arraylist
        return mDataset.size();
    }

}
