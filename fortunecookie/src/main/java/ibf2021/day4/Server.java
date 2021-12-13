package ibf2021.day4;

//import java.io.BufferedInputStream;
//import java.io.DataInputStream;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        Cookie newCookie = new Cookie();
        int socketPortNum = 0;
        String cookiePath = "";

        for (String arg : args) {
            if (arg.contains(".txt")) {
                newCookie.setCookiePath(arg);
                cookiePath = arg;
            } else if (arg.matches("\\d+")) {
                socketPortNum = Integer.parseInt(arg);
            }
        }
        
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        System.out.println("Opening new connection for port " + socketPortNum + "...");
        ServerSocket serverSocket = new ServerSocket(12345);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                CookieClientHandler worker = new CookieClientHandler(socket,cookiePath);
                threadPool.submit(worker);
            }
        } finally {
            serverSocket.close();
        }

        /*try (InputStream iS = socket.getInputStream()) {
            while (true) {
                DataInputStream diS = new DataInputStream(new BufferedInputStream(iS));

                OutputStream oS = socket.getOutputStream();
                PrintWriter pW = new PrintWriter(oS, true);
                while (true) {
                    String readData = diS.readUTF();
                    System.out.println("Client: " + readData);
                    if (readData.equals("get-cookie")) {
                        String cookieMessage = newCookie.getCookie();
                        String serverCommand = "cookie-text " + cookieMessage;
                        pW.println(serverCommand);
                    } else if (readData.equals("close")) {
                        break;
                    } else {
                        pW.println("Type 'get-cookie' to receive fortune cookies");
                    }
                }
                socket.close();
                System.out.println("Client disconnected, closing port 12345...");
                serverSocket.close();
                break;
            }
        }*/
    }
}

