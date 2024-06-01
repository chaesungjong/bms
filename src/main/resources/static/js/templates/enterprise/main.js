$(document).ready(function () {

    // 주소 검색 버튼
    $("#addressSearch").click(function () {

        new daum.Postcode({
            oncomplete: function (data) {

                $("#address").val(data.address);
                $("#addressNumber").val(data.zonecode);

            }
        }).open();

    });

    // 등록 하기 버튼
    $("#Registration").click(function () {

        let form = document.getElementById('frmSubmit');
        let inputs = form.querySelectorAll('input[required], select[required]');
        let allFilled = true;

        for (let input of inputs) {
            if (!input.value.trim()) {
                allFilled = false;
                alert(input.placeholder + " 를 입력해주세요.");
                break; // exit the loop if a required field is empty
            }
        }

        if (!allFilled) {
            // ajaxRequest 함수를 사용하여 로그인 처리
            ajaxRequest("/enterprise/Registration.do", null, "POST", function (response) {
                alert(response.errMsg);
            }, function () {
                alert('현재 기능 개발 준비중 입니다.');
            });
        }
    });

});