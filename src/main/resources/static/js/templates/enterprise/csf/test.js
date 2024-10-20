/*
 * /enterprise/csf/test.html 연동 스크립트 영역
 */
$(document).ready(function () {
    var imageEditor = new tui.ImageEditor('#tui-image-editor-container', {
        includeUI: {
            loadImage: {
                path: 'https://cdn.pixabay.com/photo/2023/03/06/19/12/mountains-7033345_1280.jpg', // 기본 이미지 경로
                name: 'SampleImage'
            },
            theme: {}, // 기본 테마 사용
            menu: ['crop', 'flip', 'rotate', 'draw', 'shape', 'icon', 'text'], // 메뉴 설정
            initMenu: 'filter', // 초기 메뉴
            uiSize: {
                width: '100%',
                height: '500px'
            },
            menuBarPosition: 'bottom'
        },
        cssMaxWidth: 700,
        cssMaxHeight: 500,
        usageStatistics: false // 통계 정보 비활성화
    });
  
      // jQuery를 통해 버튼을 클릭하면 편집한 이미지 가져오기
      $('#get-image').on('click', function() {
        var dataUrl = imageEditor.toDataURL();
        console.log(dataUrl); // Base64로 변환된 이미지 데이터
      });
});