$(document).ready(function () {
    var $modalBg = $('.modal_bg');
    var $hdSearchModal = $('.hd_search_modal');
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
});
