 
package com.groupd.bms.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;


/*
* HttpServletRequestWrapper를 상속받아서 사용자의 입력값을 검증하는 클래스입니다.
 * 사용자의 입력값에 대해 SQL Injection 공격을 방어하기 위해 사용합니다.
* 
*/
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final List<String> FORBIDDEN_WORDS = Arrays.asList(
       "DROP TABLE", "UNION", "INSERT", "UPDATE", "DELETE" // SQL Injection 공격을 방어하기 위한 금지어 목록
   );

   public CustomHttpServletRequestWrapper(HttpServletRequest request) {
       super(request);
   }

   @Override
   public String getParameter(String name) {

       String value = super.getParameter(name);

       if (value != null) {
           value = value.replaceAll(value, sanitizeInput(value));
       }
       
       return value;
   }

   public String sanitizeInput(String input) {
       for (String forbiddenWord : FORBIDDEN_WORDS) {
           // (?i)는 대소문자 구분 없이 매치하라는 의미의 정규표현식입니다.
           String regex = "(?i)" + Pattern.quote(forbiddenWord);
           input = input.replaceAll(regex, "");
       }
       return input;
   }


}