/*
 * csf/add_business_management.html 연동 스크립트 영역
 */
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
            document.getElementById('bm_postcode').value = data.zonecode;
            document.getElementById('bm_address').value = addr;
            document.getElementById('bm_detailAddress').focus();
        },
    }).open();
}

function bm_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById('bm_postcode').value = data.zonecode;
            document.getElementById('bm_address').value = addr;
            document.getElementById('bm_detailAddress').focus();
        },
    }).open();
}

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
