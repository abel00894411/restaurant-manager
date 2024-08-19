# Restaurant Manager

Restaurant Manager is a web-based application for effective order management in restaurants. It offers features that assist waiters, cooks and administrators in their daily duties.

## Main features

- Register, update and delete employee data, supporting three types of employees: waiters, cooks, and administrators.
- Populate the restaurant's menu, organizing the items into categories.
- Take customer orders, which are automatically assigned to a waiter for follow-up.
- Fill orders with menu items, which are automatically assigned to cooks for preparation.
- Order and item assignment is designed to balance the workload evenly among employees.
- Cooks specialize in a menu category and will only be assigned items within their category.
- Waiters can view the status of items in their assigned orders in real-time.
- Customers can generate PDF invoices for their purchases.

## Technical details

A REST API was implemented using Spring Boot and Java. The Spring WebSocket dependency was used for real-time connections, with a mechanism to track active employees and their respective roles.

The application's frontend was developed using React with both JavaScript and TypeScript. Singleton classes were implemented to manage WebSocket connections and data structures related to the features for the waiter and cook users.

## Screenshots

## How to run it

1. Clone the repository.
2. Create the database by executing the script `database/db.sql` on a MySQL instance. By default, an admin account with the RFC "admin" and the password "123" is created in the database. For security purposes, be sure to update these credentials.
3. Configure and start the Java backend (`restaurante-api` folder). You will need to enter your database connection details in `src/main/resources/application.properties`.
4. Build the frontend by navigating to the `frontend` folder, installing the required NPM dependencies with `npm install`, and then running `npm run build`.
5. Start a web server suitable for single-page applications and point it to the `frontend/dist` folder, which contains the built files. Alternatively, use `npm run preview`.

## Developers

- Abel Pintor García (API Designer, Backend Developer, Database Designer)
- Gerardo Arzate Paredes (API Designer, UI/UX Designer, Frontend Developer)
- Fernanda Daniela Pérez Álvarez (Database Designer, Backend Developer)
- Francisco Luna Fernández (Frontend Developer)
