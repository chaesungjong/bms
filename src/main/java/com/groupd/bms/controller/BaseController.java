package com.groupd.bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.groupd.bms.model.Member;
import com.groupd.bms.util.StringUtil;
import com.groupd.bms.util.Util;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            requestMap.put("loginUserid", member.getUserid());
            requestMap.put("siteKey", member.getSiteKey());
        }

        requestMap.put("ip", Util.getUserIP(request));
        requestMap.put("loginUserip", Util.getUserIP(request));

        return requestMap;
    }

    /**
     * Map을 JSON으로 변환
     * @param params
     * @return
     */
    protected String setJsonToMap(List<Map<String, Object>> params) {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsoString ="";

        try{
            jsoString = objectMapper.writeValueAsString(params);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsoString;
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
     * 파일 이름 설정
     * @param file
     * @param fileName
     * @return
     */
    protected String setFileName(MultipartFile file,  String fileName) {
        // 파일의 원래 이름에서 확장자 추출
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        }

        // 파일 이름에 확장자 추가
        String fullFileName = fileName + fileExtension;

        return fullFileName;
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

    /**
     * 페이징 처리
     * @param params
     * @return
     */
    protected String setPagination(Map<String, Object> params) {
        
        int CNT = Integer.parseInt(StringUtil.objectToString(params.get("CNT")));
        int PAGESIZE = Integer.parseInt(StringUtil.objectToString(params.get("PAGESIZE")));

        return StringUtil.objectToString(CNT/PAGESIZE + (CNT%PAGESIZE > 0 ? 1 : 0));
    }

    /**
     * 파일 URL 주소 생성
     * @param fileName
     */
    protected String getFileUrl(String fileName) {
        return "/proxy" + fileName;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/admin/dsb/main"; 
    }

	@GetMapping("/proxy/{fileName}")
    public ResponseEntity<byte[]> proxyFile(@PathVariable("fileName") String fileName) {
		return ResponseEntity.ok().body(downloadFileFromGCS(fileName));
    }

    /**
	 * 사용자 IP 주소 반환
	 * @param request HttpServletRequest
	 * @return IP 주소 문자열
	 */
	protected String getUserIP(HttpServletRequest request) {
		String clientIP = request.getHeader("X-Forwarded-For");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}

}