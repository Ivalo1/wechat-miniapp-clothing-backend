package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.util.List;
@Data
public class LogisticsVO { private String company; private String trackingNo; private String status; private List<TraceVO> traces;
    @Data public static class TraceVO { private String time; private String description; }
}
