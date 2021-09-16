package model

data class Hours(
    val hour: String,
    val hour_ts: Int,
    val temp: Int,
    val feels_like: Int,
    val icon: String,
    val condition: String,
    val cloudness: Double,
    val prec_type: Int,
    val prec_strength: Double,
    val is_thunder: Boolean,
    val wind_dir: String,
    val wind_speed: Double,
    val wind_gust: Double,
    val pressure_mm: Int,
    val pressure_pa: Int,
    val humidity: Int,
    val uv_index: Double,
    val soil_temp: Double,
    val soil_moisture: Double,
    val prec_mm: Double,
    val prec_period: Int,
    val prec_prob: Int
)
