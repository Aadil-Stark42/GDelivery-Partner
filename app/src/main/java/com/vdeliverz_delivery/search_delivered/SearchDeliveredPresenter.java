package com.vdeliverz_delivery.search_delivered;


import com.vdeliverz_delivery.search_delivered.model.DeliveredSearchResponse;

public class SearchDeliveredPresenter implements SearchDeliveredIntract.OnSearchListener, SearchDeliveredContract.GetsearchIntractor.OnFinishedListener{


    SearchDeliveredContract searchDeliveredContract;
    SearchDeliveredIntract searchDeliveredIntract;

    public SearchDeliveredPresenter(SearchDeliveredContract searchDeliveredContract, SearchDeliveredIntract searchDeliveredIntract){
        this.searchDeliveredContract = searchDeliveredContract;
        this.searchDeliveredIntract = searchDeliveredIntract;
    }

    public void validateDetails(String search_keyword){
        searchDeliveredIntract.directValidation(search_keyword,this);

    }

    @Override
    public void onFinished(DeliveredSearchResponse searchResultResponse) {
        searchDeliveredContract.search_delivered_success(searchResultResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        searchDeliveredContract.search_delivered_failure(error_msg);
    }

    @Override
    public void do_logout() {
        searchDeliveredContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(searchDeliveredContract != null) {
            searchDeliveredIntract.search_delivered_APICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        searchDeliveredContract.search_delivered_failure(msg);
    }


}
