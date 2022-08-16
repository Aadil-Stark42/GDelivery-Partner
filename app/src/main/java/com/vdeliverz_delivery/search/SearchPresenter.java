package com.vdeliverz_delivery.search;


import com.vdeliverz_delivery.search.model.SearchResultResponse;

public class SearchPresenter implements SearchIntract.OnSearchListener, SearchContract.GetsearchIntractor.OnFinishedListener{


    SearchContract pickedContract;
    SearchIntract pickedIntract;

    public SearchPresenter(SearchContract pickedContract, SearchIntract pickedIntract){
        this.pickedContract = pickedContract;
        this.pickedIntract = pickedIntract;
    }

    public void validateDetails(String search_keyword){
        pickedIntract.directValidation(search_keyword,this);

    }

    @Override
    public void onFinished(SearchResultResponse searchResultResponse) {
        pickedContract.search_success(searchResultResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        pickedContract.search_failure(error_msg);
    }

    @Override
    public void do_logout() {
        pickedContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(pickedContract != null) {
            pickedIntract.searchAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        pickedContract.search_failure(msg);
    }


}
