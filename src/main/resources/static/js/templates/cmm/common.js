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
    data: $.param(data), // 데이터를 URL 인코딩된 문자열로 변환
    dataType: "json",
    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
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


function setDatepicker(id){

  
  $(id).datepicker({
    format: 'yyyymmdd', //데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
    autoclose: true, //사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
    calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
    multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false
    multidateSeparator: ',', //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
    toggleActive: true, //이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
    weekStart: 0, //달력 시작 요일 선택하는 것 기본값은 0인 일요일
    language: 'ko', //달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
  })
  .on('changeDate', function (e) {
      /* 이벤트의 종류 */
      //show : datePicker가 보이는 순간 호출
      //hide : datePicker가 숨겨지는 순간 호출
      //clearDate: clear 버튼 누르면 호출
      //changeDate : 사용자가 클릭해서 날짜가 변경되면 호출 (개인적으로 가장 많이 사용함)
      //changeMonth : 월이 변경되면 호출
      //changeYear : 년이 변경되는 호출
      //changeCentury : 한 세기가 변경되면 호출 ex) 20세기에서 21세기가 되는 순간
      // e.date를 찍어보면 Thu Jun 27 2019 00:00:00 GMT+0900 (한국 표준시) 위와 같은 형태로 보인다.
  });

}

/**
 * 이미지 업로드 관련 함수 모음
 * @param {*} event 
 */
function allowDrop(event) {
  event.preventDefault();
}

function drag(event) {
  event.dataTransfer.setData("text", event.target.id);
}

function drop(event) {
  event.preventDefault();
  const data = event.dataTransfer.getData("text");
  const draggedImg = document.getElementById(data);
  const targetImg = event.target;

  if (targetImg.tagName === 'IMG') {
    // Swap the image sources
    const tempSrc = draggedImg.src;
    draggedImg.src = targetImg.src;
    targetImg.src = tempSrc;
  }
}
/**
 * 이미지 업로드 관련 함수 모음 끝
 */


/*
 * 파일 업로드 관련 함수
 */
function uploadFile(file,callback) {

  const storage = window.storage;
  const uploadBytes = window.uploadBytes;

  if (!file) {
      alert('파일을 선택해 주세요.');
      return;
  }
  
  const storageRef  = window.ref(storage,  file.name);

  uploadBytes(storageRef, file).then((snapshot) => {
      setfileUrlAddress(file.name,callback);
  }).catch((error) => {
    console.log(error);
  });
}

function setfileUrlAddress(fileName,callback){
    
  const getDownloadURL = window.getDownloadURL;
  getDownloadURL(ref(window.storage, fileName))
  .then((url) => {
    callback(url);
  })
  .catch((error) => {
    console.log(error);
  });

}

/*
 * 페이지 이동 함수
 */
function loadContent(url) {
  $.ajax({
      url: url,
      success: function(result) {
          $("#main-content").html(result);
      },
      error: function() {
          alert("Failed to load content.");
      }
  });
}