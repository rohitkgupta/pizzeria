###### Pizza Factory

PizzaFactory is a backend service for Pizza Store and it has two entry points.

`1`  OrderService `-` Consumer client uses this service to place order. 

`2` VendorService `-` Vendor client uses this service for inventory management.

`3` ItemService `-` Both Vendor and Consumer Client uses this service to list down available option.

**Consumer Client:** Consumer client will build order object using ItemService(to get list of available items) and validate and submit order using validate(Order) and placeOrder(Order) method.

**Vendor Client:** Vendor client will use item service to list down available items and give option to add new item, update item price, update item quantity

Please check IntegrationTest to understand the flow.
