$(document).ready(function () {
    var $modalBg = $('.modal_bg');
    var $hdSearchModal = $('.hd_search_modal');
    var $hdResult = $('.hd_result');
    var $hdTop = $('.hd_top');

    function toggleModal(open) {
        $modalBg.toggleClass('open', open);
        $hdSearchModal.toggleClass('open', open);
    }

    $('.hd_search_btn').click(function () {
        toggleModal(true);
    });

    $modalBg.add('.search_modal_close').click(function () {
        toggleModal(false);
    });

    $('.hd_search_list a').click(function () {
        toggleModal(false);
        $hdResult.addClass('open');
        $hdTop.css('justify-content', 'space-between');
    });

    $('.hd_result_btn').click(function () {
        $(this).toggleClass('open');
        $('.hd_ext').toggleClass('open');
    });

    $('.hd_ext_tgl').click(function () {
        $(this).toggleClass('open');
        $('.hd_ext_info').toggleClass('open');
    });
});