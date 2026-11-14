# PathFinder – Personalized Learning Path Generator

## About the Project
PathFinder is a Java-based web application that generates personalized learning roadmaps for students based on their learning goals and current skill levels.

It helps students organize their learning journey into manageable steps and track their progress.

## Features
- Personalized learning paths based on student details.
- Supports Java Development, Web Development, Python Programming, and Data Structures.
- Three skill levels: Beginner, Intermediate, and Advanced.
- Interactive roadmap with completion checkboxes.
- Real-time progress tracking with a percentage-based progress bar.
- Colourful and user-friendly interface.

## Technologies Used
- **Java** – Backend logic and roadmap generation.
- **HTML** – Webpage structure.
- **CSS** – Styling and design.
- **JavaScript** – Frontend interaction and progress tracking.
- **JDK HTTP Server** – Handles communication between the frontend and backend.

## Project Structure
```text
PathFinder/
├── src/
│   ├── App.java
│   └── Student.java
├── web/
│   ├── index.html
│   ├── style.css
│   └── script.js
└── README.md
```

## How to Run the Project

1. Install Java JDK.
2. Open the project folder in VS Code.
3. Open the terminal in the project folder.
4. Run the following command:

```bash
java --add-modules jdk.httpserver src/App.java
```

5. Open your browser and visit:

```text
http://localhost:8080
```

## Future Enhancements
- Save student progress between sessions.
- Add more learning goals and topics.
- Include recommended learning resources.
- Add student login and registration.

## Author
Developed as a Java Project-Based Learning project.
