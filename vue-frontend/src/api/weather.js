import {instance} from './index';

// 날씨 API
export function get7daysWeather(lat, lon) { // Todo: ${받은apikey값} 전체를 api key 값으로 변경해주세요 ex:) avxkjalkglajkdhlkjhrg2873987529
    return instance.get(`https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&exclude=minutely,hourly,alerts&appid=${받은apikey값}&units=metric&lang=kr`);
}

export let weatherData = '';

function saveCoords(coordsObj) {
    localStorage.setItem('coords', JSON.stringify(coordsObj));
}

function handleGeoSuccess(position) {
    const latitude = position.coords.latitude;  // 경도
    const longitude = position.coords.longitude;  // 위도
    const coordsObj = {latitude, longitude};
    saveCoords(coordsObj);
}

function handleGeoError(err) {
    alert("위치를 찾을 수 없습니다.");
}

export async function loadCoords() {
    const loadedCoords = localStorage.getItem('coords'); // localStorage에서 위치정보 가져옴
    if (loadedCoords === null) { // 위치 정보가 없으면
        navigator.geolocation.getCurrentPosition(handleGeoSuccess, handleGeoError); // 위치 정보 요청 함수
    } else {
        const parseCoords = JSON.parse(loadedCoords); // json형식을 객체 타입으로 바꿔서 저장
        const {data} = await get7daysWeather(parseCoords.latitude, parseCoords.longitude)
        weatherData = data;
    }
}

//행정구역 검색
export function searchLo(keyword) {
    return instance.get(`/api/weather/location?keyword=${keyword}`);
}

//사용자 위치 저장
export function saveLo(userData) {
    return instance.post('/api/weather/savelocation', userData);
}

//사용자 위치 삭제
export function deleteLo(num) {
    return instance.delete(`/api/weather/deletelocation/${num}`);
}

//사용자 저장 위치 조회
export function userLo(userid) {
    return instance.get(`/api/weather/userlocation?userid=${userid}`);
}

//사용자 저장 위치 수 조회
export function countLo(userid) {
    return instance.get(`/api/weather/countlocation?userid=${userid}`);
}

//날씨-코디 예측 값 요청
export function api_wwData(Data) {
    return instance.post(`http://54.180.151.43:5000/recommend`, Data);
}