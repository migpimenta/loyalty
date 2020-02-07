data class Car(val year: Int,
               val powerCC: Int,
               val weightKg: Int,
               val automaticGears: Boolean)

val car1 = Car(1996, 1600, 1598, true)

val car2 = Car(year = 1996,
        powerCC = 1600,
        weightKg = 1598,
        automaticGears = true)

fun calculateCilinderVolume(height: Int, radius: Int) = height * radius * radius * Math.PI

val volume = calculateCilinderVolume(height = 10, radius = 2)