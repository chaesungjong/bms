$(document).ready(function () {
  $('.side_menu > li > a').click(function (e) {
      e.preventDefault();
      $(this).toggleClass('open');
      $('.side_menu > li > a').not(this).removeClass('open');
      var subMenu = $(this).siblings('.side_sub_menu');
      $('.side_sub_menu').not(subMenu).slideUp();
      subMenu.slideToggle();
  });
});