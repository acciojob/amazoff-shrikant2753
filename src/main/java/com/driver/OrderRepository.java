package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    private HashMap<String, Order>orderMap;
    private HashMap<String, DeliveryPartner>deliveryPartnerMap;
    private HashMap<String, String>orderDeliveryPartnerMap;
    private HashMap<String, ArrayList<String>> deliveryPartnerOrderListMap;


    public OrderRepository(){
        deliveryPartnerMap = new HashMap<>();
        orderMap = new HashMap<>();
        orderDeliveryPartnerMap = new HashMap<>();
        deliveryPartnerOrderListMap = new HashMap<>();
    }

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId){
        deliveryPartnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String deliveryPartnerId){
        orderDeliveryPartnerMap.put(orderId, deliveryPartnerId);
        ArrayList<String>orderList = deliveryPartnerOrderListMap.getOrDefault(deliveryPartnerId, new ArrayList<>());
        orderList.add(orderId);
        deliveryPartnerOrderListMap.put(deliveryPartnerId, orderList);
    }

    public Order getOrderById(String orderId){
//        if(!orderMap.containsKey(orderId))
//            return null;
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
//        if(!deliveryPartnerMap.containsKey(partnerId))
//            return null;
        return deliveryPartnerMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        if(!deliveryPartnerOrderListMap.containsKey(partnerId))
            return 0;
        return deliveryPartnerOrderListMap.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        if(!deliveryPartnerOrderListMap.containsKey(partnerId))
            return null;
        return deliveryPartnerOrderListMap.get(partnerId);
    }

    public List<String> getAllOrder(){
        List<String>order = new ArrayList<>();
        for(String orderId : orderMap.keySet())
            order.add(orderId);
        return order;
    }

    public int getCountOfUnassignedOrders(){
        return orderMap.size() - orderDeliveryPartnerMap.size();
    }

    public void deletePartnerById(String partnerId){
        if(deliveryPartnerMap.containsKey(partnerId)){
            deliveryPartnerMap.remove(partnerId);
            ArrayList<String> orders = deliveryPartnerOrderListMap.get(partnerId);
            for(String id:orders){
                orderDeliveryPartnerMap.remove(id);
            }
            deliveryPartnerOrderListMap.remove(partnerId);
        }
    }


    public void deleteOrderById(String orderId){
        if(orderMap.containsKey(orderId)){
            orderMap.remove(orderId);
            String deliveryPartnerId = orderDeliveryPartnerMap.get(orderId);
            orderDeliveryPartnerMap.remove(orderId);
            if(deliveryPartnerMap.containsKey(deliveryPartnerId)){
                ArrayList<String> orders = deliveryPartnerOrderListMap.get(deliveryPartnerId);
                for(String id : orders){
                    if(id.equals(orderId))
                        orders.remove(id);
                }
            }
        }
    }

}
