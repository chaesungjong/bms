/*
 * csf/business_management.html 연동 스크립트 영역
 */
$(document).ready(function () {


    // 직원 목록 테이블 생성
    var table = $('#employeeTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/csf/customer_list",
            "type": "POST",
            "data": function(d) {
                // 검색 조건을 데이터에 추가
                d.fr_date = $('#fr_date').val();
                d.to_date = $('#to_date').val();
                d.searchType = $('#searchType').val();
                d.search = $('#sch_word').val();
            }
        },
        "columns": [
            { "data": "regdate", "className": "bm_wr_wdate" },
            {
                "data": null,
                "className": "bm_wr_bname",
                "render": function(data, type, row) {
                    return '<a href="#" class="bm_info">' + row.siteName + '</a>'; 
                }
            },
            { "data": "regUserkey", "className": "bm_wr_name" },
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
                    return '<span class="bm_cont_ing">' + row.strSiteStateLevel + '</span>'; 
                }
            },
            {
                "data": null,
                "className": "bm_wr_cdate",
                "render": function(data, type, row) {
                    // contractSdate와 contractEdate 데이터를 합쳐서 반환
                    return row.contractSdate + " ~ " + row.contractEdate;
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


    $('.bm_info').click(function () {
        var userBtn = $(this).attr('id');
        $('.bm_info_wrap').removeClass('on');
        $('.bm_info_wrap').removeClass('out');
        $('.bm_info_wrap#' + userBtn).addClass('on');
        $('body, html').addClass('modal-active');
    });
    
    $('.bm_info_close').click(function () {
        $('.bm_info_wrap').addClass('out');
        $('body, html').removeClass('modal-active');
    });

    // 직원 상세 페이지 팝업 띄우기
    $('body').on('click', 'tr', function() {
        var searchVal = $(this).attr('data-sitekey');
        showProfile(searchVal);
    });

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
    
});


function showProfile(searchval) {

    var data = {
        searchVal : searchval
    };

    ajaxRequest("/csf/customer_detail", data, "POST", function (response) {

        if (response.retVal == 0) {
            
            $('#siteName').text(response.siteName);
            $('#strSiteStateLevel').text(response.strSiteStateLevel);
            $('#contractSdate').text(response.contractSdate);
            $('#contractEdate').text(response.contractEdate);
            $('#strContractState').text(response.strContractState);
            $('#strSiteOpenState').text(response.strSiteOpenState);
            $('#transaction').text(response.transaction);
            $('#siteURL').text(response.siteURL);
            $('#siteDomainExpdt').text(response.siteDomainExpdt);
            $('#siteHostingExpdt').text(response.siteHostingExpdt);
            $('#adress').text(response.adress);
            $('#memo').text(response.memo);
            $('#ceoName').text(response.ceoName);
            $('#managerPhone').text(response.managerPhone);
            $('#managerEmail').text(response.managerEmail);

            $('#imgBusinessRegNo').text(response.imgBusinessRegNo);
            $('#imgBusinessRegNo').attr('data-img', "/proxy/" + response.imgBusinessRegNo);
            $('#imgOpenCertificate').text(response.imgOpenCertificate);
            $('#imgOpenCertificate').attr('data-img', "/proxy/" + response.imgOpenCertificate);
            $('#imgDoctorLicense').text(response.imgDoctorLicense);
            $('#imgDoctorLicense').attr('data-img', "/proxy/" + response.imgDoctorLicense);
            $('#imgSpecialistLicense').text(response.imgSpecialistLicense);
            $('#imgSpecialistLicense').attr('data-img', "/proxy/" + response.imgSpecialistLicense);
            $('#imgDegreeCertificate').text(response.imgDegreeCertificate);
            $('#imgDegreeCertificate').attr('data-img', "/proxy/" + response.imgDegreeCertificate);
            $('#imgEtcFiles').text(response.imgEtcFiles);
            $('#imgEtcFiles').attr('data-img', "/proxy/" + response.imgEtcFiles);
            $('#imgEtc').text(response.imgEtc);
            $('#imgEtc').attr('data-img', "/proxy/" + response.imgEtc);

            $('#Registration').attr('href', "/csf/add_business_management?searchVal=" + response.siteKey);

            
            setSnsInformation(response.snsList);

            $('.bm_info_wrap').removeClass('on');
            $('.bm_info_wrap').removeClass('out');
            $('.bm_info_wrap' ).addClass('on');
            $('body, html').addClass('modal-active');


        } else {
            alert("다시 시도해 주세요.");
        }
    }, function () {
        alert('현재 기능 개발 준비중 입니다.');
    });

}

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

function downloadFile(id) {
    // 클릭된 a 태그의 href 속성 값을 가져옴
    var fileUrl = $('#' + id).attr('data-img');

    // 임시 a 태그 생성
    var $tempLink = $('<a>');
    $tempLink.attr('href', fileUrl);
    
    // 파일 다운로드 설정
    $tempLink.attr('download', fileUrl.substring(fileUrl.lastIndexOf('/') + 1));

    // 페이지에 추가 후 클릭하여 다운로드 실행
    $('body').append($tempLink);
    $tempLink[0].click();

    // 임시로 만든 링크 제거
    $tempLink.remove();
}