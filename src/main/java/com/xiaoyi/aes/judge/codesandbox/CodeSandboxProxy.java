package com.xiaoyi.aes.judge.codesandbox;

import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    /**
     *  构造函数
     * @param codeSandbox
     */
    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse =
                codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱返回信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
