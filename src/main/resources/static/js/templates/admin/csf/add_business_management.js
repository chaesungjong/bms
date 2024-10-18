/*
 * csf/add_business_management.html 연동 스크립트 영역
 */

$(document).ready(function () {

    const fileTarget = $('.add_bm_file input');
    var counter = 1;

    // 수정 시에는 사이트 코드 입력란을 보여준다.
    if($('#corrections').val() == 'Y') 
        $('#siteCode_tr').show();
    

    //담당자 추가 버튼
    $(".bm_pic_plus").click(function() {
        counter++;
        var newSelect = $("<select name='bmMember" + counter + "' id='bmMember" + counter + "' class='bm_pic'></select>");
        $("#bmMember").append(newSelect); 
        setMember('bmMember'+counter);

    });


    // 변경 이력 닫기 버튼 클릭시
    $(".bm_info_close_pop").click(function(){
        $(".bm_info_popup_wrap").fadeOut();
    });

    //거래 변경 이력 버튼 클릭시
    $('.bm_log').click(function () {
        if ($('#infoList li').length === 0) {
            alert("데이터가 없습니다.");
        }else{
            $('.bm_info_popup_wrap').fadeIn();
        }
    });


    // 등록 하기 버튼
    $('#Registration').click(function () {

        if(validate()){
            var form = $('#frmSubmit')[0];
            var formData = new FormData(form);
            $.ajax({
                type: 'post',
                url: '/admin/csf/Registration.do',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {

                    if (data.retVal == '0') {
                        
                        var corrections = $('#corrections').val(); // 이미지 초기화

                        if (corrections != '') alert('업체 수정이 완료되었습니다.'); 
                        else alert('업체 등록이 완료되었습니다.');
                        
                        location.href = '/admin/csf/business_management';

                    } else {
                        alert('등록에 실패했습니다.');
                    }
                }, error: function (request, status, error) {
                    console.log('code: ' + request.status + '\n' + 'message: ' + request.responseText + '\n' + 'error: ' + error);
                }
            });
        }

    });

    // 업체 관리 리스트 페이지로 이동
    $('#businessManagement').click(function () {
        location.href = '/admin/csf/business_management'; 
    });

    fileTarget.on('change', function () {
        var files = $(this)[0].files;
        var fileArr = [];
        for (var i = 0; i < files.length; i++) {
            fileArr.push(files[i].name);
        }

        var fileList = fileArr.join('<br>');
        $(this).siblings('.bm_file_text').html(fileList);
    }); 

    
    setMember('bmMember1');
    setSnsInformation() ;
    setMemberList();
    customerTransactionHistory();


});

/**
 * 회원 목록을 셋팅한다.
 */
function setMemberList(){
    
    try{

        var jsonData = $('#regMemberList').val();
        var data = JSON.parse(jsonData);
      
        $.each(data.slice(0, -1), function(index, item) {
            $(".bm_pic_plus").click();
        });

        $.each(data, function(index, item) {

            var id = "bmMember" + ++index;
            $("#" + id).val(item.userkey); 

            $("#" + id+" option:contains('선택해주세요')").remove();

            // hidden input 태그 생성 및 속성 설정
            var hiddenInput = $("<input>", {
                type: "hidden",
                id: id + "seq",
                name: id + "seq", // 필요에 따라 name 속성 추가
                value: item.seq
            });
        
            // hidden input 태그를 원하는 위치에 추가 (예: body 태그)
            $("form").append(hiddenInput); 
        });

    }catch(e) {
        //console.log(e);
    }
}

/**
 * SNS 정보를 조회하는 함수
 */
function setSnsInformation() {

    try{
        const snsListData = JSON.parse($('#snsList').val()); 
        if(snsListData != null && snsListData != '') {
            // 각 SNS 타입에 대한 input 필드에 값 할당
        snsListData.forEach(sns => {

            const snsType = sns.snsType;
            if(sns.domain != null || sns.domain != '') document.getElementById(snsType + 'siteDomain').value = sns.domain;
            if(sns.id != null || sns.id != '')         document.getElementById(snsType + 'id').value = sns.id;
            if(sns.pwd != null || sns.pwd != '')       document.getElementById(snsType + 'pw').value = sns.pwd;
            if(sns.seq != null || sns.seq != '')       document.getElementById(snsType + 'seq').value = sns.seq;
            
        });
    }
    }catch(e) {
        //console.log(e);
    }

}



/**
 * 다음 우편번호 API 호출
 * @returns
 */
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

/**
 * 담당자 목록을 셋팅한다.
 * @param {*} name 
 */
function setMember(name){

    try{

        var jsonData = $('#memberList').val();
        var data = JSON.parse(jsonData);

        $("#"+name).append($("<option></option>")
        .attr("value", "")
        .text('이름(팀명)')); 

        $.each(data, function(index, item) {
          $("#"+name).append($("<option></option>")
            .attr("value", item.userkey)
            .text(item.name + ' (' + item.strTeamCode + ')')); 
        });

    }catch(e) {
        console.log(e);
    }

}

/**
 * 거래 변경 이력 조회
 */
function customerTransactionHistory(){

    var data = {
        siteKey : $('#siteKey').val()
    };

    $('#infoList').empty();

    ajaxRequest("/admin/csf/customer_transaction_history", data, "POST", function (data) {

        // 데이터를 순회하며 각각의 아이템을 li 요소로 추가합니다.
        data.forEach((item, index) => {
            let listItem = `
                <li>
                    <span class="bm_info_popup_date">${item.moddatetime}</span>
                    <span class="bm_info_popup_name">${item.modUserName}</span>
                    <span class="bm_info_popup_name">${item.colDesc} : ${item.gubun ==="U" && item.oldVal != "" ? item.oldVal +"->" : ""} ${item.newVal}</span>
                </li>
            `;
            $('#infoList').append(listItem);
        });
        
    });

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