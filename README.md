## Loyalty Service

Loyalty Service is an application written in Kotlin and Spring Boot built for educational purposes.
It implements a simple loyalty program with the following characteristics:

* Clients can create *loyalty points*.
Each loyalty point is associated with an account identified (integer) and a USD value.
Bulk creation is supported.

* When an account holder reaches **four** loyalty points, the user is entitled to a *loyalty reward*.
The value of the reward is the average of the loyalty points that compose it.

* Account holders can:

  * Retrieve all their loyalty points.
  * Retrieve all their loyalty rewards.
  * Redeem their rewards, i.e., use their free product.

### Kotlin features demonstrated

* Nullable types
* Extension functions
* Collections rich APIs
* Immutability support
* Data classes
* String templates (TODO!)