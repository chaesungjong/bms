/*
 * csf/maintenance.html 연동 스크립트 영역
 */
$(document).ready(function () {
    $('.main_board_state>span').click(function () {
        $('.main_board_state>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_category>span').click(function () {
        $('.main_board_category>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_wrstate').click(function () {
        $(this).children('.state_select').slideToggle(100);
    });
    $('.main_board_wrcategory').click(function () {
        $(this).children('.category_select').slideToggle(100);
    });
});


 // 업체 목록 테이블 생성
 function dataTableReload() {

    var table = $('#employeeTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/admin/ems/employee_list",
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
                "className": "bm_wr_wdate",
                "render": function(data, type, row) {
                return formatDate(row.jobStartDate);
            } },
            { "data": "name" },
            { "data": "departCode" },
            { "data": "teamCode" },
            { "data": null, 
                "className": "bm_wr_wdate",
                "render": function(data, type, row) {
                return formatDate(row.birthday);
                } 
            },
            { "data": "email" },
            {
                "data": "memo",
                "defaultContent": ""  // 기본값 설정
            },
            { "data": "userid", "visible": false } // 히든 컬럼 추가
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