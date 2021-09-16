package tests

import helpers.AllureHelpers.step
import io.qameta.allure.Allure.step
import io.qameta.allure.Description
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel.NORMAL
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import steps.YandexWeatherStep
import java.time.LocalDate

@DisplayName("Weather Handler Testing")
class WeatherHandlerTest {

    @DisplayName("Default location based on your ip")
    @Test
    @Description("Prediction based on ip of client which makes request")
    @Severity(NORMAL)
    fun getWeatherPredictionDefaultLocation() {

        val defaultLocation = "Россия" // default location based on your ip

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction().execute()
        val body = response.body()!!

        step("Check the response") {
            step("check default location $defaultLocation")
            assertThat(
                "country is correct",
                body.geo_object.country.name, equalTo(defaultLocation)
            )
        }
    }

    @DisplayName("Default location based on your ip for EN")
    @Test
    @Description("Prediction in english language")
    @Severity(NORMAL)
    fun getWeatherPredictionDefaultLocationEn() {

        val defaultLocation = "Russian Federation" // default location based on your ip

        val params = mapOf(
            "lang" to "en_US"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            step("check default location $defaultLocation")
            assertThat(
                "country is correct",
                body.geo_object.country.name, equalTo(defaultLocation)
            )
        }
    }

    @DisplayName("Location based on Moscow latitude and longitude")
    @Test
    @Description("We pass coordinates of Moscow to get prediction for this city")
    @Severity(NORMAL)
    fun getWeatherPredictionForMoscow() {

        val cityName = "Москва"

        val params = mapOf(
            "lat" to "55.45",
            "lon" to "37.37"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            step("check province name $cityName")
            assertThat(
                "province name is correct",
                body.geo_object.province.name, equalTo(cityName)
            )
        }
    }

    @DisplayName("Weather prediction for one day only")
    @Test
    @Description("We limit prediction with only one day")
    @Severity(NORMAL)
    fun getWeatherPredictionWithLimitOne() {

        val params = mapOf(
            "limit" to "1"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            assertThat(
                "forecast only for one day",
                body.forecasts.size, equalTo(1)
            )
        }
    }

    @DisplayName("Weather prediction for more than one day")
    @Test
    @Description("We try to get prediction for more than one day for example 3")
    @Severity(NORMAL)
    fun getWeatherPredictionWithForThreeDays() {

        val params = mapOf(
            "limit" to "3"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            assertThat(
                "forecast for more than 1 day",
                body.forecasts.size, equalTo(3)
            )
        }
    }

    @DisplayName("Weather prediction for one day with hours")
    @Test
    @Description("We try to get prediction for one day for every hour")
    @Severity(NORMAL)
    fun getWeatherPredictionOneDayWithHours() {

        val params = mapOf(
            "limit" to "1",
            "hours" to "true"
        )

        val currentDate = LocalDate.now().toString()

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            assertAll("check forecast data",
                { assertThat("forecast only for one day", body.forecasts.size, equalTo(1)) },
                { assertThat("forecast contains 24 hours", body.forecasts[0].hours.size, equalTo(24))},
                { assertThat("forecast for today", body.forecasts[0].date, equalTo(currentDate))}

            )
        }
    }

    @DisplayName("Weather prediction for one day without hours")
    @Test
    @Description("We try to get prediction for one day without hours")
    @Severity(NORMAL)
    fun getWeatherPredictionOneDayWithoutHours() {

        val params = mapOf(
            "limit" to "1",
            "hours" to "false"
        )

        val api = YandexWeatherStep.getWeather()
        val response = api.getWeatherPrediction(params = params).execute()
        val body = response.body()!!

        step("Check the response") {
            assertAll("check forecast data",
                { assertThat("forecast only for one day", body.forecasts.size, equalTo(1)) },
                { assertThat("forecast doesn't  contain hours", body.forecasts[0].hours.isNullOrEmpty())}
            )
        }
    }
}
