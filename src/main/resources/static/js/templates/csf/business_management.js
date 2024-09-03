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

        // 이벤트 리스너 등록
    document.addEventListener('keypress', function(event) {
        if (event.keyCode === 13) {
            $('#searchButton').click();
            event.preventDefault(); 
        }
    });



    // 회사 목록 테이블 생성
    $('.bm_info').click(function () {
        var userBtn = $(this).attr('id');
        $('.bm_info_wrap').removeClass('on');
        $('.bm_info_wrap').removeClass('out');
        $('.bm_info_wrap#' + userBtn).addClass('on');
        $('body, html').addClass('modal-active');
    });
    
    // 회사 목록 테이블 생성
    $('.bm_info_close').click(function () {
        $('.bm_info_wrap').addClass('out');
        $('body, html').removeClass('modal-active');
    });

    // 직원 상세 페이지 팝업 띄우기
    $('body').on('click', 'tr', function() {
        var searchVal = $(this).attr('data-sitekey');
        customerShowProfile(searchVal);
    });

    $('#Registration').on('click', function(event) {
        event.preventDefault(); 
    
        var siteKey = $(this).attr('data-img');

        var form = $('<form>', {
            method: 'POST',
            action: '/csf/add_business_management'
        });

        form.append($('<input>', {
            type: 'hidden',
            name: 'searchVal',
            value: siteKey
        }));

        $('body').append(form);
        form.submit();
    });

    $('#Registration').on('click', function(event) {
        event.preventDefault(); 
    
        var siteKey = $(this).attr('data-img');

        var form = $('<form>', {
            method: 'POST',
            action: '/csf/add_business_management'
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
            action: '/csf/add_business_management'
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
            "url": "/csf/customer_list",
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
                    return formatDate(row.contractSdate) + " ~ " + formatDate(row.contractEdate);
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
 * 회사 정보 상세 보기
 * @param {*} searchval 
 */
function customerShowProfile(searchval) {

    var data = {
        searchVal : searchval
    };

    ajaxRequest("/csf/customer_detail", data, "POST", function (response) {

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

            $('#imgBusinessRegNo').text(response.imgBusinessRegNo);
            $('#imgBusinessRegNo').attr('data-img', getProxy(response.imgBusinessRegNo));

            $('#imgOpenCertificate').text(response.imgOpenCertificate);
            $('#imgOpenCertificate').attr('data-img', getProxy(response.imgOpenCertificate));

            $('#imgDoctorLicense').text(response.imgDoctorLicense);
            $('#imgDoctorLicense').attr('data-img', getProxy(response.imgDoctorLicense));

            $('#imgSpecialistLicense').text(response.imgSpecialistLicense);
            $('#imgSpecialistLicense').attr('data-img', getProxy(response.imgSpecialistLicense));

            $('#imgDegreeCertificate').text(response.imgDegreeCertificate);
            $('#imgDegreeCertificate').attr('data-img', getProxy(response.imgDegreeCertificate));

            $('#imgDesignAssets').text(response.imgDesignAssets);
            $('#imgDesignAssets').attr('data-img', getProxy(response.imgDesignAssets));

            $('#imgEtcFiles').text(response.imgEtcFiles);
            $('#imgEtcFiles').attr('data-img', getProxy(response.imgEtcFiles));

            $('#imgEtc').text(response.imgEtc);
            $('#imgEtc').attr('data-img', getProxy(response.imgEtc));

            $('#Registration').attr('data-img', response.siteKey);

            
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
