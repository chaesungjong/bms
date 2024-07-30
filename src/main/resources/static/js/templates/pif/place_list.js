/*
 * pif/place_list.html 연동 스크립트 영역
 */
// 진행상황 버튼
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

$('.show_btn').click(function () {
    $(this).siblings().toggle();
});

$('.place_list_main_wrap td.confirm_box .btn_popup input').click(function () {
    $(this).parent().hide();
});

// 내용 첨부
$('.planner_box').html('김선영');
$('.designer_box').html('이수은');
$('.date_box').html('2024.05.20 ~ 2024.06.20');

// 버튼 활성화
$('.place_list_sub_wrap .btn_wrap li').click(function () {
    $(this).addClass('click_on').siblings().removeClass('click_on');
});

$(document).on('click', '.page_btn_wrap .btn_wrap li', function () {
    $(this).addClass('click_on').siblings().removeClass('click_on');
});

// 팝업
$('.guide_btn').click(function () {
    $('.popup_wrap').show();
});

$('.exit_btn').click(function () {
    $('.popup_wrap').hide();
});
