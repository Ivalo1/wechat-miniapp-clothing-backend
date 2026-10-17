package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.dto.BannerDTO;
import com.ivalo1.wechatbackend.application.dto.AnnouncementDTO;
import com.ivalo1.wechatbackend.application.vo.BannerVO;
import com.ivalo1.wechatbackend.application.vo.AnnouncementVO;
import com.ivalo1.wechatbackend.domain.content.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ContentAppService {
    private final ContentRepository contentRepository;

    @Cacheable(value = "banner", key = "'position:' + #position")
    public List<BannerVO> getBanners(Integer position) {
        return contentRepository.findBanners(position).stream().map(b -> {
            BannerVO vo = new BannerVO();
            vo.setId(b.getId()); vo.setTitle(b.getTitle()); vo.setImageUrl(b.getImageUrl());
            vo.setLinkType(b.getLinkType()); vo.setLinkValue(b.getLinkValue());
            return vo;
        }).collect(Collectors.toList());
    }

    @Cacheable(value = "banner", key = "'announcements'")
    public List<AnnouncementVO> getAnnouncements() {
        return contentRepository.findActiveAnnouncements().stream().map(a -> {
            AnnouncementVO vo = new AnnouncementVO();
            vo.setId(a.getId()); vo.setTitle(a.getTitle()); vo.setContent(a.getContent());
            vo.setPublishTime(a.getPublishTime());
            return vo;
        }).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "banner", allEntries = true)
    public void createBanner(BannerDTO dto) {
        Banner b = new Banner();
        b.setTitle(dto.getTitle()); b.setImageUrl(dto.getImageUrl()); b.setLinkType(dto.getLinkType());
        b.setLinkValue(dto.getLinkValue()); b.setSortOrder(dto.getSortOrder()); b.setStatus(dto.getStatus());
        b.setPosition(dto.getPosition());
        contentRepository.saveBanner(b);
    }

    @Transactional
    @CacheEvict(value = "banner", allEntries = true)
    public void createAnnouncement(AnnouncementDTO dto) {
        Announcement a = new Announcement();
        a.setTitle(dto.getTitle()); a.setContent(dto.getContent()); a.setStatus(dto.getStatus());
        a.setPublishTime(LocalDateTime.now());
        contentRepository.saveAnnouncement(a);
    }
}
