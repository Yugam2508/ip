# Jarvis - Personal Task Manager

Jarvis is a personal assistant chatbot designed to help you manage your tasks efficiently. It supports ToDo tasks, Deadlines, and Events, with persistent storage and smart search capabilities.

## Features

- **Task Management**: Add, delete, and mark tasks as complete
- **Multiple Task Types**: Support for ToDo, Deadline, and Event tasks
- **Date Handling**: Smart date parsing with LocalDate
- **Search Functionality**: Find tasks by keyword
- **Persistent Storage**: Automatic save and load from file
- **Motivational Quotes**: Get random motivational quotes with the cheer command

## Quick Start

1. Ensure you have Java 21 installed
2. Download the latest release JAR file
3. Run the application: `java -jar jarvis.jar`
4. Start managing your tasks!

## Usage Examples

**Add a todo:**
```
todo Read a book
```

**Add a deadline:**
```
deadline Submit report /by 2024-03-15
```

**Add an event:**
```
event Team meeting /from 2024-03-10 2pm /to 2024-03-10 4pm
```

**List all tasks:**
```
list
```

**Find tasks:**
```
find book
```

**Mark task as done:**
```
mark 1
```

**Delete a task:**
```
delete 2
```

**Get motivation:**
```
cheer
```

**Exit:**
```
bye
```

## Building from Source

**Prerequisites:**
- JDK 21
- Gradle

**Build commands:**

Build the project:
```
./gradlew build
```

Run tests:
```
./gradlew test
```

Create JAR file:
```
./gradlew shadowJar
```

The JAR file will be created in `build/libs/`

## Project Structure
```
src/
├── main/
│   └── java/
│       └── jarvis/
│           ├── Jarvis.java          (Main class)
│           ├── parser/
│           │   ├── Parser.java
│           │   └── CommandType.java
│           ├── storage/
│           │   └── Storage.java
│           ├── tasks/
│           │   ├── Task.java
│           │   ├── Todo.java
│           │   ├── Deadline.java
│           │   ├── Event.java
│           │   ├── TaskList.java
│           │   └── JarvisException.java
│           └── ui/
│               └── Ui.java
└── test/
```

## Acknowledgements

- Based on the CS2103DE Individual Project
- SE-EDU coding standards
- Java LocalDate documentation

---

*Project for CS2103DE Software Engineering*