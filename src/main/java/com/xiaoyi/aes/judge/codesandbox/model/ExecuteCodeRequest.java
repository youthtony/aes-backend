package com.xiaoyi.aes.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 这是执行代码的请求体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 输入
     */
    private List<String> inputList;

    /**
     * 代码
     */
    private String code;

    /**
     * 使用语言
     */
    private String language;
}
