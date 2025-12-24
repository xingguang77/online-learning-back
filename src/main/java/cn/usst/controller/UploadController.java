package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.utils.AliyunOSSOperator;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 员工管理Controller
 */
@Slf4j
@RequestMapping("/upload")
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping
    public Result upload(MultipartFile file) throws IOException, ClientException {
        log.info("接收参数：{} ",file.getOriginalFilename());
        String url=aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传oss,url：{} ",url);
        return Result.success(url);
    }

}
