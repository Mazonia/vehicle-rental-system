# ApexDrive: Enterprise Vehicle Rental System

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java 11+](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven Build](https://img.shields.io/badge/Build-Maven-red.svg)](pom.xml)
[![Code of Conduct](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)

**ApexDrive Vehicle Rental System** is an object-oriented Java application and interactive Web portal for managing vehicle fleets, customer bookings, loyalty reward programs, and rental transaction histories.

---

## ✨ Features

- 🚘 **Multi-Category Fleet**: Cars, Trucks, and Motorcycles with customizable daily rates.
- 💎 **Tiered Loyalty Program**: Earn points per rental day to unlock Silver, Gold, and Platinum status.
- 📜 **Transaction Auditing**: Automated transaction generation using Builder design pattern.
- 🎨 **Glassmorphism Web UI**: Interactive browser dashboard (`index.html`) for offline visual booking & estimation.
- 🧪 **Unit Test Coverage**: Automated JUnit 5 test suite for domain models and rental rules.

---

## 🏗️ Architecture

- `com.rental.Vehicle` (Abstract Base Class)
  - `com.rental.Car`
  - `com.rental.Truck`
  - `com.rental.Motorcycle`
- `com.rental.Customer` & `com.rental.LoyaltyProgram`
- `com.rental.RentalTransaction` (Builder Pattern)
- `com.rental.RentalAgency` (Service Orchestrator)

---

## 🚀 Quick Start

### 1. Launch Web Dashboard
Open [`index.html`](index.html) in any modern browser to access the interactive web interface.

### 2. Build & Run Java Backend
```bash
# Compile and test with Maven
mvn clean test

# Package JAR file
mvn clean package
```

---

## 🛡️ Governance & Security

- **[Code of Conduct](CODE_OF_CONDUCT.md)**
- **[Contributing Guide](CONTRIBUTING.md)**
- **[Security Policy](SECURITY.md)**
- **[License](LICENSE)**
