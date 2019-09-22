package com.example.marketim.api;

class NetworkingUtils {
    private static ApiService apiService;

    static ApiService getApiInstance() {
        if (apiService == null)
            apiService = RetrofitAdapter.getInstance().create(ApiService.class);

        return apiService;
    }
}
