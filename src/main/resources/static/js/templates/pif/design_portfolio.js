/*
 * pif/design_portfolio.html 연동 스크립트 영역
 */

// 버튼 변경(내용 변경)
$('.gallery_sub_wrap .homepage, .gallery_sub_wrap .homepage span').click(function () {
    $('.page_btn_wrap .btn_wrap').html(
        `<li class="click_on">
                    <button type="button" class="onepage"><span>원페이지 </span><span class="num">10</span></button>
                </li>
                <li>
                    <button type="button" class="multipage"><span>멀티페이지 </span><span class="num">14</span></button>
                </li>`
    );

    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

$('.gallery_sub_wrap .setting, .gallery_sub_wrap .setting span').click(function () {
    $('.page_btn_wrap .btn_wrap').html(
        `<li class="click_on">
                    <button type="button"><span>플레이스 </span><span class="num">800</span></button>
                </li>
                <li>
                    <button type="button"><span>스킨 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>홍보성 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>톡톡 </span><span class="num">600</span></button>
                </li>`
    );

    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img3.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

$('.gallery_sub_wrap .online, .gallery_sub_wrap .online span').click(function () {
    $('.page_btn_wrap .btn_wrap').html(
        `<li class="click_on">
                    <button type="button"><span>사이니지 </span><span class="num">800</span></button>
                </li>
                <li>
                    <button type="button"><span>이벤트 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>랜딩/상세 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>광고 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>썸네일 </span><span class="num">600</span></button>
                </li>`
    );

    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img4.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

$('.gallery_sub_wrap .offline, .gallery_sub_wrap .offline span').click(function () {
    $('.page_btn_wrap .btn_wrap').html(
        `<li class="click_on">
                    <button type="button"><span>옥외광고 </span><span class="num">800</span></button>
                </li>
                <li>
                    <button type="button"><span>책자/카달로그 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>원내 인쇄물 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>주의사항/동의서 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>포스터/전단 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>명함 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>봉투/홍보물 </span><span class="num">600</span></button>
                </li>
                <li>
                    <button type="button"><span>패키지/상품 </span><span class="num">600</span></button>
                </li>`
    );

    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img5.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

// 내용첨부
$('.gallery_main_wrap ul li').html(
    `<a href="">
                <img src="/img/preview_img.png" alt="">
            </a>
            <div>
                <a href="">
                    <span class="title">서울스마트치과 홈페이지</span>
                    <span class="name">이수은</span>
                </a>
                <a href="">
                    <span class="date">2024.07.12 ~ 2024.12.30</span>
                    <small>(인천송도)</small>
                </a>
            </div>`
);

$(document).on('click', '.page_btn_wrap .btn_wrap .onepage, .page_btn_wrap .btn_wrap .onepage span', function () {
    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

$(document).on('click', '.page_btn_wrap .btn_wrap .multipage, .page_btn_wrap .btn_wrap .multipage span', function () {
    $('.gallery_main_wrap ul li').html(
        `<a href="">
                    <img src="/img/preview_img2.png" alt="">
                </a>
                <div>
                    <a href="">
                        <span class="title">서울스마트치과 홈페이지</span>
                        <span class="name">이수은</span>
                    </a>
                    <a href="">
                        <span class="date">2024.07.12 ~ 2024.12.30</span>
                        <small>(인천송도)</small>
                    </a>
                </div>`
    );
});

//팝업 내용첨부
$('.popup_con .homepage_01 ul li').html(
    `<a href="">
                <span class="sub_title">연세진정한치과</span>
                <span class="link">http://ysjinjunghan.com/</span>
            </a>`
);

$('.popup_con .homepage_02 ul li').html(
    `<a href="">
                <span class="sub_title">서울스마트치과</span>
                <span class="link">http://ysjinjunghan.com/</span>
            </a>`
);

// 버튼 활성화
$('.gallery_sub_wrap .btn_wrap li').click(function () {
    $(this).addClass('click_on').siblings().removeClass('click_on');
});

$(document).on('click', '.page_btn_wrap .btn_wrap li', function () {
    $(this).addClass('click_on').siblings().removeClass('click_on');
});

// 팝업
$('.port_btn').click(function () {
    $('.popup_wrap').show();
});

$('.exit_btn').click(function () {
    $('.popup_wrap').hide();
});
