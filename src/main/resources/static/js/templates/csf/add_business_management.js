/*
 * csf/add_business_management.html 연동 스크립트 영역
 */

$(document).ready(function () {
    $('#siteDomainExpdt').val(getTodayDate());
    $('#siteHostingExpdt').val(getTodayDate());

    $('#contractSdate').val(getTodayDate());
    $('#contractEdate').val(getTodayDate());

    const fileTarget = $('.add_bm_file input');

    fileTarget.on('change', function () {
        var files = $(this)[0].files;
        var fileArr = [];
        for (var i = 0; i < files.length; i++) {
            fileArr.push(files[i].name);
        }

        var fileList = fileArr.join('<br>');
        $(this).siblings('.bm_file_text').html(fileList);
    });

    // 등록 하기 버튼
    $('#Registration').click(function () {

        if(validate()){
            var form = $('#frmSubmit')[0];
            var formData = new FormData(form);
            $.ajax({
                type: 'post',
                url: '/csf/Registration.do',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {

                    if (data.retVal == '0') {
                        alert('등록되었습니다.');
                        location.href = '/csf/business_management';
                    } else {
                        alert('등록에 실패했습니다.');
                    }
                }, error: function (request, status, error) {
                    console.log('code: ' + request.status + '\n' + 'message: ' + request.responseText + '\n' + 'error: ' + error);
                }
            });
        }

    });

    setSnsInformation() ;
});

function setSnsInformation() {

    const snsListData = JSON.parse($('#snsList').val()); 

    if(snsListData != null && snsListData != '') {
        
        try{

             // 각 SNS 타입에 대한 input 필드에 값 할당
            snsListData.forEach(sns => {
                const snsType = sns.snsType;
                
                document.getElementById(snsType + 'siteDomain').value = sns.domain;
                document.getElementById(snsType + 'id').value = sns.id;
                document.getElementById(snsType + 'pw').value = sns.pwd;
                
            });

        }catch(e) {
            console.log(e);
        }
    }

}

document.addEventListener('DOMContentLoaded', function () {
    const radioButtons = document.querySelectorAll('input[type="radio"]');

    radioButtons.forEach((radio) => {
        radio.addEventListener('click', function (e) {
            if (this.previousChecked) {
                this.checked = false;
            }
            this.previousChecked = this.checked;
        });
    });
});

function bm_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById('postNo').value = data.zonecode;
            document.getElementById('address').value = addr;
            document.getElementById('addressDesc').focus();
        },
    }).open();
}
document.addEventListener('DOMContentLoaded', (event) => {
    const blogUp = document.getElementById('blogUp');
    const blogDown = document.getElementById('blogDown');
    const cntBlogPos = document.getElementById('cntBlogPos');

    blogUp.addEventListener('click', () => {
        let currentValue = parseInt(cntBlogPos.value, 10) || 0;
        cntBlogPos.value = currentValue + 1;
    });

    blogDown.addEventListener('click', () => {
        let currentValue = parseInt(cntBlogPos.value, 10) || 0;
        cntBlogPos.value = Math.max(currentValue - 1, 0); // Prevent negative values
    });
});

/**
 * 업체 등록 유효성 검사
 * @returns {Boolean}
 */
function validate() {
    let form = document.getElementById('frmSubmit');
    let inputs = form.querySelectorAll('input[required], select[required]');
    let allFilled = true;

    for (let input of inputs) {
        if (!input.value.trim()) {
            allFilled = false;
            input.focus();
            alert(input.placeholder + "를(을) 입력해주세요.");
            break; // 필수 필드가 비어있는 경우 루프 종료
        }
    }
    
    return allFilled;
}