package steps

import api.ApiRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "https://api.weather.yandex.ru"

class YandexWeatherStep {

    companion object {
        fun getWeather() : ApiRequest {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiRequest::class.java)
        }
    }
}
