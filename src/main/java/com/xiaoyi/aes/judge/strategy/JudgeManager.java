package com.xiaoyi.aes.judge.strategy;

import com.xiaoyi.aes.judge.strategy.impl.DefaultJudgeStrategy;
import com.xiaoyi.aes.judge.strategy.impl.JavaJudgeStrategy;
import com.xiaoyi.aes.model.dto.questionsubmit.JudgeInfo;
import com.xiaoyi.aes.model.entity.QuestionSubmit;
import com.xiaoyi.aes.model.enums.QuestionSubmitLanguageEnum;
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
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmitLanguageEnum languageType = judgeContext.getLanguageType();
        JudgeStrategy judgeStrategy;
        if (QuestionSubmitLanguageEnum.JAVA.equals(languageType)) {
            judgeStrategy = new JavaJudgeStrategy();
        }else {
            judgeStrategy = new DefaultJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
