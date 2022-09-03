package com.keer.springbootmall.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreatedOrderRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;

    public  List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
