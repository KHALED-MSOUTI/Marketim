package com.example.marketim.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.marketim.R;
import com.example.marketim.adapter.rvAdapter;
import com.example.marketim.contract.mainActivityContract;
import com.example.marketim.model.jsonList;
import com.example.marketim.presenter.myPresenter;
import com.example.marketim.utils.Const;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements mainActivityContract.View, rvAdapter.ListItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.logoutChip)
    Chip logoutChip;
    private SharedPreferences preferences;
    myPresenter mPresenter;
    rvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new myPresenter(this);
        mPresenter.start();
        logoutChip.setOnClickListener(v -> new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.alert))
                .setContentText(getString(R.string.logoutAlertText))
                .setConfirmText("Evet")
                .setConfirmClickListener(sweetAlertDialog -> {
                    preferences.edit().clear().apply();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .show());

    }

    @Override
    public void init() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mPresenter.loadData();
        preferences = getSharedPreferences(Const.PREFERENCES_NAME, MODE_PRIVATE);
        Skeleton.bind(recyclerView)
                .adapter(adapter)
                .load(R.layout.list_row_item)
                .show();
    }



    @Override
    public void loadDataInList(ArrayList<jsonList> items) {
        adapter = new rvAdapter(this, items, this);
        recyclerView.setAdapter(adapter);

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