$(document).ready(function () {

  // 저장 버튼 클릭 핸들러
  $('#save').on('click', function () {
    // 데이터 수집
    var contents = $('#contents').val();
    var boardSeq = $('#boardSeq').val();
    var imgPaths = {};
    var imgPathSeqs = {};
    var currentDomain = window.location.origin;

    // 이미지 경로와 시퀀스 수집
    for (var i = 0; i <= 10; i++) {
      var imgPath = $('#imgPath_' + i).attr('src');
      if (imgPath) {
          imgPaths['imgPath_' + i] = imgPath.replace(currentDomain, '');
      }

      var imgPathSeq = $('#imgPath_seq_' + i).val();
      if (imgPathSeq) {
          imgPathSeqs['imgPath_seq_' + i] = imgPathSeq;
      }
    }

    // 데이터 객체 생성
    var data = {
      contents: contents,
      boardSeq: boardSeq,
      ...imgPaths, // imgPath 데이터를 객체에 추가
      ...imgPathSeqs // imgPathSeq 데이터를 객체에 추가
    };

    // AJAX 요청
    ajaxRequest( '/setting/naverPlaceRegist.do', data, 'POST',
      function (response) {

        if(response.retVal == 0){
          alert('저장되었습니다.');
        }else{
          alert(response.retMsg);
        }
        location.href = '/setting/board?etcParam=NAV_PL';
      },
      function (xhr, status, error) {
        console.error('저장 중 오류 발생:', error);
      }
    );
  });

  // PDF 저장 버튼 클릭 핸들러
  $('#pdf_save').on('click', function () {

    html2canvas($('.flex-column')[0], {
        scale: 2,
        useCORS: true,
        allowTaint: false
    }).then(function (canvas) {
        var imgData = canvas.toDataURL('image/png');
        const { jsPDF } = window.jspdf;
        var pdf = new jsPDF('p', 'mm', 'a4');
        var imgWidth = 210;
        var pageHeight = 297;
        var imgHeight = canvas.height * imgWidth / canvas.width;
        var heightLeft = imgHeight;
        var position = 0;

        pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        while (heightLeft >= 0) {
            position = heightLeft - imgHeight;
            pdf.addPage();
            pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;
        }

        pdf.save('content.pdf');
    });

  });

  // JPG 저장 버튼 클릭 핸들러
  $('#jpg_save').on('click', function () {
    html2canvas($('.flex-column')[0], {
      scale: 2, // 해상도를 높이기 위해 스케일 설정
      useCORS: true, // 외부 이미지 로드를 허용하기 위해 CORS 사용
      proxy: 'YOUR_PROXY_URL' // 프록시 URL 설정
    }).then(function (canvas) {
      var imgData = canvas.toDataURL('image/jpeg'); // 캔버스를 JPG 데이터로 변환
      var link = document.createElement('a');
      link.href = imgData;
      link.download = 'content.jpg'; // JPG 파일 다운로드
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  });

  // 이미지 클릭 핸들러, 파일 입력 대화상자 열기
  $('[id^=imgPath_]').on('click', function () {
    var id = "file_" + $(this).attr('id');
    $('#' + id).click();
  });

  // 파일 입력 변경 핸들러, 이미지 업로드
  $('[id^=file_imgPath]').on('change', function (event) {
    var inputId = $(this).attr('id');
    var imgId = inputId.replace('file_', '');
    var file = event.target.files[0];

    if (file) {
      uploadFile(file, function (data) {
        $('#' + imgId).attr('src', data);
      });
    }
  });

});
