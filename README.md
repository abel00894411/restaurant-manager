# Restaurant Manager

Restaurant Manager is a web-based application for effective order management inside restaurants. It offers features that aim to assist waiters, cookers and administrators in their daily duties.

## Main features

- Register, update and delete employee data, with support for three types of employees: waiters, cookers and administrators.
- Fill the restaurant's menu, organizing the items in categories.
- Take customer orders, that will be automatically assigned to a waiter for follow-up.
- Fill orders with menu items, that will be automatically assigned to cookers for their preparation.
- Order and items assignation is designed to keep workload equal between employees.
- Cookers specialize in a menu category, and will only be assigned items in their category.
- Waiters will see the status of items in their assigned orders in real time.
- Customers will be able to generate a PDF invoice for their purchases.

## Tecnical details

A REST API was implementend using Spring Boot and Java. The Spring Web Socket dependency was used for connection in real time, with the implementation of a mechanism to count active employees and their respective roles.

The frontend of the application was developed using React and both JavaScript and TypeScript. Singleton classes were implemented to manage websocket connections and data structures related to the features of the waiter and cooker user.

## Screenshots

## How to run it

1. Clone the repository.
2. Create the database by executing the script `database/db.sql` on a MySQL instance. By default, an admin account with RFC "admin" and password "123" is registered in the database. For security purposes, be sure to edit its credentials.
3. Configure and start the Java backend (`restaurante-api` folder). You will need to enter your database connection details inside `src/main/resources/application.properties`.
4. Build the frontend (`frontend` folder) by first installing NPM dependencies and then running `npm run build`.
5. Start a web server on the `frontend/dist` folder.

## API documentation

## Developers

- Abel Pintor García (API design, backend programming, database)
- Gerardo Arzate Paredes (API design, UI/UX design, frontend programming)
- Fernanda Daniela Pérez Álvarez (Database, backend programming)
- Francisco Luna Fernández (Frontend programming)
