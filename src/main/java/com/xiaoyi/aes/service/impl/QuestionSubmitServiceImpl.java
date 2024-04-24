package com.xiaoyi.aes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyi.aes.common.ErrorCode;
import com.xiaoyi.aes.exception.BusinessException;
import com.xiaoyi.aes.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiaoyi.aes.model.entity.Question;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.xiaoyi.aes.model.entity.User;
import com.xiaoyi.aes.service.QuestionService;
import com.xiaoyi.aes.service.QuestionSubmitService;
import com.xiaoyi.aes.service.QuestionSubmitService;
import com.xiaoyi.aes.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author YiTao
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2024-04-24 17:06:05
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{
    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 提交记录的ID
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交
        long userId = loginUser.getId();
        // 每个用户串行提交
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
//        todo 设置初始状态
        questionSubmit.setStatus(0);
        questionSubmit.setJudgeInfo("{}");
        boolean save=this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"提交失败");
        }
        return questionSubmit.getId();
    }
}




