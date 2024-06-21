
# Bookshop Spring Boot Project

This project consists of three modules:

1. **api-gen**: Generates API from OpenAPI specification using the OpenApiTools plugin.
2. **bookshop**: Allows users to view, filter, and purchase books. Admins can add, edit, and delete books.
3. **book-order**: Manages book orders based on visit counts and generates PDF order reports.

## Table of Contents

- [Modules](#modules)
  - [api-gen](#api-gen)
  - [bookshop](#bookshop)
  - [book-order](#book-order)

## Modules

### api-gen

This module generates the API from an OpenAPI specification.

- **OpenAPI Spec**: \`src/main/resources/api-spec.yaml\`

### bookshop

This module allows users to view, filter, and purchase books. It also allows admins to manage books.

- **Features**:
  - View and filter books
  - Purchase books (logged-in users)
  - Manage books (admins)

- **Dependencies**:
  - Spring Boot Starter Security
  - Spring Boot Starter Data JPA
  - Spring Cloud Starter OpenFeign
  - api-gen (local dependency)

### book-order

This module manages book orders based on visit counts and generates PDF order reports.

- **Features**:
  - Accept list of books with visit counts
  - Generate order quantities based on visit counts
  - Generate PDF order reports

- **Dependencies**:
  - Spring Boot Starter Data JPA
  - iText (PDF generation)
  - api-gen (local dependency)