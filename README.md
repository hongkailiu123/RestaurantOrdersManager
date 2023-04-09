# An application for restaurant operations - *Feast*

## Description
I want to develop an application for restaurant operations called Feast! 
Feast aims to improve the efficiency of restaurant operations.
For example, Feast can track the status of each table (available or not), 
record the items the customers ordered for each table, 
generate the bill and manage the list of customers waiting for a table.
Managers will use Feast to help operate their restaurants, 
and this application would be applicable to every restaurant after an adaption.
Developing an application for restaurant operations sounds practical. 
I love to apply what I learned in real life. Also, 
I dream of having a restaurant and hope to use my self-developed 
application to help operate my own restaurant.


## User Stories

- As a user, I want to be able to add an item customers ordered to their tables' order list.
- As a user, I want to be able to remove an item customers ordered from their tables' order list.
- As a user, I want to be able to generate a bill for a specific table.
- As a user, I want to be able to check the status of a table (available or not).
- As a user, I want to be able to check the status of all tables.
- As a user, I want to be able to add a customer to the end of waitlist.
- As a user, I want to be able to remove a first customer from the waitlist.
- As a user, I want to be able to save the entire state of FeastApp to a file.
- As a user, I want to be able to load a state of FeastApp from file.

## Instructions for Grader

- You can see my visual component, which is FeastAppImage, at the pop-up window when the FeastApp starts.
- You can choose to start a new restaurant state or reload the previous state 
from file on a pop-up window when FeastApp starts.
- You can choose to save the current restaurant state or not on a pop-up 
window when closing FeastApp.
- You can add items to a chosen table in the restaurant by clicking menu Orders -> Take order.
- You can remove items for a chosen table in the restaurant by clicking menu Orders -> Remove item.
- You can check out for a chosen table in the restaurant by clicking menu Orders -> Check out. 
- You can add customers to the waitlist by clicking menu Waitlist -> Add customer.
- You can remove customers from the waitlist by clicking menu Waitlist -> Remove customer.
- You can change your target table (the table you want to do something with) by 
choosing one in the ComboBox at the center of window.


## Phase 4: Task 2 (a representative sample of EventLog)

- Sat Apr 08 15:00:04 PDT 2023 
- Dave(6046714354) has been added to the waitlist. 
- Sat Apr 08 15:00:18 PDT 2023 
- Jason(7789846966) has been added to the waitlist. 
- Sat Apr 08 15:00:36 PDT 2023 
- Alex(6049687228) has been added to the waitlist. 
- Sat Apr 08 15:01:04 PDT 2023 
- Joy(2369906038) has been added to the waitlist. 
- Sat Apr 08 15:01:43 PDT 2023 
- Item: 豉油鸡 ($30.0) is added for Table 1 
- Sat Apr 08 15:02:05 PDT 2023 
- Item: Sushi ($20.3) is added for Table 1 
- Sat Apr 08 15:02:47 PDT 2023 
- Item: Butter Chicken ($12.3) is added for Table 1 
- Sat Apr 08 15:03:09 PDT 2023 
- Item: 炒面 ($22.1) is added for Table 1 
- Sat Apr 08 15:04:17 PDT 2023 
- Item: 炒面 ($22.1) is removed for Table 1 
- Sat Apr 08 15:04:42 PDT 2023 
- Item: BBQ Pork ($23.5) is added for Table 3 
- Sat Apr 08 15:04:50 PDT 2023 
- Dave(6046714354) has been removed from the waitlist. 
- Sat Apr 08 15:04:53 PDT 2023 
- Jason(7789846966) has been removed from the waitlist.

## Phase 4: Task 3 (refactoring plan)

If I had more time to work on my project (FeastApp), I would refactor the GraphicalUIFeastApp class 
(as well as the ConsoleBasedUIFeastApp class if needed). The current implementation of the GraphicalUIFeastApp class 
is responsible for a wide range of tasks, including displaying JPanels, 
creating all JMenus and JMenuItems, adding them to the main JFrame, listening for user actions, 
and updating the FeastApp state and all JPanels. As a result, the GraphicalUIFeastApp class has become 
overly complex, with low cohesion, making it difficult to read and less reusable.

To address this issue, I propose breaking down the GraphicalUIFeastApp class into smaller,
more focused classes that each handle a specific aspect of the application's user interface, 
which will help to improve the overall cohesion of the codebase and make it easier to maintain 
and extend in the future. For example, we could create separate classes to handle the creation and management 
of JMenus and JMenuItems, the display of JPanels, and the processing of user actions. By dividing these 
responsibilities among smaller, more specialized classes, we can achieve a more modular and flexible 
design that is easier to understand and modify.
