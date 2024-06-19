document.getElementById('captureBtn').addEventListener('click', function () {

    html2canvas(document.body).then(function (canvas) {
        var imgData = canvas.toDataURL('image/jpeg');
        var link = document.getElementById('downloadLink');
        link.href = imgData;
        link.download = 'screenshot.jpg';
        link.style.display = 'block';

        var pdf = new jspdf.jsPDF('p', 'mm', 'a4');
        var imgWidth = 210;
        var pageHeight = 295;
        var imgHeight = canvas.height * imgWidth / canvas.width;
        var heightLeft = imgHeight;
        var position = 0;

        pdf.addImage(imgData, 'JPEG', 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        while (heightLeft >= 0) {
            position = heightLeft - imgHeight;
            pdf.addPage();
            pdf.addImage(imgData, 'JPEG', 0, position, imgWidth, imgHeight);
            heightLeft -= pageHeight;
        }
        pdf.save('screenshot.pdf');
    }).catch(function (error) {
        console.error('Error capturing the screen:', error);
    });

    $('#downloadLink').show();
});

const dropZone = document.getElementById('dropZone');

// 드래그 오버 이벤트 리스너 추가
dropZone.addEventListener('dragover', (e) => {
    e.preventDefault();
    dropZone.style.border = '2px solid #000';
});

// 드래그가 드롭 영역을 떠날 때 이벤트 리스너 추가
dropZone.addEventListener('dragleave', () => {
    dropZone.style.border = '2px dashed #ccc';
});

// 드롭 이벤트 리스너 추가
dropZone.addEventListener('drop', (e) => {
    e.preventDefault();
    dropZone.style.border = '2px dashed #ccc';
    const files = e.dataTransfer.files;
    if (files.length > 0) {
        const file = files[0];
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = (event) => {
                const img = document.createElement('img');
                img.src = event.target.result;
                dropZone.innerHTML = '';  // 이전 내용 지우기
                dropZone.appendChild(img);
            };
            reader.readAsDataURL(file);
        } else {
            alert('Please drop an image file.');
        }
    }
});