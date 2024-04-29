package com.xiaoyi.aes.judge.codesandbox;

import com.xiaoyi.aes.judge.codesandbox.impl.ExampleCodeSandbox;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiaoyi.aes.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {


    }

    @Test
    void executeCodeByValue() {
        CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandbox(type);
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code("console.log('Hello, World!');")
                .inputList(inputList)
                .language("java")
                .build();
        // 执行代码并获取结果
        ExecuteCodeResponse response = codeSandbox.executeCode(request);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandbox(type);
        codeSandbox=new CodeSandboxProxy(codeSandbox);
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code("console.log('Hello, World!');")
                .inputList(inputList)
                .language("java")
                .build();
        // 执行代码并获取结果
        ExecuteCodeResponse response = codeSandbox.executeCode(request);
        Assertions.assertNotNull(response);
    }



    public static void main(String[] args) {
        System.out.println("请输入代码类型：1.example 2.remote 3.thirdParty");
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String type=scanner.next();
            CodeSandbox codeSandbox = CodeSandboxFactory.createCodeSandbox(type);
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                    .code("console.log('Hello, World!');")
                    .inputList(inputList)
                    .language("java")
                    .build();
            // 执行代码并获取结果
            ExecuteCodeResponse response = codeSandbox.executeCode(request);
        }
    }
}