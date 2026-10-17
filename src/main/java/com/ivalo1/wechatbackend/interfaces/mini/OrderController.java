package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.OrderCreateDTO;
import com.ivalo1.wechatbackend.application.service.OrderAppService;
import com.ivalo1.wechatbackend.application.service.PaymentAppService;
import com.ivalo1.wechatbackend.application.vo.OrderDetailVO;
import com.ivalo1.wechatbackend.application.vo.OrderListVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.infrastructure.lock.IdempotentLock;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/mini/order") @RequiredArgsConstructor
public class OrderController {
    private final OrderAppService orderAppService;
    private final PaymentAppService paymentAppService;
    @Operation(summary = "创建订单") @PostMapping("/create")
    @IdempotentLock(key="'order:create:'+#auth.principal", message="订单正在创建中")
    public ApiResponse<String> create(Authentication auth, @Valid @RequestBody OrderCreateDTO dto) { return ApiResponse.success(orderAppService.createOrder((Long)auth.getPrincipal(), dto)); }
    @Operation(summary = "订单列表") @GetMapping("/list")
    public ApiResponse<PageResult<OrderListVO>> list(Authentication auth, @RequestParam(required=false) Integer status, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int size) { return ApiResponse.success(orderAppService.getOrderList((Long)auth.getPrincipal(), status, page, size)); }
    @Operation(summary = "订单详情") @GetMapping("/{orderNo}")
    public ApiResponse<OrderDetailVO> detail(Authentication auth, @PathVariable String orderNo) { return ApiResponse.success(orderAppService.getOrderDetail((Long)auth.getPrincipal(), orderNo)); }
    @Operation(summary = "取消订单") @PostMapping("/cancel/{orderNo}")
    public ApiResponse<Void> cancel(Authentication auth, @PathVariable String orderNo) { orderAppService.cancelOrder((Long)auth.getPrincipal(), orderNo); return ApiResponse.success(); }
    @Operation(summary = "确认收货") @PostMapping("/confirm/{orderNo}")
    public ApiResponse<Void> confirm(Authentication auth, @PathVariable String orderNo) { orderAppService.confirmReceive((Long)auth.getPrincipal(), orderNo); return ApiResponse.success(); }
    @Operation(summary = "发起支付") @PostMapping("/pay/{orderNo}")
    @IdempotentLock(key="'pay:'+#orderNo", message="支付正在处理中")
    public ApiResponse<Map<String,String>> pay(Authentication auth, @PathVariable String orderNo) { return ApiResponse.success(paymentAppService.createPayment((Long)auth.getPrincipal(), orderNo)); }
}
