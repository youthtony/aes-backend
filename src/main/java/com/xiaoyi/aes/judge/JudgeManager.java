package com.xiaoyi.aes.judge;

import com.xiaoyi.aes.judge.strategy.DefaultJudgeStrategy;
import com.xiaoyi.aes.judge.strategy.JavaLanguageJudgeStrategy;
import com.xiaoyi.aes.judge.strategy.JudgeContext;
import com.xiaoyi.aes.judge.strategy.JudgeStrategy;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
//        确定开发语言，对不同语言进行不同的判题处理
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
