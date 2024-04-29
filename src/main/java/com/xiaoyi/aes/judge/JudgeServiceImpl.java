package com.xiaoyi.aes.judge;

import cn.hutool.json.JSONUtil;
import com.xiaoyi.aes.common.ErrorCode;
import com.xiaoyi.aes.exception.BusinessException;
import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.CodeSandboxFactory;
import com.xiaoyi.aes.judge.codesandbox.CodeSandboxProxy;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;
import com.xiaoyi.aes.judge.strategy.JudgeContext;
import com.xiaoyi.aes.model.dto.question.JudgeCase;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.entity.Question;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.xiaoyi.aes.model.enums.JudgeInfoMessageEnum;
import com.xiaoyi.aes.model.enums.QuestionSubmitStatusEnum;
import com.xiaoyi.aes.service.QuestionService;
import com.xiaoyi.aes.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService{

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
//        传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit=questionSubmitService.getById(questionSubmitId);
        if (questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
//        核验题目是否存在
        Question question = questionService.getById(questionId);
        if (question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        // 如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
//        更新题目提交状态为运行中
        QuestionSubmit questionSubmitUpdate=new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新");
        }
//        调用沙箱 获取执行结果
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr=question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandbox(type);
        codeSandbox=new CodeSandboxProxy(codeSandbox);

        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        // 执行代码并获取 执行结果
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 根据沙箱的执行结果，对结果进行处理，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
//        设置上下文
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
