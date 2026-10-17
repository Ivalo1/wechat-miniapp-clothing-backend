package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.FavoriteAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/mini/favorite") @RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteAppService favoriteAppService;
    @Operation(summary = "收藏") @PostMapping("/{productId}")
    public ApiResponse<Void> add(Authentication auth, @PathVariable Long productId) { favoriteAppService.addFavorite((Long)auth.getPrincipal(), productId); return ApiResponse.success(); }
    @Operation(summary = "取消收藏") @DeleteMapping("/{productId}")
    public ApiResponse<Void> remove(Authentication auth, @PathVariable Long productId) { favoriteAppService.removeFavorite((Long)auth.getPrincipal(), productId); return ApiResponse.success(); }
}
