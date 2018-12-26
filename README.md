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

### Kotlin features covered

* Nullable types
* Extension functions
* Rich Collection APIs
* Immutability support
* Data classes
* String templates
* *When* statement
* *Let* and *Elvis operator*


### How to build and run the application

```
mvn clean install && mvn spring-boot:run
```

### Sample requests

Adds two points to accountId=500 and one point to accountId=30:

```
curl -v -X POST \
  http://localhost:8080/points \
  -H 'content-type: application/json' \
  -d '[
    {
        "accountId": 500,
        "usdValue": 100
    },
    {
        "accountId": 500,
        "usdValue": 100
    },
    {
        "accountId": 30,
        "usdValue": 200
    }
]'
```

Get all points for accountId=500:

```
curl http://localhost:8080/account/500/points | json_pp
```

Get all rewards for accountId=500:
```
curl http://localhost:8080/account/500/rewards | json_pp
```

Redeem a reward
```

```