package com.caniss.ifarmm;

public class Price {
    private String mProduct;
    private String mPrice;
    private String mImageUrl;

    public Price() {
    }

    public Price(String mProduct, String mPrice, String mImageUrl) {
        this.mProduct = mProduct;
        this.mPrice = mPrice;
        this.mImageUrl = mImageUrl;
    }

    public String getmProduct() {
        return mProduct;
    }

    public void setmProduct(String mProduct) {
        this.mProduct = mProduct;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
