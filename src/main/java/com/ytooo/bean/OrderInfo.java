package com.ytooo.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderInfo {
    private String orderId;
    private Integer itemCode;
    private String itemName;
    private String categoryName;
    private Integer price;
    private Double weight;
    private Integer num;
    private List<String> logistics = new ArrayList<>();
}
