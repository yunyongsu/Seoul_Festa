$(document).ready(function() {
    // 페이지 로드 시 날씨 정보 가져오기
    getWeather();

    // 날씨 정보 가져오는 함수
    function getWeather() {
        // OpenWeatherMap API 키
        var apiKey = '865f9d1023a26cbdcee5f9140118b167';
        // 도시 이름
        var city = 'Seoul'; // 현재는 서울을 기준으로 예시로 했습니다.

        // API 요청 URL
        var apiUrl = 'https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=865f9d1023a26cbdcee5f9140118b167&units=metric';

        // API 요청 보내기
        $.ajax({
            url: apiUrl,
            type: 'GET',
            success: function(data) {
                // 날씨 정보를 가져와서 페이지에 표시
                var temperature = data.main.temp;
                var iconCode = data.weather[0].icon; // 아이콘 코드 가져오기
                var iconUrl = 'http://openweathermap.org/img/wn/' + iconCode + '.png'; // 아이콘 URL 생성
                var weatherHtml = '<img src="' + iconUrl + '" alt="Weather Icon">' + temperature + '℃';
                $('#weather').html(weatherHtml);
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
                $('#weather').html("날씨 정보를 가져오지 못했습니다.");
            }
        });
    }
});