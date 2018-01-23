package com.gbm.mgb.domain.order;

import com.gbm.mgb.dto.Coupon;
import com.gbm.mgb.dto.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 订单持久层 on 2018/1/3.
 */
@Repository
public interface OrderDao {
    /**
     * 根据劵码查询是否正确
     * @param coupon
     * @return
     */
    Coupon findCouponByTicket(Coupon coupon);

    /**
     * 更改核销状态
     * @param coupon
     */
    void updateCouponByTicket(Coupon coupon);

    /**
     * 判断用户是否核销 或者 支付
     * @param
     * @return
     */
    Coupon findCouponByIphone(@Param("iPhone") String iPhone);

    /**
     * 添加订单信息
     * @param
     */
    void saveOrdersIphone(Orders orders);
    /**
     * 获取用户的收货地址
     * @param
     * @return
     */
    Orders findOrdersByIphone(@Param("phone")String phone);

    /**
     * 更新用户收货地址
     * @param orders
     */
    void updateOrdersByAddress(Orders orders);

    /**
     * 保存订单信息
     * @param orders
     */
    void updateOrdersAndToPlace(Orders orders);

    /**
     * 根据订单号,手机号.查询订单信息
     * @param orders
     * @return
     */
    Orders findOrdersToPlayInFo(Orders orders);

    /**
     * 更新订单流水号
     * @param orders
     */
    void updateOrdersByPlayNo(Orders orders);

    /**
     * 更新劵码流水号
     * @param orders
     */
    void updateCouponByPlayNo(Orders orders);

    /**
     * 更改状态支付成功
     * @param order
     */
    void updatePayOrderStateToSuccess(Orders order);

    /**
     * 更改劵码表状态
     * @param order
     */
    void updateCouponStateToSuccess(Orders order);
}
