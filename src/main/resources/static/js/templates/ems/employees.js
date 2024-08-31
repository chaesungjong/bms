$(document).ready(function () {

    // 직원 목록 테이블 생성
    var table = $('#employeeTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/ems/employee_list",
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
    

    // 직원 상세 페이지 팝업 띄우기
    $('body').on('click', 'tr', function() {
        var userId = $(this).data('user-id');
        showProfile(userId);
    });
    
});