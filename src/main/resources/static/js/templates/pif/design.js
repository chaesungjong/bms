/*
 * pif/design.html 연동 스크립트 영역
 */
$(document).ready(function () {
    $('.design_board_type>span').click(function () {
        $('.design_board_type>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_state>span').click(function () {
        $('.main_board_state>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.design_board_category>span').click(function () {
        $('.design_board_category>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_wrstate').click(function () {
        $(this).children('.state_select').slideToggle(100);
    });
    $('.main_board_wrcategory').click(function () {
        $(this).children('.category_select').slideToggle(100);
    });
});
