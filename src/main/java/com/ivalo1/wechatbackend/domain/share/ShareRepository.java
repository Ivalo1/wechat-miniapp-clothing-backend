package com.ivalo1.wechatbackend.domain.share;
import java.util.List;
public interface ShareRepository {
    void saveShareRecord(ShareRecord record);
    List<ShareRecord> findByUserId(Long userId);
    DistributionConfig getConfig(int level);
}
