package kg.attractor.java.server;

import java.io.IOException;

public class Lesson44Server {

    private final BaseServer server;

    public Lesson44Server() throws IOException {
        server = new BaseServer(9889);
    }

    public void start() {
        server.start();
    }
}