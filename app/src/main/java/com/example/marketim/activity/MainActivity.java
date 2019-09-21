package com.example.marketim.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.marketim.R;
import com.example.marketim.adapter.rvAdapter;
import com.example.marketim.contract.mainActivityContract;
import com.example.marketim.model.jsonList;
import com.example.marketim.presenter.myPresenter;
import com.example.marketim.utils.Const;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements mainActivityContract.View,rvAdapter.ListItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewl;
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

    }

    @Override
    public void init() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewl.setLayoutManager(manager);
        mPresenter.loadData();
        preferences = getSharedPreferences(Const.PREFERENCES_NAME,MODE_PRIVATE);


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

    public void Logout(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert))
                .setMessage(getString(R.string.alertText))
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Evet", (dialog, which) -> {
                    preferences.edit().clear().apply();
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Hayır", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}