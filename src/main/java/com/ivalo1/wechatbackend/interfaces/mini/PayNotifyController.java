package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.PaymentAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/mini/pay") @RequiredArgsConstructor
public class PayNotifyController {
    private final PaymentAppService paymentAppService;
    @PostMapping("/notify")
    public String notify(@RequestBody String xmlData) { return paymentAppService.handlePayNotify(xmlData); }
}
