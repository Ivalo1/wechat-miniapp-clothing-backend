package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.AddressDTO;
import com.ivalo1.wechatbackend.application.service.AddressAppService;
import com.ivalo1.wechatbackend.application.vo.AddressVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/mini/address") @RequiredArgsConstructor
public class AddressController {
    private final AddressAppService addressAppService;
    @Operation(summary = "地址列表") @GetMapping("/list")
    public ApiResponse<List<AddressVO>> list(Authentication auth) { return ApiResponse.success(addressAppService.getAddressList((Long)auth.getPrincipal())); }
    @Operation(summary = "新增") @PostMapping
    public ApiResponse<Long> add(Authentication auth, @Valid @RequestBody AddressDTO dto) { return ApiResponse.success(addressAppService.saveAddress((Long)auth.getPrincipal(), dto)); }
    @Operation(summary = "修改") @PutMapping("/{id}")
    public ApiResponse<Void> update(Authentication auth, @PathVariable Long id, @Valid @RequestBody AddressDTO dto) { addressAppService.updateAddress((Long)auth.getPrincipal(), id, dto); return ApiResponse.success(); }
    @Operation(summary = "删除") @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(Authentication auth, @PathVariable Long id) { addressAppService.deleteAddress((Long)auth.getPrincipal(), id); return ApiResponse.success(); }
}
