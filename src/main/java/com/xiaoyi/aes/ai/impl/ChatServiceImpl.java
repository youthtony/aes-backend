package com.xiaoyi.aes.ai.impl;

import cn.hutool.json.JSONObject;
import com.xiaoyi.aes.ai.ChatService;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatServiceImpl implements ChatService {

    public static final String URL = "https://luckycola.com.cn/ai/openwxyy";

//    todo 改为自己的值
    public static final String APP_KEY = "xxx";

    public static final String UID = "xxx";

    public static final String IS_LONG_CHAT = "1";

    @Override
    public String chat(String message) {
        OkHttpClient client = new OkHttpClient();

        // 设置请求的URL
        RequestBody body = RequestBody.create(
                "{\n" +
                        "  \"appKey\": \"" + APP_KEY + "\",\n" +
                        "  \"ques\": \"" + message + "\",\n" +
                        "  \"uid\": \"" + UID + "\"\n" +
                        "}",
                MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println("Response: " + responseBody);
//            将响应体转换为JSON对象
            JSONObject jsonObject = new JSONObject(responseBody);
            if (jsonObject.get("code").toString().equals("0"))
                return new JSONObject(jsonObject.get("data")).get("result").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 发生异常时返回空
    }
}
