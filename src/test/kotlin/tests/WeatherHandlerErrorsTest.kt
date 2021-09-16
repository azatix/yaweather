package tests

import io.qameta.allure.Description
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel.NORMAL
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import steps.YandexWeatherStep
import java.util.*

@DisplayName("Weather Handler Errors Testing")
class WeatherHandlerErrorsTest {

    @DisplayName("Access denied with custom key")
    @Test
    @Description("Checking handler when we don't have access")
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

    @DisplayName("Geolocation not found")
    @Test
    @Description("We try to request with impossible coordinates")
    @Severity(NORMAL)
    fun getWeatherPredictionGeolocationNotFound() {

        val params = mapOf(
            "lat" to "400",
            "lon" to "400"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()

        MatcherAssert.assertThat(
            "check that we get Not Found error",
            response.code(), equalTo(404)
        )
    }
}
