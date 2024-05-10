package com.xiaoyi.aes.judge.codesandbox.impl;

import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
*  第三方代码沙箱
*/
@Slf4j
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("第三方代码沙箱");
        return null;
    }
}
