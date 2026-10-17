package com.ivalo1.wechatbackend.domain.content;
import java.util.List;
public interface ContentRepository {
    List<Banner> findBanners(Integer position);
    List<Announcement> findActiveAnnouncements();
    void saveBanner(Banner banner);
    void saveAnnouncement(Announcement announcement);
}
