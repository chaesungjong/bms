/*
 * csf/medical_schedule.html 연동 스크립트 영역
 */
// 오늘 날짜
window.onload = function () {
    today = new Date();
    today = today.toISOString().slice(0, 10);
    bir = document.getElementById('today_date');
    bir.value = today;
};

// 전체 체크
$(document).ready(function () {
    $('#check_all').click(function () {
        if ($('#check_all').is(':checked')) $('input[name=chk]').prop('checked', true);
        else $('input[name=chk]').prop('checked', false);
    });

    $('input[name=chk]').click(function () {
        var total = $('input[name=chk]').length;
        var checked = $('input[name=chk]:checked').length;

        if (total != checked) $('#check_all').prop('checked', false);
        else $('#check_all').prop('checked', true);
    });
});

// 내부 컨텐츠

$('.ch_box').html(`<input type="checkbox" name="chk">`);

$('.con_box').html(
    `<span class="green_day">1일 2일 3일 정상진료</span>
            <small></small>
            <span class="red_day">6일 15일 휴진</span>
            <small></small>
            <span class="night_day">5일 12일 19일 26일 야간진료</span>`
);

$('.con_box_n').html(`2일 오전휴진이지만 아닌듯하여 오전출근 할까말까 고민했지만 어쩌거 저쩌고`);

$('.issue_box').text('눈에 잘띄게 제작 ※ 12월부터 수요일 점심휴게없이');

$('.confirm_box').html(
    `<input type="button" value="접수" style="border: 1px solid #DDDDDD; color: #333;" class="show_btn">
            <div class="btn_popup">
                <input type="button" value="접수" style="background-color: #fff; color: #333; border: 1px solid #DDDDDD;">
                <input type="button" value="확인" style="background-color: #3E3C3C;">
                <input type="button" value="진행" style="background-color: #39AA4C;">
                <input type="button" value="완료" style="background-color: #2D4BB6;">
                <input type="button" value="컨펌" style="background-color: #E24545;">
                <input type="button" value="팝업" style="background-color: #844A29;">
            </div>`
);

// 진행상황 버튼

$('.show_btn').click(function () {
    $(this).siblings().toggle();
});

$('.btn_popup input').click(function () {
    // var btnStyle = $(this).attr("style");
    // var btnTitle = $(this).val();

    // console.log($(".show_btn").value);

    $(this).parent().hide();
});

if ($('.schedule_main_wrap tr').hasClass('blue_on') == true) {
    $('.blue_on').css({ background: '#ECF5FE' });
    $('.blue_on .place_box, .blue_on .confirm_box').css({ background: '#fff' });
}

if ($('.schedule_main_wrap tr').hasClass('red_on') == true) {
    $('.red_on').css({ background: '#F9EFEF' });
    $('.red_on .place_box, .red_on .confirm_box').css({ background: '#fff' });
}

//팝업

$('.schedule_board .blog').click(function () {
    $('.blog_popup').show();
});

$('.schedule_board .schedule').click(function () {
    $('.schedule_popup').show();
});

$('.popup .btn_wrap .exit_btn').click(function () {
    $('.popup_wrap').hide();
});
