package com.example.marketim.presenter;

import com.example.marketim.api.CallTask;
import com.example.marketim.api.Callback;
import com.example.marketim.contract.mainActivityContract;
import com.example.marketim.model.jsonList;

import java.util.ArrayList;

public class myPresenter implements mainActivityContract.Presenter {
    private mainActivityContract.View mView;

    public myPresenter(mainActivityContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadData() {
        CallTask.getUsers(new Callback<ArrayList<jsonList>>() {
            @Override
            public void returnResult(ArrayList<jsonList> items) {
                mView.loadDataInList(items);
            }

            @Override
            public void returnError(String message) {
                mView.showError(message);
            }
        });
    }

    @Override
    public void start() {
        mView.init();
    }


}
