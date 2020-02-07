import com.hotels.loyalty.Point
import java.util.*

val rewardIdOpt: Optional<Int> = Optional.ofNullable(null)
val rewardId: Int? = null

rewardIdOpt.map { n -> n * 2 }.orElse(-1)

rewardId?.let { n -> n * 2 } ?: -1

val point = Point(usdValue = 100.0, accountId = 50)

point.rewardId?.let{
    "rewardId=$it"
} ?: "no reward id"


val a: Int? = 4
val b: Int? = 5

val c = a.valueOrZero() + b.valueOrZero()

fun Int?.valueOrZero() = this ?: 0
