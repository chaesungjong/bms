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

    $('#datePicker').datepicker({
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

});