# Asset-Management Restful Backend
Very simple json rest api showing one way of using akka http with [slick 3](https://github.com/slick/slick) library for database access.


It supports the following features:

* Generic Data Access layer, create a DAL with crud for an entity with just one line
* Models as case classes and slick models, independent from database driver and profile
* Multiple database types configured in properties file (h2 and postgresql for instance)
* Cake pattern for DI
* Spray-json to parse json
* Tests for DAL
* tests for routes

Utils: 

* Typesafe config for property management
* Typesafe Scala Logging (LazyLogging)


#Running

The database pre-configured for postgres, so you just have to:


        $ sbt run

#Using


	
	curl localhost:8080/printers/1

#TODO


#Credits

Akka-Slick template for getting me started.
