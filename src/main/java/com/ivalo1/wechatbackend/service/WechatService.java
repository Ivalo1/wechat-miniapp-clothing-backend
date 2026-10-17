package com.ivalo1.wechatbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class WechatService {

    @Value("${wechat.appid:}")
    private String appId;

    @Value("${wechat.secret:}")
    private String secret;

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String,String> codeToSession(String code) throws Exception {
        // jscode2session
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appId, secret, code);
        URI uri = new URI(url);
        String resp = rest.getForObject(uri, String.class);
        JsonNode node = mapper.readTree(resp);
        Map<String,String> result = new HashMap<>();
        if (node.has("openid")) {
            result.put("openid", node.get("openid").asText());
        }
        if (node.has("session_key")) {
            result.put("session_key", node.get("session_key").asText());
        }
        if (node.has("errcode")) {
            result.put("errcode", node.get("errcode").asText());
            result.put("errmsg", node.has("errmsg")?node.get("errmsg").asText():"");
        }
        return result;
    }
}
