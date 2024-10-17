/*
 * csf/business_management.html 연동 스크립트 영역
 */

var table = null;
$(document).ready(function () {
    
    //검색 버튼 클릭 이벤트
    $('#searchButton').on('click', function() {
        table.draw();
    });

    //하단 페이지 버튼 클릭 이벤트
    $('#customPagination').on('click', 'a', function (e) {
        e.preventDefault();
        if (!$(this).hasClass('disabled') && !$(this).hasClass('pg_current')) {
            var page = $(this).data('page');
            table.page(page).draw(false);
            updateCustomPagination(table.settings());
        }
    });

    // 엔터 클릭 시, 검색 버튼 클릭 이벤트
    $(document).on('keypress', function(event) {
        if (event.which === 13) { 
          $('#searchButton').click();
          event.preventDefault(); 
        }
    });

    //프로필 상세 정보 삭제 시 
    $('.bm_info_close').click(function () {
        $('.bm_info_wrap, .bm_info_popup_wrap').addClass('out');
        $('body, html').removeClass('modal-active');
        $(".bm_info_popup_wrap").hide();
    });

    // 변경 이력 닫기 버튼 클릭시
    $(".bm_info_close_pop").click(function(){
        $(".bm_info_popup_wrap").fadeOut();
    });

    //거래 변경 이력 버튼 클릭시
    $('.bm_info_log').click(function () {
        $('.bm_info_popup_wrap').fadeIn();
    });



    // 회사 상세 팝업 띄우기 
    $('body').on('click', 'tr', function() {
        var searchVal = $(this).attr('data-sitekey');
        customerShowProfile(searchVal);
        customerTransactionHistory(searchVal);
    });

    // 이미지 클릭 시 이미지 상세 페이지로 이동
    $('#Registration').on('click', function(event) {
        event.preventDefault(); 
    
        var siteKey = $(this).attr('data-siteKey');

        var form = $('<form>', {
            method: 'POST',
            action: '/admin/csf/add_business_management'
        });

        form.append($('<input>', {
            type: 'hidden',
            name: 'searchVal',
            value: siteKey
        }));

        $('body').append(form);
        form.submit();
    });

    // 업체 등록 버튼 클릭 이벤트
    $('#add_business_management').on('click', function(event) {
        event.preventDefault(); 
    

        var form = $('<form>', {
            method: 'POST',
            action: '/admin/csf/add_business_management'
        });

        $('body').append(form);
        form.submit();
    });

    //회사 정보 가져오기
    setDataTable();
    
});

/**
 * 회사 정보를 조회 하는 함수
 */
function setDataTable(){

    // 회사 목록 테이블 생성
    table = $('#employeeTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/admin/csf/customer_list",
            "type": "POST",
            "data": function(d) {
                // 검색 조건을 데이터에 추가
                d.fr_date = $('#fr_date').val();                    //시작일
                d.to_date = $('#to_date').val();                    //종료일
                d.searchType = $('#searchType').val();              //검색조건
                d.search = $('#sch_word').val();                    //검색어
            }
        },
        "columns": [
            { "data": null, 
              "className": "bm_wr_wdate",
              "render": function(data, type, row) {
                return formatDate(row.regdate);
            } },
            {
                "data": null,
                "className": "bm_wr_bname",
                "render": function(data, type, row) {
                    return '<a href="#" class="bm_info">' + row.siteName + '</a>'; 
                }
            },
            { "data": null, 
              "className": "bm_wr_name",
              "render": function(data, type, row) {
                return row.ceoName;
                }
            },
            {
                "data": null,
                "className": "bm_wr_bname",
                "render": function(data, type, row) {
                    return '<span class="bm_state_nm">' + row.strSiteState + '</span>'; 
                }
            },
            {
                "data": null,
                "className": "bm_wr_bname",
                "render": function(data, type, row) {
                    return '<span class="bm_cont_ing">' + row.strContractState + '</span>'; 
                }
            },
            {
                "data": null,
                "className": "bm_wr_cdate",
                "render": function(data, type, row) {
                    // contractSdate와 contractEdate 데이터를 합쳐서 반환
                    if(row.contractSdate == "" || row.contractEdate == "") {
                        return '';
                    } else {
                        return formatDate(row.contractSdate) + " ~ " + formatDate(row.contractEdate);
                    }
                }
            },
            { "data": "memo", "className": "bm_wr_etc", "defaultContent": "" }  // 기본값 설정
        ],
        "language": {
            "paginate": {
                "first": "처음",
                "last": "맨끝",
                "next": "다음",
                "previous": "이전"
            },
            "info": "", // 정보 텍스트를 숨깁니다.
            "lengthMenu": "", // 길이 메뉴 텍스트를 숨깁니다.
            "zeroRecords": "데이터가 없습니다.", // 필요에 따라 조정
            "infoEmpty": "" // 빈 정보 텍스트를 숨깁니다.
        },
        "dom": 'rtip', // 기본 페이징 네비게이션 컨트롤을 숨깁니다.
        "createdRow": function(row, data, dataIndex) {
            // 행에 userId 값을 data 속성으로 추가
            $(row).attr('data-siteKey', data.siteKey);
        },
        "initComplete": function(settings, json) {
            updateCustomPagination(settings);
        }
    });

    //화면 새로 그려질 때마다 해당 함수 호출 (페이지 이동, 검색 등)
    table.on('draw', function () {
        updateCustomPagination(table.settings());
    });

}

/**
 * ajax  완료 후, 넘버링 데이타를 표시한다.
 * @param {} settings 
 */
function updateCustomPagination(settings) {
    var api = new $.fn.dataTable.Api(settings);
    var pageInfo = api.page.info();
    var paginationHtml = '';

    paginationHtml += '<a href="#" data-page="0" class="pg_page pg_start' + (pageInfo.page === 0 ? ' disabled' : '') + '">처음</a>';
    paginationHtml += '<a href="#" data-page="' + (pageInfo.page - 1) + '" class="pg_page pg_prev' + (pageInfo.page === 0 ? ' disabled' : '') + '">이전</a>';
    for (var i = 0; i < pageInfo.recordsTotal; i++) {
        paginationHtml += '<a href="#" data-page="' + i + '" class="pg_page' + (pageInfo.page === i ? ' pg_current' : '') + '">' + (i + 1) + '</a>';
    }
    paginationHtml += '<a href="#" data-page="' + (pageInfo.page + 1) + '" class="pg_page pg_next' + (pageInfo.page === pageInfo.recordsTotal - 1 ? ' disabled' : '') + '">다음</a>';
    paginationHtml += '<a href="#" data-page="' + (pageInfo.pages - 1) + '" class="pg_page pg_end' + (pageInfo.page === pageInfo.recordsTotal - 1 ? ' disabled' : '') + '">맨끝</a>';

    $('#customPagination').html(paginationHtml);
}

/**
 * 거래 변경 이력 조회
 */
function customerTransactionHistory(siteKey){

    var data = {
        siteKey : siteKey
    };

    ajaxRequest("/admin/csf/customer_transaction_history", data, "POST", function (data) {
        // 먼저 기존 리스트 내용을 지워줍니다.
        $('#infoList').empty();

        // 데이터를 순회하며 각각의 아이템을 li 요소로 추가합니다.
        data.forEach(item => {
            let listItem = `
                <li>
                    <span class="bm_info_popup_date">${item.moddatetime}</span>
                    <span class="bm_info_popup_name">${item.modUserName}</span>
                    <span class="bmn_info_popup_con">${item.colDesc}</span>
                </li>
            `;
            $('#infoList').append(listItem);
        });

    });

}

/**
 * 회사 정보 상세 보기
 * @param {*} searchval 
 */
function customerShowProfile(searchval) {

    var data = {
        searchVal : searchval
    };

    ajaxRequest("/admin/csf/customer_detail", data, "POST", function (response) {

        if (response.retVal == 0) {
            
            $('#siteName').text(response.siteName);
            $('#strSiteStateLevel').text(response.strSiteStateLevel);
            $('#contractSdate').text(formatDate(response.contractSdate));
            $('#contractEdate').text(formatDate(response.contractEdate));
            $('#strContractState').text(response.strContractState);
            $('#strSiteOpenState').text(response.strSiteOpenState);
            $('#transaction').text(response.transaction);
            $('#siteURL').text(response.siteURL);
            $('#siteDomainExpdt').text(formatDate(response.siteDomainExpdt));
            $('#siteHostingExpdt').text(formatDate(response.siteHostingExpdt));
            $('#adress').text(response.adress);
            $('#memo').text(response.memo);
            $('#ceoName').text(response.ceoName);
            $('#managerPhone').text(formatPhoneNumber(response.managerPhone));
            $('#managerEmail').text(response.managerEmail);
            $('#contactInformation').text(response.contactInformation);

            if(response.imgBusinessRegNo != null && response.imgBusinessRegNo != '') {
                $('#imgBusinessRegNo').text("사업자등록증 다운로드");
                $('#imgBusinessRegNo').attr('data-img', getProxy(response.imgBusinessRegNo));
            }

            if(response.imgOpenCertificate != null && response.imgOpenCertificate != '') {
                $('#imgOpenCertificate').text("학위증 다운로드");
                $('#imgOpenCertificate').attr('data-img', getProxy(response.imgOpenCertificate));
            }
            
            if(response.imgDoctorLicense != null && response.imgDoctorLicense != '') {
                $('#imgDoctorLicense').text("개설필증 다운로드");
                $('#imgDoctorLicense').attr('data-img', getProxy(response.imgDoctorLicense));
            }

            if(response.imgSpecialistLicense != null && response.imgSpecialistLicense != '') {
                $('#imgSpecialistLicense').text("이외 자료 다운로드");
                $('#imgSpecialistLicense').attr('data-img', getProxy(response.imgSpecialistLicense));
            }

            if(response.imgDegreeCertificate != null && response.imgDegreeCertificate != '') {
                $('#imgDegreeCertificate').text("의사 면허증 다운로드");
                $('#imgDegreeCertificate').attr('data-img', getProxy(response.imgDegreeCertificate));
            }
            
            if(response.imgDesignAssets != null && response.imgDesignAssets != '') {
                $('#imgDesignAssets').text("전문의 자격증 다운로드");
                $('#imgDesignAssets').attr('data-img', getProxy(response.imgDesignAssets));
            }
            
            if(response.imgEtcFiles != null && response.imgEtcFiles != '') {
                $('#imgEtcFiles').text("디자인 활용 자료 다운로드");
                $('#imgEtcFiles').attr('data-img', getProxy(response.imgEtcFiles));
            }

            if(response.imgEtc != null && response.imgEtc != '') {
                $('#imgEtc').text("디자인 활용 자료 다운로드");
                $('#imgEtc').attr('data-img', getProxy(response.imgEtc));
            }

            $('#Registration').attr('data-sitekey', response.siteKey);

            
            setSnsInformation(response.snsList);

            $('.bm_info_wrap').removeClass('on');
            $('.bm_info_wrap').removeClass('out');
            $('.bm_info_wrap' ).addClass('on');
            $('body, html').addClass('modal-active');


        } else {
            alert("다시 시도해 주세요. 에러코드 : " + response.retVal);
        }
    }, function () {
        alert('관리자에게 문의해 주세요.');
    });

}

/**
 * SNS 정보를 조회하는 함수
 * @param {*} searchVal
 */
function setSnsInformation(snsList) {

    try{

        const snsListData = JSON.parse(snsList); 

        if(snsListData != null && snsListData != '') {
            // 각 SNS 타입에 대한 input 필드에 값 할당
            snsListData.forEach(sns => {
                const snsType = sns.snsType;
                
                $('#' + snsType + 'siteDomain').text(sns.domain);
                $('#' + snsType + 'id').text(sns.id);
                $('#' + snsType + 'pw').text(sns.pwd); 
                
            });
        }

    }catch(e) {
        console.log(e);
    }

}
