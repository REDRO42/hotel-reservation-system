# Hotel Reservation System

This project is a hotel reservation management system developed with Java. It is designed as a desktop application using Swing interface.

## 🚀 Features

- Room management (add, edit, delete)
- Reservation operations
- Pricing management
- Customer information tracking
- Date-based reservation control
- MySQL database integration

## 🛠️ Technologies

- Java 11
- Swing (GUI)
- MySQL 8.0
- Maven
- JUnit 5 (Unit Testing)
- Mockito (Test Mocking)
- Jackson (JSON processing)

## 📋 Requirements

- JDK 11 or higher
- MySQL 8.0
- Maven 3.6 or higher

## 🔧 Installation

1. Clone the project:
```bash
git clone https://github.com/redro42/hotel-reservation-system.git
```

2. Navigate to the project directory:
```bash
cd hotel-reservation-system
```

3. Install Maven dependencies:
```bash
mvn clean install
```

4. Set up MySQL database:
   - Start your MySQL server
   - Create the database schema
   - Configure connection settings

5. Run the application:
```bash
mvn exec:java
```

## 🧪 Tests

The project includes comprehensive unit tests. To run the tests:

```bash
mvn test
```

Test coverage:
- Model classes (Room, Reservation, PriceSetting)
- Database operations
- Business logic controls

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── otelrezervasyon/
│   │           ├── dao/         # Database access layer
│   │           ├── model/       # Data models
│   │           ├── ui/          # User interface
│   │           └── util/        # Utility classes
│   └── resources/              # Resource files
└── test/
    └── java/
        └── com/
            └── otelrezervasyon/
                └── model/       # Unit tests
```

## 🤝 Contributing

1. Fork this repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Contact

Project Owner - [@redro42](https://github.com/redro42)

Project Link: [https://github.com/redro42/hotel-reservation-system](https://github.com/redro42/hotel-reservation-system) 
