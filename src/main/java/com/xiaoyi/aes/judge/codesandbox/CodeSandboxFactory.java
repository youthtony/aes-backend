package com.xiaoyi.aes.judge.codesandbox;

import com.xiaoyi.aes.judge.codesandbox.impl.ExampleCodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.impl.RemoteCodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
* 代码沙箱工厂类（根据传入的字符串参数，创建对应的代码沙箱实例）
*/
public class CodeSandboxFactory {

    /**
     * 根据传入的字符串参数，创建对应的代码沙箱实例
     * @param codeSandboxType 代码沙箱类型
     * @return 代码沙箱实例
     */
    public static CodeSandbox createCodeSandbox(String codeSandboxType) {
        switch (codeSandboxType) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                throw new IllegalArgumentException("Invalid code sandbox type: " + codeSandboxType);
        }
    }
}
