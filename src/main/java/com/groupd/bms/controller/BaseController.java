package com.groupd.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.groupd.bms.model.Member;
import com.groupd.bms.util.Util;

import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    @Autowired
    private Storage storage;

    /**
     * setRequest 요청 받은 파라미터를 HashMap으로 변환
     * @param request
     * @return
     */
    public HashMap<String, Object> setRequest(HttpServletRequest request) {
        HashMap<String, Object> requestMap = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            requestMap.put(key, value[0]);
        });

        Member member = (Member) request.getSession().getAttribute("member");

        if(member != null){
            requestMap.put("member", member);
        }

        requestMap.put("ip", Util.getUserIP(request));

        return requestMap;
    }

    /**
     * GCS에 파일 업로드
     * @param file
     * @return
     */
    protected void uploadFileToGCS(MultipartFile file, String fileName) {
         
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
    
            // 파일 데이터 확인
            byte[] fileBytes = file.getBytes();
            //logger.info("Uploading file: " + fileName + " with content type: " + file.getContentType() + " and size: " + fileBytes.length);
    
            // 파일 업로드
            storage.create(blobInfo, fileBytes);
    
            // 파일의 URL 생성
            //result =  String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}