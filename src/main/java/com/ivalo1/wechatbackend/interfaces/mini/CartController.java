package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.CartAddDTO;
import com.ivalo1.wechatbackend.application.dto.CartUpdateDTO;
import com.ivalo1.wechatbackend.application.service.CartAppService;
import com.ivalo1.wechatbackend.application.vo.CartVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/mini/cart") @RequiredArgsConstructor
public class CartController {
    private final CartAppService cartAppService;
    @Operation(summary = "购物车列表") @GetMapping("/list")
    public ApiResponse<List<CartVO>> list(Authentication auth) { return ApiResponse.success(cartAppService.getCartList((Long)auth.getPrincipal())); }
    @Operation(summary = "加入购物车") @PostMapping("/add")
    public ApiResponse<Void> add(Authentication auth, @Valid @RequestBody CartAddDTO dto) { cartAppService.addToCart((Long)auth.getPrincipal(), dto); return ApiResponse.success(); }
    @Operation(summary = "更新数量") @PostMapping("/update")
    public ApiResponse<Void> update(Authentication auth, @Valid @RequestBody CartUpdateDTO dto) { cartAppService.updateCartItem((Long)auth.getPrincipal(), dto); return ApiResponse.success(); }
    @Operation(summary = "删除") @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(Authentication auth, @PathVariable Long id) { cartAppService.removeFromCart((Long)auth.getPrincipal(), id); return ApiResponse.success(); }
}
