$(document).ready(function() {
    /***** 변수 선언 *****/
  
    // 그림 그리기 상태 변수
    var pos = {
      drawable: false, // 그리기 가능 여부
      x: -1, // 마지막 마우스 X 좌표
      y: -1 // 마지막 마우스 Y 좌표
    };
  
    // 캔버스 히스토리 저장 배열
    var canvasHistory = [];
  
    /***** 캔버스 설정 *****/
  
    // 캔버스 객체 생성 (Fabric.js)
    var canvas = new fabric.Canvas('canvas');
  
    // 브러쉬 초기 설정
    canvas.isDrawingMode = true; // 캔버스를 그리기 모드로 설정
    canvas.freeDrawingBrush.width = $('#input').val(); // 브러쉬 크기 설정
    canvas.freeDrawingBrush.color = 'black'; // 브러쉬 색상 설정
  
  
    /***** 이벤트 핸들러 *****/
  
    // 전체 지우기 버튼 클릭 이벤트 핸들러
    $('#clearButton').click(function() {
      canvas.clear(); // 캔버스 전체 영역 지우기
      canvasHistory = []; // 히스토리 초기화
    });
  
    // 선 굵기 변경 이벤트 핸들러
    $('#input').on('input', function() {
      canvas.freeDrawingBrush.width = $(this).val(); // 입력 값으로 브러쉬 크기 설정
    });
  
    // 색상 변경 버튼 클릭 이벤트 핸들러
    $('.colorButton').click(function() {
      canvas.isDrawingMode = true; // 그리기 모드 활성화
      canvas.freeDrawingBrush.color = $(this).css('background-color'); // 버튼의 배경색을 브러쉬 색상으로 설정
    });
  
    // 텍스트 추가 버튼 클릭 이벤트 핸들러
    $('#addTextButton').click(function() {
      canvas.isDrawingMode = false; // 브러쉬 모드 비활성화
  
      // 텍스트 입력 창 생성
      var text = new fabric.Textbox('텍스트를 입력하세요', {
        left: 50,
        top: 50,
        width: 200,
        fontSize: 20,
        fontFamily: 'Arial',
        fill: 'black',
        hasControls: true, // 텍스트 크기 조절 컨트롤 추가
        borderColor: 'black', // 텍스트 크기 조절 핸들 색상
        cornerColor: 'blue', // 텍스트 크기 조절 핸들 색상
        cornerSize: 10 // 텍스트 크기 조절 핸들 크기
      });
  
      // 캔버스에 텍스트 입력 창 추가
      canvas.add(text);
      canvas.setActiveObject(text);
      text.enterEditing(); // 텍스트 입력 창 활성화
      canvas.requestRenderAll(); // 캔버스 다시 그리기
    });
  
    // 이미지 업로드 이벤트 핸들러
    $('#imageUpload').change(function(e) {
      var file = e.target.files[0]; // 선택한 파일 가져오기
      var reader = new FileReader(); // 파일 읽기 객체 생성
  
      reader.onload = function(event) { // 파일 읽기 완료 시
        var img = new Image();
        img.onload = function() {
          var image = new fabric.Image(img); // Fabric.js 이미지 객체 생성
          canvas.add(image); // 이미지를 캔버스에 추가
          image.set({
            left: 0,
            top: 0,
            width: canvas.width,
            height: canvas.height
          });
          canvas.requestRenderAll(); // 캔버스 다시 그리기
        }
        img.src = event.target.result; // 이미지 소스 설정
      }
      reader.readAsDataURL(file); // 파일을 데이터 URL로 읽기
    });
  
    // 저장 버튼 클릭 이벤트 핸들러
    $('#saveButton').click(function() {
      var image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream"); // 이미지 데이터 URL 생성
      var link = document.createElement('a'); // 링크 요소 생성
      link.download = '테스트.png'; // 다운로드 파일 이름 설정
      link.href = image; // 링크에 이미지 데이터 URL 설정
      link.click(); // 링크 클릭하여 다운로드 시작
    });
  
    // 화살표 추가 버튼 클릭 이벤트 핸들러
    $('#addArrowButton').click(function() {
      canvas.isDrawingMode = false; // 브러쉬 모드 비활성화
  
      var arrow = new fabric.Path('M 0 0 L 100 0 M 90 10 L 100 0 L 90 -10', {
        left: 100,
        top: 100,
        fill: 'black',
        stroke: 'black',
        strokeWidth: 2,
        selectable: true, // 화살표 선택 가능 여부
        hasControls: true, // 화살표 크기 조절 핸들 표시 여부
        borderColor: 'black', // 화살표 크기 조절 핸들 색상
        cornerColor: 'blue', // 화살표 크기 조절 핸들 색상
        cornerSize: 10, // 화살표 크기 조절 핸들 크기
        originX: 'center', // 회전 중심점 설정
        originY: 'center' // 회전 중심점 설정
      });
      canvas.add(arrow);
      canvas.setActiveObject(arrow);
      canvas.requestRenderAll();
    });
  
    // 뒤로 버튼 클릭 이벤트 핸들러
    $('#backButton').click(function() {
      restoreCanvasState();
    });
  
  
    /***** 캔버스 히스토리 관리 함수 *****/
  
    // 캔버스 상태 저장 함수
    function saveCanvasState() {
      canvasHistory.push(JSON.stringify(canvas.toJSON()));
    }
  
    // 캔버스 상태 복원 함수
    function restoreCanvasState() {
      if (canvasHistory.length > 1) {
        canvas.clear();
        canvasHistory.pop(); // 현재 상태 삭제
        canvas.loadFromJSON(JSON.parse(canvasHistory[canvasHistory.length - 1]), function() {
          canvas.requestRenderAll();
        });
      }
    }
  
    // 캔버스에 객체가 추가될 때 히스토리 저장
    canvas.on('object:added', function() {
      saveCanvasState();
    });
  
    // 캔버스에 객체가 수정될 때 히스토리 저장
    canvas.on('object:modified', function() {
      saveCanvasState();
    });
  
    document.addEventListener('keydown', function(e) {
      if (e.key === 'Backspace' || e.key === 'Delete') {
        // 활성화된 객체(선택된 객체) 가져오기
        var activeObject = canvas.getActiveObject();
  
        // 활성화된 객체가 있다면 삭제
        if (activeObject) {
          canvas.remove(activeObject);
          canvas.requestRenderAll();
        }
      }
    });
  });