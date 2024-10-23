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

    $('.hd_ext_count01').click(function () {
        $(this).toggleClass('open');
        $('.hd_ext_info_list').toggleClass('open');
    });
    $('.hd_ext_count04').click(function () {
        var caseBtn = $(this).attr('id');
        $('.posting_modal').removeClass('on');
        $('.posting_modal').removeClass('out');
        $('.posting_modal#' + caseBtn).addClass('on');
        $('body, html').addClass('modal-active');
    });

    $('.posting_modal_close').click(function () {
        $('.posting_modal').addClass('out');
        $('body, html').removeClass('modal-active');
    });
});
