package com.halodoc.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nageshkumar
 * since  12/09/17.
 */
@Getter
@Setter
public class MerchantProductResponse {
    private String created_by;

    private String id;

    private String external_id;

    private String sale_price;

    private String updated_at;

    private String product_id;

    private String cost_price;

    private String active;

    private String updated_by;

    private String created_at;

    private String sku_id;

    private String currency;

    @JsonIgnore
    private String merchant_location_id;

    private String merchant_price;


    @Override
    public String toString() {
        return "ClassPojo [created_by = " + created_by + ", id = " + id + ", external_id = " + external_id + ", sale_price = " + sale_price + ", updated_at = " + updated_at + ", product_id = " + product_id + ", cost_price = " + cost_price + ", active = " + active + ", updated_by = " + updated_by + ", created_at = " + created_at + ", sku_id = " + sku_id + ", currency = " + currency + "]";
    }
}
