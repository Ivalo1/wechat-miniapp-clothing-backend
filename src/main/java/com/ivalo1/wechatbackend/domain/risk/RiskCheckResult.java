package com.ivalo1.wechatbackend.domain.risk;
import lombok.Data;

@Data
public class RiskCheckResult {
    private boolean passed;
    private String reason;
    private int riskLevel; // 0正常 1低风险 2高风险

    public static RiskCheckResult pass() {
        RiskCheckResult r = new RiskCheckResult();
        r.passed = true; r.riskLevel = 0;
        return r;
    }
    public static RiskCheckResult reject(String reason, int level) {
        RiskCheckResult r = new RiskCheckResult();
        r.passed = false; r.reason = reason; r.riskLevel = level;
        return r;
    }
}
