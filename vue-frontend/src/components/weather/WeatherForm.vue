<template>
    <div>
        <div id="main-form">
            <div id="todayWeather" class="todayWeather box">
                <div class="card-header card-title">
                    <h4>오늘의 날씨</h4>
                </div>
                <div class="card-location">현위치 {{ this.locationInfo }}</div>
                <div class="card-icon">
                    <div class="card-round"><img :src="this.iconSrc" class="weatherIcon"></div>
                </div>
                <div class="card-weather">
                    <div class="card-weather-box">
                        <div class="card-weather1">{{ this.currentWeather.weather[0].main }}</div>
                        <div class="card-weather3">{{ this.currentWeather.weather[0].description }}</div>
                        <div class="card-weather3">평균 기온 {{ this.currentWeather.temp }}℃</div>
                        <div class="card-weather2">
                            <div class="card-weather2-left"> 최고 기온 {{ this.weeklyWeather[0].temp.max }}℃</div>
                            <div class="card-weather2-right"> 최저 기온 {{ this.weeklyWeather[0].temp.min }}℃</div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="todayWear" class="todayWear box">
                <div class="card-title">
                    <div class="card-header">
                        <h4>오늘의 코디</h4>
                    </div>
                </div>
                <div v-for="item in wwResult" class="card-wear">
                    <div class="todayReco"><img :src="item" alt="이미지" class="wear-img"/></div>
                </div>
            </div>
            <div id="weeklyWeather" class="weeklyWeather box">
                <div class="card-title">
                    <div class="card-header">
                        <h4>주간 날씨 정보</h4>
                    </div>
                </div>
                <div class="card-week">
                    <div v-for="(item,i) in weeklyWeather" v-if="i > 0" :key="i" class="card-day">
                        <div class="card-day-box">{{ $moment().add(i, 'days').format('YYYY-MM-DD') }}</div>
                        <div class="card-day-box">
                            <div><img :src="`https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png`"
                                      class="card-day-icon"></div>
                        </div>
                        <div class="card-day-box">{{ item.weather[0].main }}</div>
                        <div class="card-day-box">{{ item.temp.day }}℃</div>
                        <div class="card-day-box">
                            <div class="card-day-box-left">{{ item.temp.max }}℃</div>
                            <div class="card-day-box-right">{{ item.temp.min }}℃</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {api_wwData, loadCoords, weatherData} from "@/api/weather";

export default {
    data() {
        return {
            locationInfo  : '',
            currentWeather: {},
            weeklyWeather : [],
            iconSrc       : "",
            wwResult      : {}
        }
    },
    methods: {
        async fetchWeather() {
            await loadCoords();
            this.locationInfo = weatherData.timezone;
            this.currentWeather = weatherData.current;
            this.iconSrc = `https://openweathermap.org/img/wn/${weatherData.current.weather[0].icon}@2x.png`;
            this.weeklyWeather = weatherData.daily;

            const Data = {
                user_id: this.$store.state.id,
                temp   : weatherData.daily[0].temp.day
            }
            //날씨-코디 img 예측값 가져오기
            const {data} = await api_wwData(Data);
            this.wwResult = data;

        },
    }
    ,
    created() {
        this.fetchWeather();
    },
};
</script>
<style scoped>
@import '../../css/common/mainP.css';
</style>
