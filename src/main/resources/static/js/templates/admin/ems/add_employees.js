/*
 * ems/add_employees.html 연동 스크립트 영역
 */
$(document).ready(function () {

    // 이미지 업로드
    $('#imgProfile').change(function () {
        readURL(this);
    });

    $("#payGiveType").change(function() {
        
        // 변경된 텍스트 가져오기 (필요한 경우)
        var selectedText = $(this).find("option:selected").text();

        if("기타" == selectedText) {
            $("#bankName").removeAttr("required");
            $("#bankAccount").removeAttr("required");
        }else{
            $("#bankName").attr("required", "required");
            $("#bankAccount").attr("required", "required");
        }
        
      });

    // 한글이 필수로 들어가야할 때 사용
    $('.bank_box, .name_box').keyup(function () {
        var regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
        var v = $(this).val();
        if (regexp.test(v)) {
            alert('한글만 입력가능 합니다.');
            $(this).val(v.replace(regexp, ''));
        }
    });

    // 비밀번호 변경 모달
    $('#profile_box').click(function () {
        $('#imgProfile').click();
    });

    // 비밀번호 변경 모달
    $('.pwd_modify').click(function () {
        var caseBtn = $(this).attr('id');
        $('.modal-container').removeClass('on');
        $('.modal-container').removeClass('out');
        $('.modal-container#' + caseBtn).addClass('on');
        $('body, html').addClass('modal-active');
    });

    $('.pwd_modify_close').click(function () {
        $('.modal-container').addClass('out');
        $('body, html').removeClass('modal-active');
    });

    // 비밀번호 view
    $('.btnView').click(function () {
        $(this).toggleClass('on');
    });


    // 등록 하기 버튼 클릭 시 호출
    $('#registration').click(function () {
        // 유효성 검사
        if (validate()) {
            var form = $('#addEmpForm')[0];
            var formData = new FormData(form);
            $.ajax({
                type: 'post',
                url: '/admin/ems/Registration.do',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.retVal == '0') {
                        
                        var corrections = $('#corrections').val(); // 이미지 초기화

                        if (corrections != '') alert('직원 수정이 완료되었습니다.'); 
                        else alert('직원 등록이 완료되었습니다.');
                        
                        location.href = '/admin/ems/employees';

                    } else if (data.retVal == '-3') {
                        alert('동일한 아이디가 있습니다.');
                    } else {
                        alert('직원 등록에 실패했습니다. 관리자에게 문의하세요.');
                    }
                },
            });
        }
    });

    //직업 상태 선택 시 호출
    $('#jobStatus').change(function () {
        jobStatusChange(this);
    });

    // 입사일 선택 시 호출
    $('#jobStartDate').change(function () {
        var jobStartDate = $(this).val();
        const duration = calculateDuration(jobStartDate, getTodayDate());
        $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
    });

    // 퇴사일 선택 시 호출
    $('#jobEndDate').change(function () {
        var jobEndDate = $(this).val();
        const duration = calculateDuration($('#jobStartDate').val(), jobEndDate);
        $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
    });

    // 휴대폰 번호 입력 시
    $('#juminNo').on('input', function () {
        formatRRN($('#juminNo'));
    });

    //업무폰 입력시
    $('#hpno').on('input', function () {
        formatNumber($('#hpno'));
    });

    //업무폰 입력시
    $('#hpnoDepart').on('input', function () {
        formatNumber($('#hpnoDepart'));
    });

    //비밀번호 변경 클릭 시
    $('#changePwd').click(function () {

        var now_pwd = $('#now_pwd').val();
        var new_pwd = $('#new_pwd').val();
        var new_pwd_check = $('#new_pwd_check').val();

        if(now_pwd == ''){
            alert('현재 비밀번호를 입력해주세요.');
            return;
        }

        if(new_pwd == ''){
            alert('새 비밀번호를 입력해주세요.');
            return;
        }

        if(new_pwd_check == ''){
            alert('새 비밀번호 확인을 입력해주세요.');
            return;
        }

        if(new_pwd != new_pwd_check){
            alert('새 비밀번호가 일치하지 않습니다.');
            return;
        }

        var password = {    
            'now_pwd' : now_pwd,
            'new_pwd' : new_pwd
        };

        // ajaxRequest 함수를 사용하여 로그인 처리
        ajaxRequest("/admin/ems/pwdChange.do", password, "POST", function (response) {
            $('.pwd_modify_close').click();
            if (response.retVal == '0') {
                alert('비밀번호 변경이 완료되었습니다.');
            }else if (response.retVal == '1') {
                alert('현재 비밀번호가 일치하지 않습니다.');
            } else {
                alert('비밀번호 변경이 실패 하였습니다. ');
            }
        }, function () {
            alert('현재 기능 개발 준비중 입니다.');
        });

    });

    
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


    calculateDuration();
    init();
});

// 화면 최초 로딩 시 실행
function init() {
    // 입사일에 따라 상태 값 변경
    if ($('#jobStatus').val() === 'WORK') {
        // 입사일 상태가 "근무"인 경우 퇴사일 입력란 숨김
        $('#jobEndDateLabel').hide();
        $('#jobEndDatesmall').hide();
    } else if ($('#jobStatus').val() === 'LEAVE') {
        // 입사일 상태가 "휴일"인 경우 퇴사일 입력란 표시
        $('#jobEndDateLabel').hide();
        $('#jobEndDatesmall').hide();
    } else if ($('#jobStatus').val() === 'RETIRE') {
        // 입사일 상태가 "퇴사"인 경우 퇴사일 입력란 표시
        $('#jobEndDateLabel').show();
        $('#jobEndDatesmall').show();
    }

    // 주민등록번호 형식 변환
    formatRRN($('#juminNo'));
    jobStatusChange('#jobStatus');


    // departCode 변경 이벤트 처리
    $("#departCode").change(function() {
        var selectedDepartCode = $(this).val();

        // teamCode 옵션 모두 숨김
        $("#teamCode option").hide();
    
        // 선택된 departCode에 해당하는 _T 값을 가진 옵션만 표시 (숫자 포함 가능)
        var visibleOptions = $("#teamCode option[value^='" + selectedDepartCode + "_T']").show();
    
        // 표시된 옵션 중 첫 번째 옵션 선택
        if (visibleOptions.length > 0) {
            visibleOptions.first().prop("selected", true);
        }
    });

    // 초기 로딩 시에도 teamCode 옵션 설정
    $("#departCode").trigger("change");
    
}

/**
 * 입사일 선택에 따라 근무기간 계산
 * @param {*} value
 */
function jobStatusChange(value) {
    var selectedJobStatus = $(value).val();

    switch (selectedJobStatus) {
        case 'WORK':
            $('#jobEndDateLabel').hide();
            $('#jobEndDatesmall').hide();
            if ($('#jobStartDate').val() != '') {
                const duration = calculateDuration($('#jobStartDate').val(), getTodayDate());
                $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
            }
            break;

        case 'LEAVE':
            $('#jobEndDateLabel').hide();
            $('#jobEndDatesmall').hide();
            if ($('#jobStartDate').val() != '') {
                const duration = calculateDuration($('#jobStartDate').val(), getTodayDate());
                $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
            }
            break;

        case 'RETIRE':
            $('#jobEndDateLabel').show();
            $('#jobEndDatesmall').show();
            if ($('#jobStartDate').val() != '' && $('#jobEndDate').val() != '') {
                const duration = calculateDuration($('#jobStartDate').val(), $('#jobEndDate').val());
                $('#Period').val(`${duration.years} 년 ${duration.months} 개월`);
            }
            if ($('#jobEndDate').val() == '' || $('#jobEndDate').val() == null) {
                $('#jobEndDate').val(getTodayDate());
            }
            break;

        default:
            break;
    }
}

/**
 * 파일이 있는지 없는지 체크
 * @param {*} id
 * @returns {Boolean}
 */
function checkFileInput(id) {
    const fileInput = $(id);
    return fileInput[0].files.length > 0;
}

/**
 * 파일 이름 추출 함수
 * @param {String} url
 * @returns {String} 파일 이름
 */
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
            alert(input.getAttribute('data-alert') + '를(을) 입력해주세요.');
            break; // 필수 필드가 비어있는 경우 루프 종료
        }
    }

    if (!allFilled) return false;

    if ($('#pwd').val() != $('#pwdCheck').val()) {
        alert('비밀번호가 일치하지 않습니다.');
        allFilled = false;
    }

    return allFilled;
}

// 첨부파일 이미지 띄우기
function readURL(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            $('#profile_preview').attr('src', e.target.result);
            uploadFile(input.files[0], function (data) {
                $('.profile_box').removeClass('profile_box');
                $('#profile_preview').attr('value', data);
            });
        };

        reader.readAsDataURL(input.files[0]);
    }
}

// 우편번호
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            document.getElementById('post').value = data.zonecode;
            document.getElementById('addr').value = addr;
            document.getElementById('addrDesc').focus();
        },
    }).open();
}
