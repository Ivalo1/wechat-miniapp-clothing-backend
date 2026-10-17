package com.ivalo1.wechatbackend.infrastructure.logistics;
import com.ivalo1.wechatbackend.domain.logistics.LogisticsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class LogisticsService {
    /** 查询物流轨迹 (对接快递100/快递鸟API) */
    public LogisticsInfo queryLogistics(String company, String trackingNo) {
        // TODO: 对接快递100 API
        // String url = "http://poll.kuaidi100.com/poll/query.do";
        LogisticsInfo info = new LogisticsInfo();
        info.setCompany(company); info.setTrackingNo(trackingNo);
        info.setStatus("在途"); info.setTraces(List.of());
        return info;
    }
}
