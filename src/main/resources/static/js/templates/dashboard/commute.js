document.addEventListener('DOMContentLoaded', () => {
    const dropZone = document.getElementById('dropZone');
    const imageContainer = document.getElementById('imageContainer');

    // Add event listener for paste event
    dropZone.addEventListener('paste', (event) => {
        const items = (event.clipboardData || event.originalEvent.clipboardData).items;
        for (const item of items) {
            if (item.kind === 'file' && item.type.startsWith('image/')) {
                const blob = item.getAsFile();
                const reader = new FileReader();
                reader.onload = function (event) {
                    const img = document.createElement('img');
                    img.src = event.target.result;
                    img.style.maxWidth = '100%';
                    img.style.maxHeight = '100%';
                    imageContainer.innerHTML = ''; // Clear any existing content
                    imageContainer.appendChild(img);
                };
                reader.readAsDataURL(blob);
            }
        }
    });

    // Optional: Prevent default drag behavior
    dropZone.addEventListener('dragover', (event) => {
        event.preventDefault();
    });

    dropZone.addEventListener('drop', (event) => {
        event.preventDefault();
    });
});