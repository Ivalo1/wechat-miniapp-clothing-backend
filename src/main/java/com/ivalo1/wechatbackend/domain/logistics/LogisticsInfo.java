package com.ivalo1.wechatbackend.domain.logistics;
import lombok.Data;
import java.util.List;

@Data
public class LogisticsInfo {
    private String orderNo;
    private String company;
    private String trackingNo;
    private String status;
    private List<TraceNode> traces;

    @Data
    public static class TraceNode {
        private String time;
        private String description;
    }
}
