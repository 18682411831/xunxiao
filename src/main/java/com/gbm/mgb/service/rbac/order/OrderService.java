package com.gbm.mgb.service.rbac.order;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.domain.order.OrderDao;
import com.gbm.mgb.dto.Coupon;
import com.gbm.mgb.dto.Orders;
import com.gbm.mgb.utils.order.*;
import com.gbm.mgb.utils.redis.JedisClientPool;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 登陆 on 2018/1/2.
 */
@Service
@Transactional
public class OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private JedisClientPool jedisClientPool;

    /**
     * 劵码验证
     * @param coupon
     * @return
     */
    public  Result cancel(Coupon coupon) {
        Coupon coup = orderDao.findCouponByTicket(coupon);
        if (coup!=null && coup.getStatus()==1 ){
            logger.info("传入的劵码信息",coupon);
            return Result.ok("验证!正确");
        }else if (coup.getStatus()==2){
            return Result.build(401,"劵码已核销!!");
        }
        return Result.build(400,"劵码密码不正确!!");
    }

    /**
     * 劵码核销更改状态
     * @param coupon
     * @param
     * @return
     */
    public Result toCancel(Coupon coupon) {
        Coupon coupon1 = orderDao.findCouponByTicket(coupon);
        String ticket = jedisClientPool.get(coupon.getTicket());
        if (coupon1!=null && StringUtils.isEmpty(ticket) && coupon1.getStatus() == 1){
            jedisClientPool.set(coupon.getTicket(),coupon.getPassword().toString());
            jedisClientPool.expire(coupon.getTicket(),20);
            //更改为核销状态
            coupon.setStatus(2);
            orderDao.updateCouponByTicket(coupon);
            logger.info("被核销的劵码",coupon1);
            return Result.ok("SUCCESS");
        }
        return Result.build(403,"核销失败,劵码异常");
    }
    /**
     * 判断用户是否核销/或者支付   3:已经支付 2:已经核销 1:未核销
     * @return
     */
    public Result checkedUser(String iphone) {
        Coupon cou = orderDao.findCouponByIphone(iphone);
        if (cou == null || cou.getStatus() == 1){
            return Result.ok(1);
        }
        logger.info("该用户的状态",cou);
        if (cou.getPlayStatus() == 2){
            return Result.ok(3);
        }
        if (cou.getPlayStatus() == 3){
            Result.build(400,"该订单支付异常");
        }
        if (cou.getStatus() == 2){
            return Result.ok(2);
        }
        return Result.build(403,"接口异常");
    }
    /**
     * 获取用户的收货地址
     * @param
     * @return
     */
    public Result getAddress(String iphone) {
        Orders order = orderDao.findOrdersByIphone(iphone);
        if (order != null){
           logger.info("用户的收货地址",order);
            return Result.ok(order);
        }
        return Result.build(400,"收货地址为空");
    }
    /**
     * 保存/更新/用户收货地址
     * @return
     */
    public Result saveAddress(Orders orders) {
        if (!isChinaPhoneLegal(orders.getOphone())){
            return Result.build(400,"联系电话不正确!");
        }
        Orders orders1 = orderDao.findOrdersByIphone(orders.getPhone());
        if (orders1 == null) {
            logger.info("保存用户收货地址"+orders);
            orderDao.saveOrdersIphone(orders);
        }else {
            logger.info("更新用户收货地址"+orders);
            orderDao.updateOrdersByAddress(orders);
        }
        return Result.ok();
    }

/*    @Autowired
    private SMSDao smsDao;

    *//**
     * 短信登陆业务
     * @param sms
     * @return
     *//*
    public Result login(SMS sms) {
        if (StringUtils.isEmpty(sms.getMobilePhone())){
            return Result.build(403,"电话号码不能为空!!");
        }else if (!isChinaPhoneLegal(sms.getMobilePhone())){
            return Result.build(403,"电话号码不正确,请核对!!");
        }else if (StringUtils.isEmpty(sms.getValidCode())){
            return Result.build(403,"验证码不能为空!!");
        }
        sms.setType(19);
        //核对验证码
        String code = smsDao.findValidCode(sms);
        if (!sms.getValidCode().equals(code)){
            return Result.build(404,"验证码错误,请核对后输入");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("validCode", sms.getValidCode());
        map.put("mobilePhone", sms.getMobilePhone());
        //更新状态
        smsDao.updateState(map);
        TokenAuthenticationService.createAuthentication(new HttpServletResponse,);
    }
 *//**
     * 国内手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "1[34578]\\d{9}";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @Value("${MONEY}")
    private Integer money;
    public Result toOrders(Orders orders){
        Orders order = orderDao.findOrdersByIphone(orders.getPhone());
        if (orders!=null && orders.getMoney().compareTo(new BigDecimal(money)) !=-1 && !StringUtils.isEmpty(orders.getGoods()) && order!=null && !StringUtils.isEmpty(order.getAddress())){
            //生成订单号
            orders.setOrderId("ZXIX"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(orders.getPhone().substring(7)));
            orderDao.updateOrdersAndToPlace(orders);
            Orders order_to = orderDao.findOrdersByIphone(orders.getPhone());
            logger.info("用户下单返回参数"+order_to);
            return Result.ok(order_to);
        }
        return Result.build(403,"下单失败,请稍候再试");
    }
    /**
     * 下单支付接口
     * @return
     */
    public Result toPlay(Orders orders)  {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(orders.getOrderId())){
            return Result.build(400,"订单编号不能为空!!");
        }
        Orders order = orderDao.findOrdersToPlayInFo(orders);
        if (order == null){
            return Result.build(400,"该订单不存在,或者已支付!");
        }
        //订单流水号
        String playNo = "P"+OrderUtils.createOrderNumber();
        orders.setPayNo(playNo);
        //更新订单流水号
        orderDao.updateOrdersByPlayNo(orders);
        orderDao.updateCouponByPlayNo(orders);
   try {
       //支付宝
        if (orders.getPlayType() == 1){
                responseMap.put("AliPayUrl", buildAliPay(order.getMoney()+"", playNo));
            return Result.ok(responseMap);
            //微信
        }else if (orders.getPlayType() == 2){
            responseMap.put("WeChatPayUrl", buildWechatPay(order.getMoney().toString(), playNo));
            System.out.println(responseMap.get("WeChatPayUrl"));
            return Result.ok(responseMap);
        }
  } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.build(400,"请勾选支付方式");
    }
    /**
     * 支付宝支付
     */
    private String buildAliPay(String price,String playNo) throws Exception {
        // 加密后的支付金额
        String payAmount = Base64.encode(RSACoder.encryptByPublicKey((new BigDecimal(price)+"").getBytes(), Config.publicKey));
        // 加密后的本平台支付商户编号
        String paternerNo = Base64.encode(RSACoder.encryptByPublicKey(
                Config.paternerNo.getBytes(), Config.publicKey));
        String proName = "Apple Iphone X 64GB";
        String proDesc = "Apple Iphone X 64GB";
        String payType = "0";// 支付类型(0手机wap,1pc即时到帐)
        // 接口请求地址
        String purl = Config.alipayUrl + "payAmount=" + URLEncoder.encode(payAmount, "utf-8")
                + "&paternerNo=" + URLEncoder.encode(paternerNo, "utf-8")
                + "&orderNo=" + playNo + "&proName="
                + URLEncoder.encode(proName, "utf-8") + "&proDesc="
                + URLEncoder.encode(proDesc, "utf-8") + "&payType="
                + payType;
        logger.info("支付宝请求地址",purl);
        return purl;
    }

    /**
     * 微信支付
     * @param price 支付金额
     * @param playNo 流水号
     * @return
     */
    private String buildWechatPay(String price,String playNo){
            // 加密后的支付金额
            try {
                price = ((int) (Double.parseDouble(price) * 100)) + "";
                String payAmount = Base64.encode(RSACoder.encryptByPublicKey((price + "").getBytes(), Config.publicKey));
                System.out.println("支付金额================" + payAmount);
                String proName = "Apple Iphone X 64GB";
                String proDesc = "Apple Iphone X 64GB";

                JSONObject json = new JSONObject();
                json.put("orderNo", playNo);
                json.put("partnerNo", Config.paternerNo);
                json.put("payAmount", payAmount);
                json.put("proDesc", proDesc);

                String result = HttpClientUtils.postJson(Config.wechatUrl, json.toString());
                System.out.println("返回参数====" + result);
                JSONObject json_result = JSONObject.fromObject(result);
                logger.info("返回参数",json_result);
                if ("000000".equals(json_result.get("resCode"))) {
                    logger.info("微信支付请求地址",(String) json_result.getJSONObject("data").get("weChatPayUrl"));
                    return (String) json_result.getJSONObject("data").get("weChatPayUrl");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    /**
     * 阿里支付回调
     * @param data
     * @param sign
     */
    public void aliPayNotify(String data, String sign) {
        logger.info("回调参数"+data);
        JSONObject json = JSONObject.fromObject(data);
        String payNo = json.get("orderNo") + "";
        String tradeNo = json.get("tradeNo") + "";
        logger.info("支付流水号"+payNo);
        logger.info("第三方支付流水号"+tradeNo);
        String mySign = "";
        try {
            mySign = MD5Util.getMd5String(Config.MD5String + data);
        } catch (UnsupportedEncodingException e) {
        }
        Orders order = new Orders();
        logger.info("数据验证签名"+mySign.equals(sign));
        //1.变更支付流水状态 => 成功
        order.setPayNo(payNo);
        order.setTradeNo(tradeNo);
        if (mySign.equals(sign)) {
            order.setStatus(2);
             orderDao.updatePayOrderStateToSuccess(order);
             orderDao.updateCouponStateToSuccess(order);
        } else {
            //变更支付流水状态 => 失败
            order.setStatus(3);
            orderDao.updatePayOrderStateToSuccess(order);
            orderDao.updateCouponStateToSuccess(order);
            System.out.println("变更订单失败状态：" );

        }
    }

    /**
     * 获取用户订单信息
     * @param iphone
     * @return
     */
    public Result queryOrder(String iphone) {
        if (StringUtils.isEmpty(iphone)){
            return Result.build(400,"参数错误");
        }
        Coupon cou = orderDao.findCouponByIphone(iphone);
        if(cou!=null && cou.getPlayStatus() == 2){
            Orders orders = orderDao.findOrdersByIphone(iphone);
            return Result.ok(orders);
        }
        return Result.build(403,"劵码重复核销!!越界操作!");

    }
}
