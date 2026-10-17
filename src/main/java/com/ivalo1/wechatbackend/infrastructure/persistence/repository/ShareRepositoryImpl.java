package com.ivalo1.wechatbackend.infrastructure.persistence.repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.share.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ShareRecordMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.DistributionConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository @RequiredArgsConstructor
public class ShareRepositoryImpl implements ShareRepository {
    private final ShareRecordMapper shareRecordMapper;
    private final DistributionConfigMapper configMapper;
    @Override public void saveShareRecord(ShareRecord record) { shareRecordMapper.insert(record); }
    @Override public List<ShareRecord> findByUserId(Long userId) { return shareRecordMapper.selectList(new LambdaQueryWrapper<ShareRecord>().eq(ShareRecord::getUserId, userId).orderByDesc(ShareRecord::getCreateTime)); }
    @Override public DistributionConfig getConfig(int level) { return configMapper.selectOne(new LambdaQueryWrapper<DistributionConfig>().eq(DistributionConfig::getLevel, level).eq(DistributionConfig::getStatus, 1)); }
}
