package com.xiaoyi.aes.judge.codesandbox.impl;

import com.xiaoyi.aes.judge.codesandbox.CodeSandbox;
import com.xiaoyi.aes.judge.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.model.ExecuteCodeResponse;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.enums.JudgeInfoMessageEnum;
import com.xiaoyi.aes.model.enums.QuestionSubmitLanguageEnum;
import com.xiaoyi.aes.model.enums.QuestionSubmitStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* 示例代码沙箱
*/
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        QuestionSubmitLanguageEnum language = executeCodeRequest.getLanguage();
        log.info("处理代码中，语言类型为{}...{}", language.getText(), code);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        return ExecuteCodeResponse
                .builder()
                .message("测试执行成功")
                .status(QuestionSubmitStatusEnum.SUCCEED.getValue())
                .outputList(inputList)
                .judgeInfo(judgeInfo)
                .build();
    }
}
