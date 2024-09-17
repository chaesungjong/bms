$(document).ready(function () {

    /**
     * 회원 가입 기능 
     */
    $("#register").click(function () {
    
        // 유효성 검사
        if(!validateForm()){
            return;
        }

        // 로그인 데이터 준비
        var loginData = $("#registrationForm").serialize();
        // ajaxRequest 함수를 사용하여 로그인 처리
        ajaxRequest("/mmb/registerProcess.do", loginData,"POST", function(response) {
            // 로그인 성공 시 대시보드 페이지로 리디렉트
            if (response.success) {
                window.location.href = '/';
            } else {
                alert(response.message || 'Invalid username or password');
            }
        }, function() {
            alert('현재 기능 개발 준비중 입니다.');
        });
    });
});

/**
 * 회원 가입 유효성 검사
 */
function validateForm() {
    var inputs = document.querySelectorAll('#registrationForm .form-control');
    for (var i = 0; i < inputs.length; i++) {
        if (!inputs[i].value.trim()) {
            alert(inputs[i].placeholder + '을(를) 입력해주세요.');
            return false;
        }

        if(forbiddenWordsCheck(inputs[i].value)){
            alert('금지된 단어가 포함되어 있습니다.');
            return false;
        }
    }
    return true;
}