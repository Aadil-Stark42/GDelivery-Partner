package com.vdeliverz_delivery.retrofit;

import com.vdeliverz_delivery.delivered.model.DeliveredListResponse;
import com.vdeliverz_delivery.delivery_details.model.DeliveryDetailResponse;
import com.vdeliverz_delivery.home.model.DashboardResponse;
import com.vdeliverz_delivery.login.model.LoginResponse;
import com.vdeliverz_delivery.notification.model.NotificationListResponse;
import com.vdeliverz_delivery.ongoing_orders.model.OngoingResponse;
import com.vdeliverz_delivery.order_items_list.model.OrderDetailsResponse;
import com.vdeliverz_delivery.profile.get_profile.model.GetProfileResponse;
import com.vdeliverz_delivery.search.model.SearchResultResponse;
import com.vdeliverz_delivery.search_delivered.model.DeliveredSearchResponse;
import com.vdeliverz_delivery.utils.GeneralResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VdeliverzApiInterface {


    //login
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delivery-login")
    Call<LoginResponse> getloginApi(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("fcm_token") String fcm_token);

    //orders list
    @Headers({"Accept: application/json"})
    @GET("delivery-dashboard")
    Call<DashboardResponse>getOrdersList();

    //Ongoing list
    @Headers({"Accept: application/json"})
    @GET("ongoing-order")
    Call<OngoingResponse>getOngoingList();

    //Accept order
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("accept-order")
    Call<GeneralResponse>orderacceptApicall(@Field("order_id") String order_id);


    //Mark as picked
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("pick-order")
    Call<GeneralResponse>pickApicall(@Field("order_id") String order_id);

    //Mark as delivered
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("deliver-order")
    Call<GeneralResponse>deliveredApicall(@Field("order_id") String order_id);

    //Mark as Canceled
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("cancel-delivery")
    Call<GeneralResponse>cancelApicall(@Field("order_id") String order_id);

    //Mark as reject
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("reject-order")
    Call<GeneralResponse>rejectApicall(@Field("order_id") String order_id);

    //Collect cash
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delivery-cash")
    Call<GeneralResponse>collectcashApicall(@Field("order_id") String order_id,
                                            @Field("cash_note") String cash_note);

    //delivery-detail
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delivery-detail")
    Call<DeliveryDetailResponse>deliverydetailApicall(@Field("order_id") String order_id);


    //delivery-search
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delivery-search")
    Call<SearchResultResponse>searchApicall(@Field("search") String search);

    //delivered orders
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("delivered-order-search")
    Call<DeliveredSearchResponse>searchdeliveredApicall(@Field("search") String search);


    //collect delivered order list
    @Headers({"Accept: application/json"})
    @GET("delivered-orders")
    Call<DeliveredListResponse>getdeliveredlist();


    //collect cancelled order list
    @Headers({"Accept: application/json"})
    @GET("canceled-orders")
    Call<DeliveredListResponse>getcancelledorderlist();

    //Get user profile
    @Headers({"Accept: application/json"})
    @GET("user")
    Call<GetProfileResponse> getuser_details();


    //update profile
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("user-update")
    Call<GeneralResponse> updateuser_profileApi(@Field("mobile") String mobile,
                                                @Field("email") String email,
                                                @Field("name") String name);

    //Get Notification list
    @Headers({"Accept: application/json"})
    @GET("delivery-notifications")
    Call<NotificationListResponse> getnotificationlist();


    //logout
    @Headers({"Accept: application/json"})
    @GET("logout")
    Call<GeneralResponse>do_logout_apicall();

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("order-details")
    Call<OrderDetailsResponse>get_order_details(@Field("order_id") String order_id);


}
