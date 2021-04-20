package com.example.buaadataplatform.module.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface UploadService {

    public String imgUpload(MultipartFile file);

    public String videoUpload(MultipartFile file, HttpSession session);
}
