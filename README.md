###### Pizza Factory
Build Instructions: Please use ./gradlew clean build command to build project on your machine.
This command will run test cases and generate executable jar.

Note: You may need to run chmod +x gradle and chmod +x gradlew first time to give executable permission to gradlew. 

I have added both Integration and Unit test cases. If you start reading code from IntegrationTest.java it will give you better idea of flow and design.

PizzaFactory is a backend service for Pizza Store and it has below three main services.

`1`  OrderService `-` Consumer client uses this service to place order. 

`2` VendorService `-` Vendor client uses this service for inventory management.

`3` ItemService `-` Both Vendor and Consumer Client uses this service to list down available option.

**Consumer Client:** Consumer client will build order using ItemService(to get list of available items) and validate and submit order using validate(Order) and placeOrder(Order) method.

**Vendor Client:** Vendor client will use item service to list down available items and give option to add new item, update item price, update item quantity using VendorService

Please feel free to call on phone in case of any queries. 
