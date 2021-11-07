package com.rk.weatherapp.data.repositories.http

import com.rk.weatherapp.domain.entities.Result
import com.rk.weatherapp.domain.entities.Weather
import com.rk.weatherapp.domain.interfaces.repositories.WeatherRepo
import retrofit2.http.GET
import java.lang.Exception

class OpenWeatherWeatherRepo: WeatherRepo {

    override suspend fun getWeatherForCity(cityId: String): Result<Weather, Exception> {
        TODO("Not yet implemented")
    }
    
}

class OWWeather {

}

class OWCoord {

}


//{
//    "coord": {
//    "lon": 105.7993,
//    "lat": 11.0879
//},
//    "weather": [
//    {
//        "id": 803,
//        "main": "Clouds",
//        "description": "多雲",
//        "icon": "04n"
//    }
//    ],
//    "base": "stations",
//    "main": {
//    "temp": 298.11,
//    "feels_like": 299.01,
//    "temp_min": 298.11,
//    "temp_max": 298.11,
//    "pressure": 1007,
//    "humidity": 90,
//    "sea_level": 1007,
//    "grnd_level": 1006
//},
//    "visibility": 10000,
//    "wind": {
//    "speed": 2.07,
//    "deg": 161,
//    "gust": 3.83
//},
//    "clouds": {
//    "all": 80
//},
//    "dt": 1636310802,
//    "sys": {
//    "country": "KH",
//    "sunrise": 1636325422,
//    "sunset": 1636367445
//},
//    "timezone": 25200,
//    "id": 1821993,
//    "name": "Svay Rieng",
//    "cod": 200
//}