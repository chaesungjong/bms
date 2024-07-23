/*
 * ems/add_employees.html 연동 스크립트 영역
 */
$(document).ready(function () {

    $('.profile').change(function () {
        readURL(this);
    });

    $('.bank_box, .name_box').keyup(function (event) {
        regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
        v = $(this).val();
        if (regexp.test(v)) {
            alert('한글만 입력가능 합니다.');
            $(this).val(v.replace(regexp, ''));
        }
    });
    
});
// 첨부파일 이미지 띄우기
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#profile_preview').attr('src', e.target.result);
            $('.upload').css({ display: 'none' });
        };

        reader.readAsDataURL(input.files[0]);
    }
}

const realUpload = document.querySelector('.real_upload');
const upload = document.querySelector('.upload');

upload.addEventListener('click', () => realUpload.click());

// 부서 선택시 팀 동적변경
function categoryChange(e) {
    var team_a = ['1팀', '2팀'];
    var team_b = ['1팀', '2팀', '3팀', '4팀'];
    var team_c = ['팀'];
    var target = document.getElementById('team_count');

    if (e.value == '디자인') var d = team_a;
    else if (e.value == '마케팅') var d = team_b;
    else var d = team_c;

    target.options.length = 0;

    for (x in d) {
        var opt = document.createElement('option');
        opt.value = d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }
}


// 날짜
window.onload = function () {
    today = new Date();
    today = today.toISOString().slice(0, 10);
    bir = document.getElementById('today_date');
    bir.value = today;
};

// 우편번호
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') {
                // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById('sample6_address').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_detailAddress').focus();
        },
    }).open();
}

// input file 커스텀

const fileTarget = $('.add_emp_file input');

fileTarget.on('change', function () {
    var files = $(this)[0].files;
    var fileArr = [];
    for (var i = 0; i < files.length; i++) {
        fileArr.push(files[i].name);
    }

    var fileList = fileArr.join('<br>');
    $(this).siblings('.bm_file_text').html(fileList);
});
