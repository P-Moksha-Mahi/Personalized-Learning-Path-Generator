
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        // Serve website files
        server.createContext("/", exchange -> {
            serveFile(exchange, "web/index.html", "text/html");
        });

        server.createContext("/style.css", exchange -> {
            serveFile(exchange, "web/style.css", "text/css");
        });

        server.createContext("/script.js", exchange -> {
            serveFile(exchange, "web/script.js",
                "application/javascript");
        });

        // Java roadmap generation API
        server.createContext("/api/roadmap", exchange -> {
            generateRoadmap(exchange);
        });

        server.start();

        System.out.println("PathFinder server started!");
        System.out.println("Open http://localhost:8080");
    }

    private static void serveFile(
        HttpExchange exchange,
        String fileName,
        String contentType
    ) throws IOException {

        byte[] content = Files.readAllBytes(Path.of(fileName));

        exchange.getResponseHeaders().set(
            "Content-Type", contentType
        );

        exchange.sendResponseHeaders(200, content.length);
        exchange.getResponseBody().write(content);
        exchange.close();
    }

    private static void generateRoadmap(
        HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equals("POST")) {
            sendResponse(exchange, 405, "Method not allowed");
            return;
        }

        String body = new String(
            exchange.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        Map<String, String> data = new HashMap<>();

        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                parts[0], StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                : "";

            data.put(key, value);
        }

        String name = data.getOrDefault("name", "");
        String goal = data.getOrDefault("goal", "");
        String level = data.getOrDefault("level", "");

        Student student = new Student(name, goal, level);

        name = student.getName();
        goal = student.getGoal();
        level = student.getSkillLevel();

        if (name.isBlank() || goal.isBlank() || level.isBlank()) {
            sendResponse(exchange, 400, "Missing student details");
            return;
        }

        String[][] topics;

        switch (goal) {

            case "Java Development":
                topics = new String[][] {
                    {"Java Basics", "Learn variables, data types and operators."},
                    {"OOP Concepts", "Learn classes, objects, inheritance and polymorphism."},
                    {"Exception Handling", "Learn how to handle program errors."},
                    {"Collections", "Understand ArrayList, HashMap and other collections."},
                    {"Java Projects", "Build practical Java applications."}
                };
                break;

            case "Web Development":
                topics = new String[][] {
                    {"HTML", "Learn webpage structure."},
                    {"CSS", "Learn webpage styling."},
                    {"JavaScript", "Learn interactive programming."},
                    {"Frontend Projects", "Build interactive websites."},
                    {"Backend Basics", "Learn servers and databases."}
                };
                break;

            case "Python Programming":
                topics = new String[][] {
                    {"Python Basics", "Learn variables and data types."},
                    {"Conditions and Loops", "Understand control statements."},
                    {"Functions", "Learn reusable functions."},
                    {"Data Structures", "Learn lists, tuples and dictionaries."},
                    {"Python Projects", "Build Python applications."}
                };
                break;

            default:
                topics = new String[][] {
                    {"Arrays", "Learn array operations."},
                    {"Strings", "Learn string manipulation."},
                    {"Linked Lists", "Understand linked lists."},
                    {"Stacks and Queues", "Learn linear data structures."},
                    {"Trees and Graphs", "Learn advanced data structures."}
                };
        }

        int start = 0;

        if (level.equals("Intermediate")) {
            start = 1;
        } else if (level.equals("Advanced")) {
            start = 3;
        }

        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"name\":\"").append(escape(name)).append("\",");
        json.append("\"goal\":\"").append(escape(goal)).append("\",");
        json.append("\"level\":\"").append(escape(level)).append("\",");
        json.append("\"topics\":[");

        for (int i = start; i < topics.length; i++) {

            if (i > start) {
                json.append(",");
            }

            json.append("{");
            json.append("\"title\":\"").append(escape(topics[i][0])).append("\",");
            json.append("\"details\":\"").append(escape(topics[i][1])).append("\"");
            json.append("}");
        }

        json.append("]}");

        sendResponse(exchange, 200, json.toString());
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    private static void sendResponse(
        HttpExchange exchange,
        int status,
        String response
    ) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
            "Content-Type", "application/json; charset=UTF-8"
        );

        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
