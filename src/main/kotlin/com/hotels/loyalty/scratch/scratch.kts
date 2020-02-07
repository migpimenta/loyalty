import java.util.*

data class Point(val id: UUID = UUID.randomUUID(), val accountId: Int, val usdValue: Double, val rewardId: UUID? = null)

val point = Point(accountId = 50, usdValue = 100.0)
val point2 = Point(id = UUID.fromString("71e824df-a17b-4f7b-a178-f7a53f033dd2"), accountId = 50, usdValue = 100.0)

point
point2
