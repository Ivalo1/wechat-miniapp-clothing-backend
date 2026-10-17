package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.domain.share.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class ShareAppService {
    private final ShareRepository shareRepository;

    @Transactional
    public String generateShareCode(Long userId, Long productId) {
        ShareRecord record = new ShareRecord();
        record.setUserId(userId); record.setProductId(productId);
        record.setShareType("product"); record.setShareCode(UUID.randomUUID().toString().substring(0, 8));
        record.setClickCount(0); record.setConvertCount(0);
        record.setCreateTime(java.time.LocalDateTime.now());
        shareRepository.saveShareRecord(record);
        return record.getShareCode();
    }

    @Transactional
    public void recordClick(String shareCode, Long clickUserId) {
        // TODO: 根据shareCode找到记录，增加点击数
    }

    public List<ShareRecord> getUserShareRecords(Long userId) {
        return shareRepository.findByUserId(userId);
    }
}
