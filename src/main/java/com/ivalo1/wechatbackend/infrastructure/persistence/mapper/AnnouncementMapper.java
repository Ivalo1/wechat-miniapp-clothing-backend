package com.ivalo1.wechatbackend.infrastructure.persistence.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivalo1.wechatbackend.domain.content.Announcement;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {}
