package com.xiaoyi.aes.service;

import com.xiaoyi.aes.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.aes.model.entity.User;

/**
* @author YiTao
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2024-04-24 17:06:05
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionId
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


}
