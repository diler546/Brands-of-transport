package ma.shuler.brandsoftransport

data class Transport(
    val name: String, // Название
    val type: String, // "Мотоцикл" или "Автомобиль"
    val loadCapacity: Int, // Грузоподъемность
    val axleCount: Int // Количество осей
)