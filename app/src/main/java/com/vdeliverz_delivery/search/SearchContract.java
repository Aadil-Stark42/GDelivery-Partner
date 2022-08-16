package com.vdeliverz_delivery.search;


import com.vdeliverz_delivery.search.model.SearchResultResponse;

public interface SearchContract {

    void search_success(SearchResultResponse searchResultResponse);

    void search_failure(String msg);

    void dashboard_logout();

    interface GetsearchIntractor {
        interface OnFinishedListener {
            void onFinished(SearchResultResponse searchResultResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void searchAPICall(OnFinishedListener onFinishedListener);
    }

}
