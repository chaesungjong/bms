package com.groupd.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.groupd.bms.model.Member;
import com.groupd.bms.util.Util;

import java.io.FileNotFoundException;
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
            requestMap.put("userId", member.getUserid());
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
            // 파일 업로드
            storage.create(blobInfo, fileBytes);

        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }

        /**
     * GCS에서 파일 다운로드
     * @param fileName 다운로드할 파일 이름
     * @return 파일 데이터
     */
    protected byte[] downloadFileFromGCS(String fileName) {
        try {

            BlobId blobId = BlobId.of(bucketName, fileName);
            Blob     blob = storage.get(blobId);

            if (blob != null && blob.exists()) return blob.getContent();
            else  throw new FileNotFoundException("File not found: " + fileName);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * GCS에서 파일 삭제
     * @param fileName 삭제할 파일 이름
     */
    protected void deleteFileFromGCS(String fileName) {
        try {

            BlobId blobId = BlobId.of(bucketName, fileName);
            boolean deleted = storage.delete(blobId);

            if (!deleted) throw new FileNotFoundException("File not found or couldn't be deleted: " + fileName);
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}