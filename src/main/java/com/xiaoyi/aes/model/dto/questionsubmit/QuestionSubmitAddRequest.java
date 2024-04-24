package com.xiaoyi.aes.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交请求
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    /**
     * 使用语言
     */
    private String language;

    /**
     * 用户具体代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}