/*
 * dsb/main.html 연동 스크립트 영역
 */
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
    });
    calendar.render();

    // 텍스트를 변경하는 코드 추가
    var daysOfWeek = [
        { class: 'fc-day-sun', text: '일' },
        { class: 'fc-day-mon', text: '월' },
        { class: 'fc-day-tue', text: '화' },
        { class: 'fc-day-wed', text: '수' },
        { class: 'fc-day-thu', text: '목' },
        { class: 'fc-day-fri', text: '금' },
        { class: 'fc-day-sat', text: '토' },
    ];

    daysOfWeek.forEach(function (day) {
        var dayElements = document.querySelectorAll('.fc-scrollgrid-section-header .' + day.class);
        dayElements.forEach(function (element) {
            element.innerText = day.text; // 원하는 텍스트로 변경
        });
    });
});
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
    $('.main_side_alert').click(function () {
        $('.main_side_alert_box').slideToggle(300);
    });
});

// 오늘 날짜 가져오기
var today = new Date();

// 날짜 형식 지정 (예: 2024. 06. 20 (목))
var year = today.getFullYear();
var month = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1
var date = ('0' + today.getDate()).slice(-2);
var day = ['일', '월', '화', '수', '목', '금', '토'][today.getDay()];

var formattedDate = year + '. ' + month + '. ' + date + ' (' + day + ')';

// 텍스트를 #today 요소에 설정
document.getElementById('today').textContent = formattedDate;
