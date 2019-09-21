package com.example.marketim.api;

public class NetworkingUtils {
    private static ApiService apiService;

    public static ApiService getApiInstance() {
        if (apiService == null)
            apiService = RetrofitAdapter.getInstance().create(ApiService.class);

        return apiService;
    }
}
