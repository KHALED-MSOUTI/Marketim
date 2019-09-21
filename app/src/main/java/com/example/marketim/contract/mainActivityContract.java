package com.example.marketim.contract;

import com.example.marketim.model.jsonList;

import java.util.ArrayList;

public interface mainActivityContract {

    interface View {
        void init();

        void showError(String message);

        void loadDataInList(ArrayList<jsonList> items);
    }

    interface Presenter {
        void start();

        void loadData();
    }
}
