window.onload = function () {

};

/**
 * api 통신 공통 함수
 * @param {API 주소}  url 
 * @param {데이터}    data 
 * @param {HTTP 메소드} method
 * @param {성공 콜백} successCallback 
 * @param {실패 콜백} errorCallback 
 */
function ajaxRequest(url, data, method, successCallback, errorCallback) {
  $.ajax({
    url: url,
    method: method || "GET", // 기본값으로 "GET" 설정
    data: data,
    dataType: "json",
    success: successCallback,
    error: errorCallback
  });
}

/**
 * 특수문자 제거
 * return true : 금칙어 존재
 * return false : 금칙어 없음
 **/
function forbiddenWordsCheck(str) {
  for (let word of forbiddenWords) {
    if (str.toLowerCase().includes(word.toLowerCase())) {
      return true;
    }
  }
  return false;
}


const forbiddenWords = [
  // 기존 금칙어
  "badword1", "badword2", "badword3",

  // SQL 인젝션
  "OR '1'='1'", "DROP TABLE", "--", ";", "UNION", "SELECT", "INSERT", "UPDATE", "DELETE",

  // 크로스 사이트 스크립팅(XSS)
  "<script>", "alert(", "document.cookie", "window.location", "<img", "onload", "onerror",

  // 시스템 명령어 실행
  "&&", "|", ">", "rm ", "shutdown ", "echo", "ls ", "cat ",

  // 디렉토리 탐색(Directory Traversal)
  "../", "..\\", "%2e%2e/", "%2e%2e\\"
];

function showAlert(message) {
  alert(message);
}

function logout() {

    // ajaxRequest 함수를 사용하여 로그인 처리
    ajaxRequest( "/mmb/logout.do", "", "POST", function () {
      // 로그인 성공 시 대시보드 페이지로 리디렉트
      location.href = "/mmb/login";
  }, function () {
     location.href = "/mmb/login";
  });
}