package com.xiaoyi.aes.judge.codesandbox.impl;

import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;

/**
* 远程代码沙箱
*/
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("执行远程代码");
        return null;
    }
}
