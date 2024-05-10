package com.xiaoyi.aes.judge.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyi.aes.common.ErrorCode;
import com.xiaoyi.aes.exception.BusinessException;
import com.xiaoyi.aes.judge.JudgeService;
import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.factory.CodeSandboxFactory;
import com.xiaoyi.aes.judge.codesandbox.proxy.CodeSandboxProxy;
import com.xiaoyi.aes.judge.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.model.ExecuteCodeResponse;
import com.xiaoyi.aes.judge.strategy.JudgeContext;
import com.xiaoyi.aes.judge.strategy.JudgeManager;
import com.xiaoyi.aes.model.dto.question.JudgeCase;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.entity.Question;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.xiaoyi.aes.model.enums.QuestionSubmitLanguageEnum;
import com.xiaoyi.aes.model.enums.QuestionSubmitStatusEnum;
import com.xiaoyi.aes.service.QuestionService;
import com.xiaoyi.aes.service.QuestionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  @Description 判题服务实现类
 */
@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Resource
    private JudgeManager judgeManager;


    @Override
    public QuestionSubmit doJudge(QuestionSubmit questionSubmit) {
//        获取到对应的题目、提交信息（包含代码、编程语言等）
        if (ObjectUtils.isEmpty(questionSubmit)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
//        Question question = questionService.getOne(
//                Wrappers.lambdaQuery(Question.class).eq(Question::getId, questionId).select(Question::getJudgeCase, Question::getJudgeConfig)
//        );
        Question question = questionService.getById(questionId);
        if (ObjectUtils.isEmpty(question)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        Long questionSubmitId = questionSubmit.getId();
        String code = questionSubmit.getCode();
        QuestionSubmitLanguageEnum languageType = QuestionSubmitLanguageEnum.getEnumByValue(questionSubmit.getLanguage());
        List<JudgeCase> judgeCases = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        List<String> outputList = judgeCases.stream().map(JudgeCase::getOutput).collect(Collectors.toList());

//        核验题目是否存在
//        更新题目提交状态为运行中
        //region 更新状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        questionSubmitService.updateById(questionSubmitUpdate);
        //endregion
//        调用沙箱 获取执行结果
        //region 获取代码沙箱执行结果
        CodeSandbox codeSandbox = codeSandboxFactory.newInstance();//!确定沙箱类型 示例/远程/第三方
        ExecuteCodeRequest request = ExecuteCodeRequest
                .builder()
                .code(code)
                .inputList(inputList)
                .language(languageType)
                .build();
        ExecuteCodeResponse executeCodeResponse = new CodeSandboxProxy(codeSandbox).executeCode(request);//通过代理执行代码返回结果
        //endregion
        // 根据沙箱的执行结果，对结果进行处理，设置题目的判题状态和信息
        // 设置上下文
        List<String> outputListResult = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setOutputList(outputList);
        judgeContext.setOutputListResult(outputListResult);
        judgeContext.setQuestion(question);
        judgeContext.setLanguageType(QuestionSubmitLanguageEnum.getEnumByValue(questionSubmit.getLanguage()));

        // 修改数据库中的判题结果
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);//! 进行判题，看结果是否符合预期
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmitService.updateById(questionSubmitUpdate);

        // todo 对题目进行一定的数据更新
        question.setId(question.getId());
        question.setSubmitNum(question.getSubmitNum()+1);
        if (QuestionSubmitStatusEnum.SUCCEED.getText().equals(judgeInfo.getMessage().toString())){
            question.setAcceptNum(question.getAcceptNum()+1);
        }
        //todo 点赞数与收参数
//        question.setThumbNum();
//        question.setFavourNum();
//        保存
        boolean updateQuestion = questionService.updateById(question);
        if (!updateQuestion){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        return questionSubmitService.getById(questionSubmit.getId());
    }
}
