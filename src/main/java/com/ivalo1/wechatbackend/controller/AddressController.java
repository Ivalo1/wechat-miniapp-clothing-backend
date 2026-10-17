package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.model.Address;
import com.ivalo1.wechatbackend.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Address>> listByUser(@PathVariable Long userId) {
        return ApiResponse.ok(addressService.listByUser(userId));
    }

    @PostMapping
    public ApiResponse<Address> create(@RequestBody Address address) {
        addressService.create(address);
        return ApiResponse.ok(address);
    }
}
