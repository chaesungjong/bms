/*
 * /enterprise/csf/maintenance.html 연동 스크립트 영역
 */
$(document).ready(function () {
    dataTableReload();
});


 // 직원 목록 테이블 생성
 function dataTableReload() {

    var table = $('#maintenanceTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/enterprise/csf/maintenance_list",
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
            { "data": null, 
                "className": "mt_wr_wdate",
                "render": function(data, type, row) {
                return formatDate(row.reqSDate);
                } 
            }, // 업무 요청일
            { "data": "name" },//작성자
            { "data": null, 
                "className": "mt_wr_state",
                "render": function(data, type, row) {
                    if(row.strTaskState =="접수"){
                        return '<span class="main_board_wrstate state_receive" style="cursor: default">' + row.strTaskState + '</span>'; 
                    }else if(row.strTaskState =="미처리"){
                        return '<span class="main_board_wrcategory cat_no" style="cursor: default">' + row.strTaskState + '</span>'; 

                    }else if(row.strTaskState =="보류"){
                        return '<span class="main_board_wrstate state_hold" style="cursor: default">' + row.strTaskState + '</span>'; 
                    }else if(row.strTaskState =="진행"){
                        return '<span class="main_board_wrstate state_progress" style="cursor: default">' + row.strTaskState + '</span>'; 
                    }else{
                        return '<span class="main_board_wrcategory cat_no" style="cursor: default">' + row.strTaskState + '</span>'; 
                    }
                } 
            },// 상태
            { "data": null, 
                "className": "mt_wr_state",
                "render": function(data, type, row) {
                    if(row.strGdTeamCode =="광고관리팀"){
                        return '<span class="main_board_wrcategory cat_mk" style="cursor: default">' +row.strGdTeamCode+'</span>';
                    }else if(row.strTaskState =="미처리"){
                        return '<span class="main_board_wrcategory cat_mk" style="cursor: default">' +row.strGdTeamCode+'</span>';

                    }else if(row.strTaskState =="보류"){
                        return '<span class="main_board_wrcategory cat_mk" style="cursor: default">' +row.strGdTeamCode+'</span>';
                    }else if(row.strTaskState =="진행"){
                        return '<span class="main_board_wrcategory cat_mk" style="cursor: default">' +row.strGdTeamCode+'</span>';
                    }else{
                        return '<span class="main_board_wrcategory cat_mk" style="cursor: default">' +row.strGdTeamCode+'</span>';
                    }
                } 
            },// 부서
            { "data": "title" },        //제목
            { "data": null, 
                "className": "mt_wr_wdate",
                "render": function(data, type, row) {
                    if(!isNaN(row.dueDate)){
                        return formatDate(row.dueDate);
                    }else{
                        return "";
                    }
                } 
            }, // 필요 일자
            { "data": null, 
                "className": "mt_wr_wdate",
                "render": function(data, type, row) {
                    if(!isNaN(row.reqEDate)){
                        return formatDate(row.reqEDate);
                    }else{
                        return "";
                    }
                } 
            } // 필요 일자
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
            $(row).attr('data-user-id', data.userid);
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