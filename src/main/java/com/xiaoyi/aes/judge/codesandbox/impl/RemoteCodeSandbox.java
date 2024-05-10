package com.xiaoyi.aes.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.xiaoyi.aes.common.ErrorCode;
import com.xiaoyi.aes.exception.BusinessException;
import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {

    private static final String URL = "http://localhost:8888/codesandbox/execute";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String responseStr = HttpUtil.post(URL, JSONUtil.toJsonStr(executeCodeRequest));
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
