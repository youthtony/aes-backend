package com.xiaoyi.aes.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  @Description 判断信息消息枚举
 */
public enum JudgeInfoMessageEnum {

//    Accepted 成功
ACCEPTED("accepted", "成功"),
//    Wrong Answer 答案错误
    WRONG_ANSWER("wrong_answer", "答案错误"),
//    Compile Error 编译错误
    COMPILE_ERROR("compile_error", "编译错误"),
//    Memory Limit Exceeded 内存溢出
    MEMORY_LIMIT_EXCEEDED("memory_limit_exceeded", "内存溢出"),
//    Time Limit Exceeded 超时
    TIME_LIMIT_EXCEEDED("time_limit_exceeded", "超时"),
//    Presentation Error 展示错误
    PRESENTATION_ERROR("presentation_error", "展示错误"),
//    Output Limit Exceeded 输出溢出
    OUTPUT_LIMIT_EXCEEDED("output_limit_exceeded", "输出溢出"),
//    Waiting 等待中
    WAITING("waiting", "等待中"),
//    Dangerous Operation 危险操作
    DANGEROUS_OPERATION("dangerous_operation", "危险操作"),
//    Runtime Error 运行错误（用户程序的问题）
    RUNTIME_ERROR("runtime_error", "运行错误"),
//    System Error 系统错误（做系统人的问题）
    SYSTEM_ERROR("system_error", "系统错误");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
