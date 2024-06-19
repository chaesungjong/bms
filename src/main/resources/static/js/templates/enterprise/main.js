$(document).ready(function () {

    // 주소 검색 버튼
    $("#address").click(function () {

        new daum.Postcode({
            oncomplete: function (data) {

                $("#address").val(data.address);
                $("#postNo").val(data.zonecode);


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


        const formData = new FormData(form);
        const jsonObject = Object.fromEntries(formData.entries());

        console.log(jsonObject);

        if (allFilled) {
            // ajaxRequest 함수를 사용하여 로그인 처리
            ajaxRequest("/enterprise/Registration.do", jsonObject, "POST", function (response) {
                alert(response.retMsg);
            }, function () {
                alert('현재 기능 개발 준비중 입니다.');
            });
        }
    });

    setDatepicker($("#siteDomainExpdt"));
    setDatepicker($("#siteHostingExpdt"));
    setDatepicker($("#contractSdate"));
    setDatepicker($("#contractEdate"));

});