package com.vdeliverz_delivery.order_items_list.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopDetails {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_street")
    @Expose
    private String shopStreet;
    @SerializedName("shop_image")
    @Expose
    private String shopImage;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopStreet() {
        return shopStreet;
    }

    public void setShopStreet(String shopStreet) {
        this.shopStreet = shopStreet;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

}
