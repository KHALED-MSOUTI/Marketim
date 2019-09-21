package com.example.marketim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.marketim.R;
import com.example.marketim.adapter.rvAdapter;
import com.example.marketim.contract.mainActivityContract;
import com.example.marketim.model.jsonList;
import com.example.marketim.presenter.myPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements mainActivityContract.View,rvAdapter.ListItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewl;

    myPresenter mPresenter;
    rvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new myPresenter(this);
        mPresenter.start();

    }

    @Override
    public void init() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewl.setLayoutManager(manager);
        mPresenter.loadData();

    }

    @Override
    public void loadDataInList(ArrayList<jsonList> items) {
        adapter = new rvAdapter(this, items, this);
        recyclerViewl.setAdapter(adapter);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        adapter.extend(clickedItemIndex);
        adapter.notifyDataSetChanged();
    }
}