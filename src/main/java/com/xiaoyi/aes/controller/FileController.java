package com.xiaoyi.aes.controller;

import cn.hutool.core.io.FileUtil;
import com.xiaoyi.aes.common.BaseResponse;
import com.xiaoyi.aes.common.ErrorCode;
import com.xiaoyi.aes.common.ResultUtils;
import com.xiaoyi.aes.constant.FileConstant;
import com.xiaoyi.aes.exception.BusinessException;
import com.xiaoyi.aes.manager.CosManager;
import com.xiaoyi.aes.model.dto.file.UploadFileRequest;
import com.xiaoyi.aes.model.entity.User;
import com.xiaoyi.aes.model.enums.FileUploadBizEnum;
import com.xiaoyi.aes.service.FileService;
import com.xiaoyi.aes.service.UserService;

import java.io.File;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 */
@RestController
@Api(tags = "file")
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;


    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public BaseResponse<String> upload(@RequestPart MultipartFile file) {
       return ResultUtils.success(fileService.fileUpload(file));
    }

    @PostMapping("/tmp")
    @ApiOperation("获取临时文件访问链接")
    public BaseResponse<String> getTempAccess(String key) {
        return ResultUtils.success(fileService.getTmpAccess(key));
    }

}
