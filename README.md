## ::Taxi App::

The reason for creating this application was the desire to develop programming skills. 
The app is a copy of apps such as Uber or Bolt. 
It allows you to order a cab in the simplest possible way.


## Technologies Used

* Java 17
* Spring
* Spring Boot
* Hibernate/Spring Data JPA
* Gradle
* JUnit/Jupiter
* Mockito
* MySQL
* H2



## Getting started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
See deployment for notes on how to deploy the project on a live system. Just clone or download the files into a folder and it's ready to be used.

- Clone the repository
```
git clone https://github.com/Grande17/AppTaxi.git
```
- Build the project
```
./gradlew build
```
- Run the jar file on your local machine or in a docker container
    - Set the connection properties of your preferred database in the application.properties file



  

## Customer Controller
<details>
<summary>Endpoints</summary>

### Create new customer
```
POST /v1/customers
```
### Body
```
{
"email": "string",
"id": 0,
"name": "string",
"phoneNumber": "string",
"surname": "string",
"username": "string"
}
```



### GET
All GET endpoints in this controller return the same response:
### Response

```
{
    "email": "string",
    "id": 0,
    "name": "string",
    "phoneNumber": "string",
    "surname": "string",
    "username": "string"
  }
```

1. Returns all created customers 
```
GET /v1/customers
```

2. Returns the specified customer by its ID

| Parameter | Type | Description |
|:----------| :--- | :--- |
| `id`      | `int` | **Required**.  |

```
GET /v1/customers/{id}
```

3. Returns the specified customer by its name

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `name`    | `String` | **Required**.  |

```
GET /v1/customers/name/{name}
```
4. Returns the specified customer by its username

| Parameter  | Type     | Description |
|:-----------|:---------| :--- |
| `username` | `String` | **Required**.  |

```
GET /v1/customers/username/{username}
```

### PUT



1. Update the specified customer:

### Body
```
{
    "email": "string",
    "id": 0,
    "name": "string",
    "phoneNumber": "string",
    "surname": "string",
    "username": "string"
  }
```


```
PUT /v1/customers
```
2. Update email of specified customer:

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `id`      | `int`    | **Required**.  |
| `email`   | `String` | **Required**.  |


```
PUT /v1/customers/email/{id}/{email}
```

### DELETE

| Parameter | Type | Description |
|:----------| :--- | :--- |
| `id`      | `int` | **Required**.  |

```
DEL /v1/customer/{id}
```
</details>

## Driver Controller

<details>

<summary>Endpoints</summary>


### POST

#### Create new driver
While creating new driver you also define the car that is linked to the driver
```
POST /v1/drivers
```
#### Body

```

  "car": {
    "bodyType": "string",
    "carBrand": "string",
    "id": 0,
    "licensePlateNumber": "string",
    "model": "string"
  },
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string",
  "status": "ACCOUNT_DELETED",
  "surname": "string"
}
```


### GET

#### Response

```
[
  {
    "car": {
      "bodyType": "string",
      "carBrand": "string",
      "id": 0,
      "licensePlateNumber": "string",
      "model": "string"
    },
    "email": "string",
    "id": 0,
    "name": "string",
    "phoneNumber": "string",
    "status": "ACCOUNT_DELETED",
    "surname": "string"
  }
]
```

1. This provides a list of all created drivers:

```
GET /v1/drivers
```

2. This provides a driver specified by its id:

| Parameter | Type | Description |
|:----------| :--- | :--- |
| `id`      | `int` | **Required**.  |

```
GET /v1/drivers/{id}
```
3. This provides a driver specified by its email:

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `email`   | `String` | **Required**.  |

```
GET /v1/drivers/email/{email}
```
4. This provides a drivers specified by name or surname:

| Parameter  | Type     | Description |
|:-----------|:---------| :--- |
| `contains` | `String` | **Required**.  |

```
GET /v1/drivers/name/{contains}
```

### PUT

1. Update driver

#### Body
```
{
  "car": {
    "bodyType": "string",
    "carBrand": "string",
    "id": 0,
    "licensePlateNumber": "string",
    "model": "string"
  },
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string",
  "status": "ACCOUNT_DELETED",
  "surname": "string"
}
```


```
PUT /v1/drivers/
```
2. Update driver status

| Status    |
|-----------|
| `ACTIVE`  |
| `BUSY`    |
| `BREAK`    |
| `INACTIVE`    |
| `ACCOUNT_DELETED`    |

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `id`      | `int`    | **Required**.  |
| `status`  | `String` | **Required**.  |

```
PUT /v1/drivers/status/{id}/{status}
```




### DELETE

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `id`      | `int`    | **Required**.  |

```
DEL /v1/drivers/{idd}
```
</details>

## Car Controller
<details>

<summary>Endpoints</summary>

### GET

#### Response

```
{
  "bodyType": "string",
  "carBrand": "string",
  "id": 0,
  "licensePlateNumber": "string",
  "model": "string"
}
```

1. Get all cars

```
GET /v1/cars
```

2. Get car by its id

| Parameter | Type | Description |
|:----------| :--- | :--- |
| `idd`     | `int` | **Required**.  |

```
GET /v1/cars/{id}
```

3. Get car by its plates

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `plates`  | `String` | **Required**.  |

```
GET /v1/cars/plates/{plates}
```

### PUT

1. Update car

#### Body
```
{
  "bodyType": "string",
  "carBrand": "string",
  "id": 0,
  "licensePlateNumber": "string",
  "model": "string"
}
```

```
PUT /v1/cars/
```
</details>

## Order controller

<details>

<summary>Endpoints</summary>

### POST

1. Create new order

#### Body
```
{
    "pickUpPlace": "string",
    "dropPlace": "string",
    "customer": {
        "id": 0
    }
}
```
```
POST /v1/order
```
### GET
#### Response
```
{
    "customer": {
      "discount": 0,
      "email": "string",
      "id": 0,
      "name": "string",
      "phoneNumber": "string",
      "surname": "string",
      "username": "string"
    },
    "driver": {
      "car": {
        "bodyType": "string",
        "carBrand": "string",
        "id": 0,
        "licensePlateNumber": "string",
        "model": "string"
      },
      "email": "string",
      "id": 0,
      "name": "string",
      "phoneNumber": "string",
      "status": "ACCOUNT_DELETED",
      "surname": "string"
    },
    "dropPlace": "string",
    "estimatedCost": 0,
    "estimatedDuration": {
      "hour": 0,
      "minute": 0,
      "nano": 0,
      "second": 0
    },
    "id": 0,
    "pickUpPlace": "string",
    "status": "ACTIVE"
  }
```

1. Get all orders
```
GET /v1/order
```
2. Get all orders of particular driver

| Parameter | Type  | Description |
|:----------|:------| :--- |
| `id`      | `int` | **Required**.  |
```
GET /v1/order/driverHistory/{id}
```
3. Get all orders of particular customer

| Parameter | Type  | Description |
|:----------|:------| :--- |
| `id`      | `int` | **Required**.  |
```
GET /v1/order/history/{id}
```
4. Get all orders by status

| Status   | 
|:---------|
| `ACTIVE` | 
| `IN_PROGRESS` | 
| `CANCELLED` | 
| `FINISHED` | 



| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `status`  | `string` | **Required**.  |
```
GET /v1/order/status/{status}
```
### PUT
1. Update order status

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `orderId` | `int`    | **Required**.  |
| `status`  | `string` | **Required**.  |

```
PUT /v1/order/{orderId}/{status}
```
2. Cancel order

| Parameter | Type     | Description |
|:----------|:---------| :--- |
| `orderId` | `int`    | **Required**.  |

```
PUT /v1/order/cancel/{orderId}
```

</details>