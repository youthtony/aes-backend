package com.xiaoyi.aes.judge.codesandbox.impl;

import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiaoyi.aes.model.enums.JudgeInfoMessageEnum;
import com.xiaoyi.aes.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
* 示例代码沙箱
*/
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 解析请求参数
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        // 执行代码并返回结果
        ExecuteCodeResponse executeCodeResponse=new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo=new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
