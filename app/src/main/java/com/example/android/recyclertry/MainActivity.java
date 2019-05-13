package com.example.android.recyclertry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * @param recyclerView for declaring the recycler view
     * @param mAdapter for declaring the adapter of view recycle view
     * @param layoutManager for defining a layout for the recycle view
     */

    private RecyclerView recyclerView ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


     //adding the details of user to arralist of type User
        ArrayList<User> myDataSet =new ArrayList();
        myDataSet.add(new User("sid","21","1"));
        myDataSet.add(new User("div","20","1"));
        myDataSet.add(new User("khushi","19","1"));
        myDataSet.add(new User("bhim","18","1"));
        myDataSet.add(new User("sahil","17","1"));
        myDataSet.add(new User("attri","16","1"));
        myDataSet.add(new User("grover","15","1"));
        myDataSet.add(new User("kriti","14","1"));
        myDataSet.add(new User("avni","13","1"));
        myDataSet.add(new User("kk","12","1"));

        //setting the arraylist to the object of adapter
        mAdapter = new MyAdapter(myDataSet);
        //setting the object of adapter to the recycle view
        recyclerView.setAdapter(mAdapter);
    }
}
