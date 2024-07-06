import { initializeApp } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-app.js";
import { getStorage ,ref, uploadBytes,getDownloadURL } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-storage.js";

const firebaseConfig = {
    apiKey: "AIzaSyCOZHR_qsXvLMfKMKTySGkPBj_s6HKbrQw",
    authDomain: "groupd-b76a2.firebaseapp.com",
    projectId: "groupd-b76a2",
    storageBucket: "groupd-b76a2.appspot.com",
    messagingSenderId: "41023047023",
    appId: "1:41023047023:web:6fdfa0f8d059d27a81549a",
    measurementId: "G-7XBJRQWK0W"
};

const app = initializeApp(firebaseConfig);
window.storage = getStorage(app);
window.ref = ref;
window.uploadBytes  = uploadBytes; 
window.getDownloadURL  = getDownloadURL; 