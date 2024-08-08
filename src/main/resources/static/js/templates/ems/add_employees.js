/*
 * ems/add_employees.html 연동 스크립트 영역
 */
$(document).ready(function () {

    // 이미지 업로드 
    $('.file-input').change(function () {
        readURL(this);
    });

    // 한글이 필수로 들어가야할 때 사용
    $('.bank_box, .name_box').keyup(function () {
        regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
        v = $(this).val();
        if (regexp.test(v)) {
            alert('한글만 입력가능 합니다.');
            $(this).val(v.replace(regexp, ''));
        }
    });

    if($('#jobStatus').val() == "WORK"){
        $('#jobEndDateLabel').hide();
        $('#jobEndDatesmall').hide();
    }else if($('#jobStatus').val() == "LEAVE"){
        
        $('#jobEndDateLabel').show();
        $('#jobEndDatesmall').show();

    }else if($('#jobStatus').val() == "RETIRE"){

    }

    //등록 하기 버튼 클릭 시 호출 
    $('#registration').click(function () {
        //유효성 검사
        if (validate()) {
            var form = $('#addEmpForm')[0];
            var formData = new FormData(form);
            $.ajax({
                type: 'post',
                url: '/ems/Registration.do',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.retVal == '0') {
                        alert('직원 등록이 완료되었습니다.');
                        location.href = '/employees';
                    } else if(data.retVal == '-3') {
                        alert('동일한 아이디가 있습니다. ');
                    } else {
                        alert('직원 등록에 실패했습니다. 관리자에게 문의하세요.');
                    }
                }
            });
        }
    });

    
      $('#jobStatus').change(function() {
        
        var selectedJobStatus = $(this).val();

        $('#Period').val();
        switch(selectedJobStatus) {
            case "WORK": {
                $('#jobEndDateLabel').hide();
                $('#jobEndDatesmall').hide();
                if($('#jobStartDate').val()!=""){
                    const duration = calculateDuration( $('#jobStartDate').val(), getTodayDate());
                    $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
                }
            }
                break;
            case "LEAVE":{
                $('#jobEndDateLabel').hide();
                $('#jobEndDatesmall').hide();
                if($('#jobStartDate').val()!=""){
                    const duration = calculateDuration( $('#jobStartDate').val(), getTodayDate());
                    $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
                }
            }
                break;
            case "RETIRE":{
                $('#jobEndDateLabel').show();
                $('#jobEndDatesmall').show();
                if($('#jobStartDate').val()!="" && $('#jobEndDate').val()!=""){
                    const duration = calculateDuration( $('#jobStartDate').val(), $('#jobEndDate').val());
                    $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
                }
            }
                break; 
            default: 
                break;
        }

    });

    $('#jobStartDate').change(function() {
        
        var jobStartDate = $(this).val();

        const duration = calculateDuration(jobStartDate, getTodayDate());
        $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);

    });

    $('#jobEndDate').change(function() {
        
        var jobEndDate = $(this).val();

        const duration = calculateDuration( $('#jobStartDate').val(), jobEndDate);
        $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);

    });

    $('#juminNo').on('input', function() {
        // this는 현재 이벤트가 발생한 요소를 가리킴
        formatRRN( $('#juminNo'));
    });

    // if($('#jobStartDate').val != ""){
    //     $('#jobStartDate').val(formatDate($('#jobStartDate').val()));
    // }

    // if($('#jobEndDate').val != ""){
    //     $('#jobEndDate').val(formatDate($('#jobEndDate').val()));
    // }


    // if($('#birthday').val != ""){
    //     $('#birthday').val(formatDate($('#birthday').val()));
    // }
    // 초기 입력값에 대해 형식 적용 및 유효성 검사
    formatRRN($('#juminNo'));
    calculateDuration();
});

function calculateDuration(){

    // 초기 상태 설정
    if (checkFileInput('#imgBankbook')) {
        $('#memInfoImgBankbookMessage').show();
    } else {
        $('#memInfoImgBankbookMessage').hide();
    }

    // 초기 상태 설정
    if (checkFileInput('#imgFamilyRL')) {
        $('#imgFamilyRLMessage').show();
    } else {
        $('#imgFamilyRLMessage').hide();
    }


    // 초기 상태 설정
    if (checkFileInput('#imgProfile')) {
        $('#imgProfileMessage').show();
    } else {
        $('#imgProfileMessage').hide();
    }


    // 초기 상태 설정
    if (checkFileInput('#imgEtc')) {
        $('#imgEtcMessage').text(imgEtc).show();
    } else {
        $('#imgEtcMessage').hide();
    }
    
}

// 데이터가 있는지 없는지 체크하는 함수
function checkFileInput(id) {
    const fileInput = $(id);
    if (fileInput[0].files.length > 0) {
        // 파일 입력 요소에 파일이 선택된 경우
        return true;
    }else {
        // 둘 다 없는 경우
        return false;
    }
}


// 파일 이름 추출 함수
function getFileName(url) {
    return url.substring(url.lastIndexOf('/') + 1);
}


/**
 * 직원 등록 유효성 검사
 * @returns {Boolean}
 */
function validate() {
    let form = document.getElementById('addEmpForm');
    let inputs = form.querySelectorAll('input[required], select[required]');
    let allFilled = true;

    for (let input of inputs) {
        if (!input.value.trim()) {
            allFilled = false;
            input.focus();
            alert(input.placeholder + "를(을) 입력해주세요.");
            break; // exit the loop if a required field is empty
        }
    }

    if(!allFilled) return false;

    if( $('#pwd').val() != $('#pwdCheck').val() ) {
        alert("비밀번호가 일치하지 않습니다.");
        allFilled = false;
    }



    return allFilled;
}

// 첨부파일 이미지 띄우기
function readURL(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        const previewId = input.id;

        reader.onload = function (e) {
            $('#' + previewId).attr('src', e.target.result);
            uploadFile(input.files[0], function (data) {
                //$('#' + previewId).attr('src', data);
                $('#' + previewId).attr('value', data);
            });
        };

        reader.readAsDataURL(input.files[0]);
    }
}

const realUpload = document.querySelector('.real_upload');
const upload = document.querySelector('.upload');

upload.addEventListener('click', () => realUpload.click());

/* 팀 카테고리 선택시 팀 선택 옵션 변경
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
}*/


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
            document.getElementById('post').value = data.zonecode;
            document.getElementById('addr').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('addrDesc').focus();
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
