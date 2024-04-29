package com.xiaoyi.aes.judge.codesandbox.impl;

import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;

/**
*  第三方代码沙箱
*/
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("执行第三方代码沙箱");
        return null;
    }
}
