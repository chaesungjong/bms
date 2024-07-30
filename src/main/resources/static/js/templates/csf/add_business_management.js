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
        let form = document.getElementById('frmSubmit');
        let inputs = form.querySelectorAll('input[required], select[required]');
        let allFilled = true;

        for (let input of inputs) {
            if (!input.value.trim()) {
                allFilled = false;
                alert(input.placeholder + ' 를 입력해주세요.');
                break; // exit the loop if a required field is empty
            }
        }

        const formData = new FormData(form);
        const jsonObject = Object.fromEntries(formData.entries());

        console.log(jsonObject);

        if (allFilled) {
            // ajaxRequest 함수를 사용하여 로그인 처리
            ajaxRequest(
                '/csf/Registration.do',
                jsonObject,
                'POST',
                function (response) {
                    alert(response.retMsg);
                },
                function () {
                    alert('현재 기능 개발 준비중 입니다.');
                }
            );
        }
    });
});

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
