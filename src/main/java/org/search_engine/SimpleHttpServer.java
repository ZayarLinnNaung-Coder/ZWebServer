package org.search_engine;

import org.search_engine.model.User;
import org.search_engine.processor.RequestProcessor;
import org.search_engine.service.UserService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleHttpServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            // Init built-in account
            initBuiltInAccount();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Create a new thread to handle the client request
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                System.out.println("Received request: " + request);

                // Extract the path from the request
                String[] requestParts = request.split(" ");
                String path = requestParts[1];
                System.out.println(path);

                // Read the HTML file and send it as the response
                if (path.equals("/")) {  // CASE 1: It sends index.html page to the client.
                    String filePath = "src/index.html";  // Default file to serve
                    File file = new File(filePath);  // Replace with the actual path to your HTML files
                    if (file.exists() && file.isFile()) {
                        System.out.println(file.getAbsolutePath());
                        String contentType = Files.probeContentType(Paths.get(filePath));
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("Content-Length: " + content.length());
                        out.println();
                        out.println(content);
                    } else {
                        // File not found
                        out.println("HTTP/1.1 404 Not Found");
                        out.println();
                    }
                } else {
                    // CASE 2: It sends a specific data to the client as an HTTP Response.
                    // ########################################################
                    // Calling the request processor

                    RequestProcessor.process(out, request);

                    // ########################################################

                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Init built-in accounts from file named `users.data`
     * */
    private static void initBuiltInAccount() {
        List<User> userList = new ArrayList<>();
        UserService userService = new UserService();

        // load accounts from `data/users.data` and them save all to memory called userDB
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/users.data"));
            String line = reader.readLine();

            while (line != null) {

                String[] data = line.split(" ");
                userList.add(new User(data[0], data[1]));

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // save all users
        userService.saveAllUsers(userList);
    }
}
