package com.xiaoyi.aes.controller;


import com.xiaoyi.aes.ai.ChatService;
import com.xiaoyi.aes.common.BaseResponse;
import com.xiaoyi.aes.common.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 文件控制器
 */
@RestController
@Api(tags = "chat")
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 请求对话
     */
    @PostMapping("/request")
    public BaseResponse<String> request(String message) {
        return ResultUtils.success(chatService.chat(message));
    }
}
