package tests

import io.qameta.allure.Description
import io.qameta.allure.Issue
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel.NORMAL
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import steps.YandexWeatherStep
import java.util.*

private const val KEY = "" //X-Yandex-API-Key

@DisplayName("Weather Handler Errors Testing")
class WeatherHandlerErrorsTest {

    @DisplayName("Test-")
    @Test
    @Issue("Weather project 000")
    @Description("Access denied with custom key")
    @Severity(NORMAL)
    fun getWeatherPredictionAccessDenied() {

        val customKey = UUID.randomUUID().toString()

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = customKey).execute()

        MatcherAssert.assertThat(
            "check that we get Access Denied error",
            response.code(), equalTo(403)
        )
    }

    @DisplayName("Test-")
    @Test
    @Issue("Weather project 000")
    @Description("Geolocation not found")
    @Severity(NORMAL)
    fun getWeatherPredictionGeolocationNotFound() {

        val params = mapOf(
            "lat" to "400",
            "lon" to "400"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()

        MatcherAssert.assertThat(
            "check that we get Not Found error",
            response.code(), equalTo(404)
        )
    }
}
