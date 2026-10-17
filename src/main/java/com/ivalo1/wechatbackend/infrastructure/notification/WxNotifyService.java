package com.ivalo1.wechatbackend.infrastructure.notification;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service @RequiredArgsConstructor
public class WxNotifyService {
    private final WxMaService wxMaService;

    /** 订单支付成功通知 */
    public void sendOrderPaidNotify(String openid, String orderNo, String amount) {
        try {
            List<WxMaSubscribeMessage.MsgData> data = new ArrayList<>();
            data.add(new WxMaSubscribeMessage.MsgData("character_string1", orderNo));
            data.add(new WxMaSubscribeMessage.MsgData("amount2", amount));
            data.add(new WxMaSubscribeMessage.MsgData("time3", java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            WxMaSubscribeMessage msg = WxMaSubscribeMessage.builder()
                .toUser(openid).templateId("ORDER_PAID_TEMPLATE_ID").data(data).build();
            wxMaService.getMsgService().sendSubscribeMsg(msg);
        } catch (Exception e) { log.error("发送支付通知失败: openid={}", openid, e); }
    }

    /** 发货通知 */
    public void sendShipNotify(String openid, String orderNo, String company) {
        try {
            List<WxMaSubscribeMessage.MsgData> data = new ArrayList<>();
            data.add(new WxMaSubscribeMessage.MsgData("character_string1", orderNo));
            data.add(new WxMaSubscribeMessage.MsgData("thing2", company));
            WxMaSubscribeMessage msg = WxMaSubscribeMessage.builder()
                .toUser(openid).templateId("SHIP_TEMPLATE_ID").data(data).build();
            wxMaService.getMsgService().sendSubscribeMsg(msg);
        } catch (Exception e) { log.error("发送发货通知失败", e); }
    }

    /** 退款通知 */
    public void sendRefundNotify(String openid, String orderNo, String amount) {
        try {
            List<WxMaSubscribeMessage.MsgData> data = new ArrayList<>();
            data.add(new WxMaSubscribeMessage.MsgData("character_string1", orderNo));
            data.add(new WxMaSubscribeMessage.MsgData("amount2", amount));
            WxMaSubscribeMessage msg = WxMaSubscribeMessage.builder()
                .toUser(openid).templateId("REFUND_TEMPLATE_ID").data(data).build();
            wxMaService.getMsgService().sendSubscribeMsg(msg);
        } catch (Exception e) { log.error("发送退款通知失败", e); }
    }
}
