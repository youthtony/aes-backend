package com.xiaoyi.aes.judge.codesandbox;


import com.xiaoyi.aes.judge.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
