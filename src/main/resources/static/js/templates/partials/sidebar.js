$(document).ready(function () {

    $('.side_menu > li > a').click(function (e) {
        e.preventDefault();
        $(this).toggleClass('open');
        $('.side_menu > li > a').not(this).removeClass('open');
        var subMenu = $(this).siblings('.side_sub_menu');
        $('.side_sub_menu').not(subMenu).slideUp();
        subMenu.slideToggle();
    });

    // a 태그 클릭 이벤트를 바인딩합니다.
    $('a[data-ajax]').click(function (event) {
        event.preventDefault();
        var url = $(this).attr('href');
        loadContent(url);
    });
    $('.user_info_btn').click(function () {
        var userBtn = $(this).attr('id');
        $('.member_info_wrap').removeClass('on');
        $('.member_info_wrap').removeClass('out');
        $('.member_info_wrap#' + userBtn).addClass('on');
        $('body, html').addClass('modal-active');
    });

    $('.member_info_close').click(function () {
        $('.member_info_wrap').addClass('out');
        $('body, html').removeClass('modal-active');
    });

    $('#member_logout').click(function () {
        // ajaxRequest 함수를 사용하여 로그아웃 처리
        ajaxRequest("/acm/logout.do", '', "POST", function (response) {
            // 로그인 성공 시 대시보드 페이지로 리디렉트
            location.href = "/acm/login";
        }, function () {
            alert('현재 기능 개발 준비중 입니다.');
        });
    });

    // 현재 URL 가져오기
    var currentUrl = window.location.pathname; 

    // 모든 메뉴 항목에서 "active" 클래스 제거
    $('.side_menu a').removeClass('active');

    // URL에서 첫 번째 경로 부분 추출 (path1)
    var path1 = currentUrl.split('/')[1]; 

    // 해당 id를 가진 메뉴 항목 찾기
    var targetMenuItem = $('#side_' + path1);

    // 찾은 메뉴 항목 클릭 이벤트 트리거
    targetMenuItem.trigger('click');

});
