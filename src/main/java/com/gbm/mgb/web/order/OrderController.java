package com.gbm.mgb.web.order;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.dto.Coupon;
import com.gbm.mgb.dto.Orders;
import com.gbm.mgb.helper.TokenAuthenticationService;
import com.gbm.mgb.service.rbac.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 登陆接口
 * on 2018/1/2.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 判断用户是否核销/或者支付
     * @return
     */
    @RequestMapping(value = "/checkedUser",method = RequestMethod.POST)
    @ResponseBody
    public Result checkedUser(HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        return orderService.checkedUser(iphone);
    }
    /**
     * 劵码验证
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/checked",method = RequestMethod.POST)
    @ResponseBody
    public Result cancel(Coupon coupon){
        return orderService.cancel(coupon);
    }

    /**
     * 劵码核销更改状态
     * @param coupon
     * @param request
     * @return
     */
    @RequestMapping(value = "/toCancel",method = RequestMethod.POST)
    @ResponseBody
    public Result toCancel(Coupon coupon,HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        coupon.setIPhone(iphone);
        System.out.println("解析请求头中的用户信息"+iphone);
        return orderService.toCancel(coupon);
    }

    /**
     * 获取用户的收货地址
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAddress",method = RequestMethod.POST)
    @ResponseBody
    public Result getAddress(HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        return orderService.getAddress(iphone);
    }

    /**
     * 保存用户收货地址
     * @return
     */
    @RequestMapping(value = "/saveAddress",method = RequestMethod.POST)
    @ResponseBody
    public Result saveAddress(Orders orders,HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        orders.setPhone(iphone);
        return orderService.saveAddress(orders);
    }

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @RequestMapping(value = "/toOrders",method = RequestMethod.POST)
    @ResponseBody
    public Result toOrders(Orders orders,HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        orders.setPhone(iphone);
        return orderService.toOrders(orders);
    }

    /**
     * 下单支付接口
     * @return
     */
    @RequestMapping(value = "/toPlay",method = RequestMethod.POST)
    @ResponseBody
    public Result toPlay(Orders orders,HttpServletRequest request) throws Exception {
        String iphone = TokenAuthenticationService.getUserId(request);
        orders.setPhone(iphone);
        return orderService.toPlay(orders);
    }

    /**
     * 支付回调
     * @param request
     */
    @RequestMapping(value="/pay/notify",method = RequestMethod.POST)
    public void notify(HttpServletRequest request) {
        System.out.println("==============vvv==================");
        String data = request.getParameter("data");
        String sign = request.getParameter("sign");
        System.out.println("data=>"+data+"|sign=>"+sign);
        orderService.aliPayNotify(data,sign);
    }

    /**
     * 获取用户订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryOrder",method = RequestMethod.POST)
    @ResponseBody
    public Result queryOrder(HttpServletRequest request){
        String iphone = TokenAuthenticationService.getUserId(request);
        return orderService.queryOrder(iphone);
    }

}
