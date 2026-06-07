# Document Versioning System

## Overview

Document Versioning System is a Java-based desktop application that allows users to create, edit, save, and manage multiple versions of various documents. The system maintains a complete saved version history, enabling users to restore previous saved versions whenever required.

The project demonstrates concepts such as file management, version control, MongoDB integration, and Java GUI development.

## Features

* Create new documents
* Open existing documents
* Save document changes
* Automatically maintain document versions
* View version history
* Restore previous versions
* MongoDB-based storage
* User-friendly graphical interface

## Tech Stack

* Java
* Maven
* MongoDB
* Java Swing
* Git & GitHub

## Project Structure

```text
src/main/java
├── database
├── formatting
├── services
├── ui_dropdowns
└── ui_main
```

## How It Works

1. A user creates or opens a document.
2. Changes are made through the editor interface.
3. If a user creates a document then it will be saved by save as operation
4. Else user can save the version by save as version operation. 
5. Previous versions are preserved in the mongoDB database.
6. Users can browse version history and restore any earlier version.
7. Users can also download their chosen version through download option in version section.

## Prerequisites

* Java JDK 17+ (or your version)
* Maven
* MongoDB running locally on port 27017

## Installation

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/document-versioning-system.git
```

Navigate to the project directory:

```bash
cd document-versioning-system
```

Build the project:

```bash
mvn clean install
```

Run the application using your IDE or Maven.

## Screenshots

### Document Screenshot

![Home Screen](screenshots/Image1.png)

### Screenshot of version of the same document

![Version History](screenshots/Image2.png)

### Can open recent files

![Restore Version](screenshots/Image3.png)

## Future Improvements

* User authentication
* Cloud storage integration
* Search functionality
* Rich text editing support


