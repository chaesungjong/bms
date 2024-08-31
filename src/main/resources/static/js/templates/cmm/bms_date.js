// 현재 날짜와 시간을 포맷팅하는 함수
function getFormattedDate(date = new Date(), format = 'YYYY-MM-DD HH:mm:ss') {
    const pad = (n) => n < 10 ? '0' + n : n;

    const year = date.getFullYear();
    const month = pad(date.getMonth() + 1);
    const day = pad(date.getDate());
    const hours = pad(date.getHours());
    const minutes = pad(date.getMinutes());
    const seconds = pad(date.getSeconds());

    return format.replace('YYYY', year)
                 .replace('MM', month)
                 .replace('DD', day)
                 .replace('HH', hours)
                 .replace('mm', minutes)
                 .replace('ss', seconds);
}

// 날짜 차이 계산 함수 (일 단위)
function dateDiffInDays(date1, date2) {
    const oneDay = 24 * 60 * 60 * 1000; // milliseconds in one day
    const diffInTime = date2.getTime() - date1.getTime();
    return Math.round(diffInTime / oneDay);
}

// 특정 날짜에 일/월/년을 더하거나 빼는 함수
function addDays(date, days) {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

// 특정 날짜에 월을 더하거나 빼는 함수
function addMonths(date, months) {
    const result = new Date(date);
    result.setMonth(result.getMonth() + months);
    return result;
}

// 특정 날짜에 년을 더하거나 빼는 함수
function addYears(date, years) {
    const result = new Date(date);
    result.setFullYear(result.getFullYear() + years);
    return result;
}

// 날짜가 유효한지 검사하는 함수
function isValidDate(dateString) {
    const date = new Date(dateString);
    return !isNaN(date.getTime());
}

// 특정 날짜가 주말인지 평일인지 확인하는 함수
function isWeekend(date) {
    const day = date.getDay();
    return day === 0 || day === 6;
}

// 두 날짜가 동일한지 확인하는 함수
function areDatesEqual(date1, date2) {
    return date1.getFullYear() === date2.getFullYear() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getDate() === date2.getDate();
}

function formatDate(dateInput) {

    // 입력 값이 8자리인 경우에만 변환 시도
    if (dateInput.length === 8) {
        const formattedDate = dateInput.substring(0, 4) + '-' + dateInput.substring(4, 6) + '-' + dateInput.substring(6, 8);
        return formattedDate;
    }
}