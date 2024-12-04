# Technical test 


## Features

-   **User Registration & Login**: Users can register and log in to the system.
-   **Room Reservation**: Users can make room reservations.
-   **Automated Email Notifications**: The system sends automated emails for booking confirmations, reminders, and other notifications.
-   **Reservation Management**: Manage reservations with specific conditions.
-   **Room Status Management**: View and update the status of hotel rooms.

## Technologies Used

-   **Backend**: Java 21, Spring Boot , Maven
-   **Frontend**: Angular , Typescript, TailwindCSS, Bootstrap
-   **Database**: PostgreSQL

## Installation

### Prerequisites

-   Java 21 or later
-   Node.js and npm
-   PostgreSQL database

### Backend Setup

1. Clone the repository:

    ```sh
    git clone https://github.com/MouhebHamza/technical-test.git
    cd technical-test/backend
    ```

2. Update the `application.properties` file with your PostgreSQL database configuration:

    ```properties
    server.port=8081
    spring.datasource.url=jdbc:postgresql://localhost:5432/PropertyManagementWebSiteDB
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

3. Build and run the Spring Boot application:
    

### Frontend Setup

1. Navigate to the frontend directory:

    ```sh
    cd technical-test/frontend
    ```

2. Install dependencies:

    ```sh
    npm install
    ```

3. Start the Angular development server:

    ```sh
    ng serve
    ```

4. Open your browser and navigate to `http://localhost:4200`.

# Project Documentation

## Design Decisions

- **Modular Architecture**: 
  - The project follows a modular approach, separating concerns into distinct components (UI), services (business logic), and models (data representation). This enhances maintainability and scalability, making it easier to extend or modify individual parts of the application.
  
- **State Management**: 
  - Angular's two-way data binding and service layers are used for managing the application's state, ensuring consistency of data across components. Services handle data retrieval and manipulation, while components bind data to the view for seamless user interaction.

- **Virtual Scrolling**:
  - Virtual scrolling is implemented in the reservation cards section to optimize performance when displaying large sets of data. This approach ensures that only the visible items are rendered, significantly reducing DOM elements and improving load times.

- **Responsive Layout**:
  - Tailwind CSS is utilized for creating a responsive, mobile-first layout. The UI adapts smoothly across different screen sizes, ensuring a consistent user experience on both mobile devices and desktop browsers.

- **Error Handling**:
  - A global exception handler is implemented for consistent error management. Custom exceptions are thrown in the backend when errors occur, and meaningful error messages are returned to the frontend for better user feedback.

## API Integration

- **Backend Communication**: 
  - The Angular application interacts with RESTful APIs to fetch and submit data related to properties and reservations. This communication is handled via Angular's `HttpClientModule`, ensuring easy handling of HTTP requests and responses.


- **Data Transformation**:
  - Data fetched from the backend is typically in raw form. Angular services and models are used to transform this data into user-friendly formats. For instance, properties and reservations are converted from entities to DTOs (Data Transfer Objects) before being displayed in the UI.

This architecture and integration approach ensures a maintainable, scalable, and efficient web application with smooth data flow and user interaction.

# Test Cases Documentation

## Backend Tests (Java)

The source code for the backend tests can be found in the `test` directory of the Java project.

### 1. PropertyControllerTest

- **Test Case: testGetAllProperties**
  - **Description**: Verifies that the `GET /api/properties` endpoint correctly returns a list of properties with the expected `buildingName`.
  - **Expected Outcome**: The response should include a property with the `buildingName` set to "Test Property".
  - **Status**: Passed

- **Test Case: testCreateReservation**
  - **Description**: Verifies that the `POST /api/reservations` endpoint successfully creates a reservation and returns the `propertyId` in the response.
  - **Expected Outcome**: The status code should be `201` and the response should contain the `propertyId`.
  - **Status**: Passed

- **Test Case: testGetReservationById**
  - **Description**: Verifies that the `GET /api/reservations/{id}` endpoint correctly returns a reservation for the provided `id`.
  - **Expected Outcome**: The reservation with the given `id` should be returned with the `propertyId`.
  - **Status**: Passed

### 2. ReservationServiceTest

- **Test Case: testFindAllProperties**
  - **Description**: Validates that the `findAllProperties()` method in `PropertyService` correctly converts entities to DTOs and returns the expected data.
  - **Expected Outcome**: The list of properties should be returned with the correct `buildingName` and property data.
  - **Status**: Passed

---

## Frontend Tests (Angular - Cypress)

The source code for the frontend tests can be found in the `/cyprus/e2e/` directory.

### Property List Component
  - **Status**: Passed

---

### PropertyService E2E Tests
  - **Status**: Failed

---

### Reservation List Component
  - **Status**: Passed

---

### ReservationService E2E Tests
  - **Status**: Failed
