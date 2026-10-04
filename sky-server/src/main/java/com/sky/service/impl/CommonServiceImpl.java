package com.sky.service.impl;

import com.sky.properties.AliyunOSSProperties;
import com.sky.service.CommonService;
import com.sky.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @Override
    public String upLoad(MultipartFile file) throws Exception {
        return aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
    }
}
