package com.ivalo1.wechatbackend.infrastructure.persistence.repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.content.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.BannerMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.AnnouncementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository @RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final BannerMapper bannerMapper;
    private final AnnouncementMapper announcementMapper;
    @Override public List<Banner> findBanners(Integer position) { LambdaQueryWrapper<Banner> w = new LambdaQueryWrapper<Banner>().eq(Banner::getStatus, 1).orderByAsc(Banner::getSortOrder); if (position != null) w.eq(Banner::getPosition, position); return bannerMapper.selectList(w); }
    @Override public List<Announcement> findActiveAnnouncements() { return announcementMapper.selectList(new LambdaQueryWrapper<Announcement>().eq(Announcement::getStatus, 1).orderByDesc(Announcement::getPublishTime)); }
    @Override public void saveBanner(Banner banner) { bannerMapper.insert(banner); }
    @Override public void saveAnnouncement(Announcement a) { announcementMapper.insert(a); }
}
