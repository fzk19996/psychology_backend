package com.example.buaadataplatform.module.controller;

import com.example.buaadataplatform.module.service.AnswerService;
import com.example.buaadataplatform.module.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;

    @PostMapping(value = "/img/upload")
    // 此处的@RequestParam中的file名应与前端upload组件中的name的值保持一致
    public String imgUpload(@RequestParam("file") MultipartFile multipartFile) {
        // replaceAll 用来替换windows中的\\ 为 /
        return uploadService.imgUpload(multipartFile);
    }

    @PostMapping(value = "/video/upload")
    // 此处的@RequestParam中的file名应与前端upload组件中的name的值保持一致
    public String videoUpload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        // replaceAll 用来替换windows中的\\ 为 /
        return uploadService.videoUpload(multipartFile, session);
    }

}
