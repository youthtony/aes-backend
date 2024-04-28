package com.xiaoyi.aes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyi.aes.model.dto.question.QuestionQueryRequest;
import com.xiaoyi.aes.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiaoyi.aes.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiaoyi.aes.model.entity.Question;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyi.aes.model.entity.User;
import com.xiaoyi.aes.model.vo.QuestionSubmitVO;
import com.xiaoyi.aes.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author YiTao
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2024-04-24 17:06:05
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
