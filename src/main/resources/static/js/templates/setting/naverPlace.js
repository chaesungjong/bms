$(document).ready(function () {
  if ($.fn.dataTable.isDataTable('#dataTable')) {
    $('#dataTable').DataTable().destroy();
  }

  $('#dataTable').DataTable({
    "ajax": {
      "url": "/setting/dataTable.json",
      "type": "POST", // GET 메서드를 사용하려면 이 줄을 생략하거나 "GET"으로 변경하세요.
      "data": function(d) {
          d.etcParam = "NAV_PL"; // etcParam 값을 추가
      }
    },
    "columns": [
      { "data": "RowNum" },  // "번호"로 매핑
      { "data": "title" },   // "제목"으로 매핑
      { "data": "name" },    // "작성자"로 매핑
      { "data": "regdate" }, // "작성일"로 매핑
      {
          "data": "seq",    // "조회"로 매핑
          "render": function(data, type, row) {
              return '<a href="/setting/naverForm?seq=' + data + '" class="btn btn-secondary btn-icon-split">' +
                  '<span class="icon text-white-50"><i class="fas fa-arrow-right"></i></span>' +
                  '<span class="text">수정</span>' +
                  '</a>';
          }
      }
  ]
  });
});

