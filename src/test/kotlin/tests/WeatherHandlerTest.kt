package tests

import helpers.AllureHelpers.step
import io.qameta.allure.Allure.step
import io.qameta.allure.Description
import io.qameta.allure.Issue
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel.NORMAL
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import steps.YandexWeatherStep

private const val KEY = "" //X-Yandex-API-Key

@DisplayName("Weather Handler Testing")
class WeatherHandlerTest {

    @DisplayName("Test-001")
    @Test
    @Issue("Weather project 001")
    @Description("Default location based on your ip")
    @Severity(NORMAL)
    fun getWeatherPredictionDefaultLocation() {

        val defaultLocation = "Россия" // default location based on your ip

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY).execute()
        val body = response.body()

        step("Check the response") {
            step("check default location $defaultLocation")
            assertThat(
                "country is correct",
                body!!.geo_object.country.name, equalTo(defaultLocation)
            )
        }
    }

    @DisplayName("Test-002")
    @Test
    @Issue("Weather project 002")
    @Description("Default location based on your ip for EN")
    @Severity(NORMAL)
    fun getWeatherPredictionDefaultLocationEn() {

        val defaultLocation = "Russian Federation" // default location based on your ip

        val params = mapOf(
            "lang" to "en_US"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            step("check default location $defaultLocation")
            assertThat(
                "country is correct",
                body!!.geo_object.country.name, equalTo(defaultLocation)
            )
        }
    }

    @DisplayName("Test-003")
    @Test
    @Issue("Weather project 003")
    @Description("Location based on Moscow latitude and longitude")
    @Severity(NORMAL)
    fun getWeatherPredictionForMoscow() {

        val cityName = "Москва"

        val params = mapOf(
            "lat" to "55.45",
            "lon" to "37.37"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            step("check province name $cityName")
            assertThat(
                "province name is correct",
                body!!.geo_object.province.name, equalTo(cityName)
            )
        }
    }

    @DisplayName("Test-004")
    @Test
    @Issue("Weather project 004")
    @Description("Weather prediction for one day only")
    @Severity(NORMAL)
    fun getWeatherPredictionWithLimitOne() {

        val params = mapOf(
            "limit" to "1"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            assertThat(
                "forecast only for one day",
                body!!.forecasts.size, equalTo(1)
            )
        }
    }

    @DisplayName("Test-005")
    @Test
    @Issue("Weather project 005")
    @Description("Weather prediction for more then one day")
    @Severity(NORMAL)
    fun getWeatherPredictionWithForThreeDays() {

        val params = mapOf(
            "limit" to "3"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            assertThat(
                "forecast for more then 1 day",
                body!!.forecasts.size, equalTo(3)
            )
        }
    }

    @DisplayName("Test-006")
    @Test
    @Issue("Weather project 006")
    @Description("Weather prediction for one day with hours")
    @Severity(NORMAL)
    fun getWeatherPredictionOneDayWithHours() {

        val params = mapOf(
            "limit" to "1",
            "hours" to "true"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            assertAll("check forecast data",
                { assertThat("forecast only for one day", body!!.forecasts.size, equalTo(1)) },
                { assertThat("forecast contains 24 hours", body!!.forecasts[0].hours.size, equalTo(24))}

            )
        }
    }

    @DisplayName("Test-007")
    @Test
    @Issue("Weather project 007")
    @Description("Weather prediction for one day without hours")
    @Severity(NORMAL)
    fun getWeatherPredictionOneDayWithoutHours() {

        val params = mapOf(
            "limit" to "1",
            "hours" to "false"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(key = KEY, params = params).execute()
        val body = response.body()

        step("Check the response") {
            assertAll("check forecast data",
                { assertThat("forecast only for one day", body!!.forecasts.size, equalTo(1)) },
                { assertThat("forecast doesn't  contain hours", body!!.forecasts[0].hours.isNullOrEmpty())}

            )
        }
    }
}
