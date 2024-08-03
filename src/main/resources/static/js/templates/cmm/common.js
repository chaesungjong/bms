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

// 현재 날짜를 구하는 함수
function getTodayDate() {
  const today = new Date();
  const year = today.getFullYear();
  const month = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1 필요
  const day = ('0' + today.getDate()).slice(-2); // 날짜가 한 자리 수일 때 0을 앞에 붙임
  return `${year}-${month}-${day}`;
}

function calculateDuration(startDate, endDate) {
  // Convert the input strings to Date objects if they are strings
  startDate = new Date(startDate);
  endDate = new Date(endDate);

  // Calculate the difference in months
  let totalMonths = (endDate.getFullYear() - startDate.getFullYear()) * 12;
  totalMonths += endDate.getMonth() - startDate.getMonth();

  // Calculate years and remaining months
  const years = Math.floor(totalMonths / 12);
  const months = totalMonths % 12;

  return { years, months };
}

function setCookie(key, value, days) {
  var date = new Date();
  date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
  var expires = "; expires=" + date.toUTCString();
  document.cookie = key + "=" + (value || "") + expires + "; path=/";
}

function formatRRN(input) {
  
  var value = "";
  try{
    value = input.val().replace(/\D/g, ''); // 숫자가 아닌 모든 문자 제거
  }catch(e){
    value = input.value.replace(/\D/g, ''); // 숫자가 아닌 모든 문자 제거
  }
  const formattedValue = value.replace(/(\d{6})(\d{1,7})?/, '$1-$2');

  input.value = formattedValue.slice(0, 14); // 최대 길이를 14로 제한

  // // 유효성 검사
  // if (isValidRRN(formattedValue)) {
  //     input.classList.remove('error');
  //     input.classList.add('success');
  //     document.getElementById('error-message').style.display = 'none';
  // } else {
  //     input.classList.add('error');
  //     input.classList.remove('success');
  //     document.getElementById('error-message').style.display = 'inline';
  // }
}

function isValidRRN(rrn) {
  // 기본적인 형식 검사
  const regex = /^\d{6}-\d{7}$/;
  if (!regex.test(rrn)) {
      return false;
  }

  // 추가적인 논리 검사는 여기서 할 수 있음 (예: 유효한 날짜인지 확인)
  const [front, back] = rrn.split('-');
  const year = parseInt(front.substring(0, 2), 10);
  const month = parseInt(front.substring(2, 4), 10);
  const day = parseInt(front.substring(4, 6), 10);

  if (month < 1 || month > 12) {
      return false;
  }
  
  if (day < 1 || day > 31) {
      return false;
  }

  // 월별 날짜 수 검증
  const daysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  if (day > daysInMonth[month - 1]) {
      return false;
  }

  return true;
}