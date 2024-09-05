# Test Scenarios <a name="test-scenarios"></a>

# Table of Contents
1. [Test scenarios](#test-scenarios)
2. [Set flight inactive](#set-flight-inactive)
3. [Lookup available flight](#lookup-available-flight)
4. [Static flight discount](#static-flight-discount)
5. [Dynamic flight discount](#dynamic-flight-discount)
6. [Register upcoming flight](#register-upcoming-flight)
7. [Create booking](#create-booking)
8. [Modify flights](#modify-flights)
9. [Get total revenue of route](#get-total-revenue-of-route)
10. [Get number of economy class passengers](#get-economy-class-passengers)
11. [Get number of economy+ class passengers](#get-economy+-class-passengers)
12. [Get number of first class passengers](#get-first-class-passengers)
13. [Get number of extra option passengers](#get-extra-option-passengers)
14. [Get number of extra legroom passengers](#get-extra-legroom-passengers)
15. [Get number of extra food passengers](#get-extra-food-passengers)
16. [Get number of extra luggage passengers](#get-extra-luggage-passengers)
17. [Get all routes](#get-all-routes)
18. [Get routes by start airport](#get-routes-by-start-airport)
19. [Get routes by end airport](#get-routes-by-end-airport)
20. [Get routes by null returns nothing](#get-routes-by-null)
21. [Get all bookings](#get-bookings)
22. [Get bookings by customer id](#get-booking-id)
23. [Get passengers by booking id](#get-passengers-id)
24. [Get bookings passengers returns empty list](#get-passengers-wrongid)
25. [Get bookings passengers throws exception](#get-passengers-exception)
26. [Get bookings by negative id returns nothing](#bookings-zeroid)
27. [Change seat](#change-seat)
28. [Change seat returns false](#change-seat2)
29. [Change menu](#change-menu)
30. [Change luggage](#change-luggage)

## Delete flight <a name="set-flight-inactive"></a>, from use case [Set Flight Inactive](../UseCaseDescriptions/use-case-descriptions.md#set-flight-inactive)  
| Name:     | Set a flight inactive                                                                                                                                                                                                                                                       |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list off all flights ( Dubai-Frankfurt, Barcelona- Berlin) <br> 2. Sales Officer selects flight from Dubai-Frankfurt <br> 3. Systems displays Date, time, Departure, Arrival and delete function  <br> 4. Sales Officer selects the delete function |
| Result:   | A flight has been deleted                                                                                                                                                                                                                                          |
## Lookup for an available flight <a name="lookup-available-flight"></a>, from use case [Look up available flight](../UseCaseDescriptions/use-case-descriptions.md#lookup-available-flights)  
| Name:     | Lookup for an available flight                                                                                                                                                                                                                                  |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System displays the function to choose the departure, arrival, and date <br> 2. Sales Employee chooses Frankfurt as Arrival, Berlin as departure and the 12.03.2023 as the date <br> 3. System displays a list with the available flights( at 8pm and 16 pm) |
| Result:   | Sales Employee could see the flights                                                                                                                                                                                                                            |

## Lookup for a flight <a name="lookup-flight"></a>, from use case [Look up available flight](../UseCaseDescriptions/use-case-descriptions.md#lookup-available-flights)  
| Name:     | Lookup for an available flight                                                                                                                                                                                                                                  |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System displays all flights <br> 2. Sales officer inserts AMS as IATA for departure date <br> 3. System displays a list with flights |
| Result:   | Sales Officer could see the flights                                                                                                                                                                                                                            |

## Static flight discount <a name="static-flight-discount"></a>, from use case [Discount flight](../UseCaseDescriptions/use-case-descriptions.md#discount-flight)  
| Name:     | Static Flight Discount                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list off all flights ( Dubai-Frankfurt, Barcelona- Berlin)<br>2. Sales Officer selects flight from Dubai-Frankfurt<br>3. System asks for if the discount should be dynamic or static<br>4. Sales Officer chooses static<br>5. Systems asks for amount of price reduction (discount) and period (start + end date).<br>6. Sales Officer enters the discount and time period<br>7. Sales Officer submits the discount<br>8. System checks if entered discount is valid |
| Result:   | A flight has been reduced in price for a limited time                                                                                                                                                                                                                                                                                                                                                                                                                                  |

## Dynamic flight discount <a name="dynamic-flight-discount"></a>, from use case [Discount flight](../UseCaseDescriptions/use-case-descriptions.md#discount-flight)  
| Name:     | Dynamic Flight Discount                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list off all routes ( Dubai-Frankfurt, Barcelona- Berlin)<br>2. Sales Officer selects flight from Dubai-Frankfurt<br>3. System asks for if the discount should be dynamic or static<br>4. Sales Officer chooses dynamic<br> 5. Systems asks for amount of price reduction (discount).<br>6. Sales Officer enters the discount<br>7. Sales Officer submits the discount<br>8. System checks if entered discount is valid<br>9. System interacts with API to determine the time period for the discount |
| Result:   | A flight has been reduced in price for a limited time                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |

## Register upcoming flight <a name="register-upcoming-flight"></a>, from use case [Register upcoming flight](../UseCaseDescriptions/use-case-descriptions.md#register-upcoming-flight)  
| Name:     | register upcoming flight                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Sales Officer enters the format to register an upcoming flight. <br> 2. System requests related information to the flight, which is going to be registered (date, arrival, departure, plane, etc.) <br> 3. Sales Officer decides that he wants to register a flight for the 11.04.2024 from Dubai-Frankfurt and selects plane and enters Date (11.04.2024). <br> 4. System saves the flight data on the 11.04.2024 from Dubai-Frankfurt with the plane 2. |
| Result:   | A upcoming flight and its requested information has been registered successfully                                                                                                                                                                                                                                                                                                                                                                             |

## Create booking <a name="create-booking"></a>, from use case [Create booking](../UseCaseDescriptions/use-case-descriptions.md#create-booking)  
| Name     | Create booking for one passenger |      
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario | 1. Actor searches for the departure (AMS)<br>2. System shows the flight with the departure (AMS) and the Arrival (DUS)<br>3. Actor selects the flight and chooses the Create new Booking element<br>4. System shows a format with customer details(First name, Last name, Mobile, Date of Birth, Address, email, Iban) and passenger details (first name, last name, date of birth, email, menu, nr of menus, seat, luggage)<br>5. Actor enters the customer details(Erkan, Kanat,123344, 14.10.2003, Boisheimerstr.44, erkan@mail.com,NL98765432123456789099) and enters the passenger details(Erkan, Kanat, 14.10.2003, erkan@mail.com,3, 12) and chooses the menu(pasta) and the seat (A1)<br>6. Actor chooses the add passenger element<br>7. System shows the entered passenger details and saves the passenger <br>8. System shows the total cost<br>9. Actor chooses the finalise booking element |
| Result   | A booking has been created with an unique number |                                                                                                                                                                                                                                                                                                                                                                                                                                                  


## Modify flights <a name="modify-flights"></a>
| Name:       | Modify flights                                                                                                                                                                                                                                                 |
|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario:   | 1. System shows a list of all flights (flight 1, flight 2,...) <br> 2. Sales Officer chooses the flight from Frankfurt to Dubai <br> 3. System shows the information regarding the flight (date, time) <br> 4. Sales Officer has the option to modify the date |
| Result:     | A flight was modified                                                                                                                                                                                                                                          |

## Modify booking <a name="modify-booking"></a>
| Name:     | Modify booking                                                                                                                                                                                                                                        |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System offers to choose the flight for the booking nr. <br> 2. Sales Employee chooses the booking (#2) <br> 3. System displays the information for booking nr.2 (date, options(luggage, seat, persons)) <br> 4. Sales Employee changes the options |
| Result:   | A booking was modified                                                                                                                                                                                                                                |

## Revenue overview <a name="revenue-overview"></a>, from use case [Get overview of revenue](../UseCaseDescriptions/use-case-descriptions.md#get-overview-of-revenue)  
| Name:     | Revenue overview                                                                                                                                         |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System displays a list of routes for the Sales Manager.<br/> 2. Sales Manager selects a route.<br/> 3. System updates view and displays revenue data. |
| Result:   | Revenue data is displayed                                                                                                                                |

## Flight KPI Overview <a name="flight-kpi-overview"></a>, from use case [Management dashboard for kpi](../UseCaseDescriptions/use-case-descriptions.md#management-dashboard-for-kpi)  
| Name:     | Route Key Performance Indicator overview                                                                                                      |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System displays a list of routes for the manager.<br/> 2. Sales Manager selects a route.<br/> 3. System displays the selected routes KPIs. |
| Result:   | Sales Manager receives a routes Key Performance Indicators                                                                                    |

## Correct KPI data <a name="correct-kpi-data"></a>, from use case [Management dashboard for kpi](../UseCaseDescriptions/use-case-descriptions.md#management-dashboard-for-kpi) 
| Name:     | Correct route KPI data                                                                                                                 |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Sales Manager selects a route to review KPI data.<br/> 2. System collects and displays the correct KPI data for the selected route. |
| Result:   | For each route accurate KPI data is presented to the user                                                                              |

## Get total revenue of a route <a name="get-total-revenue-of-route"></a>
| Name:     | Get total revenue of a route                                                                                                                                                                                                               |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System calculates the price difference '100.0' and number of routes associated with the same flight '2' and returns it as total revenue of the route. |
| Result:   | Route total revenue calculated and returned.                                                                                                                                                                                               |

## Get number of economy passengers <a name="get-economy-class-passengers"></a>
| Name:     | Get economy class passengers                                                                                                                        |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 5 economy passengers associated with the route. |
| Result:   | Number of economy passengers of the route returned.                                                                                                 |

## Get number of economy+ passengers <a name="get-economy+-class-passengers"></a>
| Name:     | Get economy+ class passengers                                                                                                                        |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 3 economy+ passengers associated with the route. |
| Result:   | Number of economy+ passengers of the route returned.                                                                                                 |

## Get number of first class passengers <a name="get-first-class-passengers"></a>
| Name:     | Get first class passengers                                                                                                                              |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 2 first class passengers associated with the route. |
| Result:   | Number of first class passengers of the route returned.                                                                                                 |

## Get number of extra option passengers <a name="get-extra-option-passengers"></a>
| Name:     | Get extra option passengers                                                                                                                                                    |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 10 passengers associated with the route who have any kind of extra option. |
| Result:   | Number of extra option passengers of the route returned.                                                                                                                       |

## Get number of extra legroom passengers <a name="get-extra-legroom-passengers"></a>
| Name:     | Get extra legroom passengers                                                                                                                                        |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 5 passengers associated with the route, who have extra legroom. |
| Result:   | Number of extra legroom passengers of the route returned.                                                                                                           |

## Get number of extra food passengers <a name="get-extra-food-passengers"></a>
| Name:     | Get extra food passengers                                                                                                                                                       |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 5 passengers associated with the route, who have chosen to have extra food. |
| Result:   | Number of extra food passengers of the route returned.                                                                                                                          |

## Get number of extra luggage passengers <a name="get-extra-luggage-passengers"></a>
| Name:     | Get extra luggage passengers                                                                                                                                        |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. System shows a list of routes <br> 2. Sales Manager selects route VNO-AMS <br> 3. System returns 5 passengers associated with the route, who have extra luggage. |
| Result:   | Number of extra luggage passengers of the route returned.                                                                                                           |

## Get all routes <a name="get-all-routes"></a>
| Name:     | Get all routes                                                                                                       |
|-----------|----------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of RouteData is initiated 2. A test route data is added to the list. 3. Get all routes method is executed. |
| Result:   | All routes are returned.                                                                                             | 

## Get routes by start airport <a name="get-routes-by-start"></a>
| Name:     | Get routes by start airport                                                                                                                            |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of RouteData is initiated. 2. A string "VNO" is provided to the method. 3. One or more RouteData objects is retrieved and added to the list. |
| Result:   | All routes matching the start airport "VNO" are returned.                                                                                              |

## Get routes by end airport <a name="get-routes-by-end"></a>
| Name:     | Get routes by end airport                                                                                                                              |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of RouteData is initiated. 2. A string "AMS" is provided to the method. 3. One or more RouteData objects is retrieved and added to the list. |
| Result:   | All routes matching the end airport "AMS" are returned.                                                                                                |

## Get routes by null returns nothing <a name="get-routes-by-null"></a>
| Name:     | Get routes by null airport                                                         |
|-----------|------------------------------------------------------------------------------------|
| Scenario: | 1. A list of RouteData is initiated. 2. An empty string is provided to the method. |
| Result:   | List of RouteData is empty.                                                        |

## Get all bookings <a name="get-bookings"></a>
| Name:     | Get bookings                                                                                                                                            |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of BookingData is initiated. 2. A test booking is added to the list. 3. Get bookings method is executed and returns list of all booking data. |
| Result:   | List contains test BookingData object.                                                                                                                  |

## Get bookings by customer id <a name="get-booking-id"></a>
| Name:     | Get bookings by customer id                                                                                                                                                                              |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of BookingData is initiated. 2. A test booking with customer id '10' is added to the list. 3. The method to get bookings by customer id '10' is executed and returns list of all booking data. |
| Result:   | List contains test BookingData object with customer id of 10.                                                                                                                                            |

## Get passengers by booking id <a name="get-passengers-id"></a>
| Name:     | Get passengers by booking id                                                                                                                                                                                   |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of PassengerData is initiated. 2. A test passenger with booking id '11' is added to the list. 3. The method to get passengers by booking id '11' is executed and returns list of all passenger data. |
| Result:   | List contains test PassengerData object with booking id of 11.                                                                                                                                                 |

## Get passengers by wrong booking id <a name="get-passengers-wrongid"></a>
| Name:     | Get passengers by wrong booking id                                                                                                                                                                        |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of PassengerData is initiated. 2. A test passenger with booking id '11' is added to the list. 3. The method to get passengers by booking id '0' is executed and returns list of passenger data. |
| Result:   | List doesn't contain test PassengerData object with booking id of 11.                                                                                                                                     |

## Get passengers by booking id 0 throws exception <a name="passengers-exception"></a>
| Name:     | Get bookings passengers throws exception                   |
|-----------|------------------------------------------------------------|
| Scenario: | 1. Get bookings passengers by booking id of 0 is executed. |
| Result:   | Persistence exception is thrown.                           |

## Get bookings by negative id <a name="bookings-zeroid"></a>
| Name:     | Get bookings by illegal customer id returns nothing                                                                                                                                                     |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. A list of BookingData is initiated. 2. A test booking with customer id '10' is added to the list. 3. The method to get bookings by customer id '0' is executed and returns list of all booking data. |
| Result:   | List of BookingData is empty.                                                                                                                                                                           |

## Change seat <a name="change-seat"></a>
| Name:     | Test change seat                                                                                                                                                                                  |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Two objects 'originalSeat' and 'newSeat' are created. 2. A passenger object is created and assigned 'originalSeat'. 3. Change seat method is executed to change 'originalSeat' with 'newSeat'. |
| Result:   | Change seat method returns true and the seat is changed.                                                                                                                                          |

## Change seat returns false <a name="change-seat2"></a>
| Name:     | Test change seat returns false                                                                                                                                                                                                                 |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Two objects 'originalSeat' and 'newSeat' with illegal name are created. 2. A passenger object is created and assigned 'originalSeat'. 3. Change seat method is executed to change 'originalSeat' with 'newSeat' that has some illegal name. |
| Result:   | Change seat method returns false and the seat is not changed.                                                                                                                                                                                  |

## Change menu <a name="change-menu"></a>
| Name:     | Test change menu                                                                                                                                                                                  |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Two objects 'originalMenu' and 'newMenu' are created. 2. A passenger object is created and assigned 'originalMenu'. 3. Change menu method is executed to change 'originalMenu' with 'newMenu'. |
| Result:   | Change menu method returns true and the menu is changed.                                                                                                                                          |

## Change luggage <a name="change-luggage"></a>
| Name:     | Test change luggage                                                                                                                                                                                              |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Scenario: | 1. Two objects 'originalLuggage' and 'newLuggage' are created. 2. A passenger object is created and assigned 'originalLuggage'. 3. Change seat method is executed to change 'originalLuggage' with 'newLuggage'. |
| Result:   | Change luggage method returns true and the luggage is changed.                                                                                                                                                   |
