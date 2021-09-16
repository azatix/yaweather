package steps

import model.Resp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface ApiRequest {
    @GET("/v2/forecast")
    fun getWeatherPrediction(
        @Header("X-Yandex-API-Key") key: String,
        @QueryMap(encoded = true) params: Map<String, String> = mapOf("" to "")
    ) : Call<Resp>
}
