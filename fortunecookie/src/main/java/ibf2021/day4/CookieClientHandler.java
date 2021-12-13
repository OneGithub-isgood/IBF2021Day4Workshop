package ibf2021.day4;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class CookieClientHandler implements Runnable {
    private final Socket socket;
    private String cookiePath;
    
    public CookieClientHandler(Socket socket, String cookiePath) {
        this.socket = socket;
        this.cookiePath = cookiePath;
    }

    @Override
    public void run() {
        Cookie newCookie = new Cookie();
        newCookie.setCookiePath(cookiePath);

        try (InputStream iS = socket.getInputStream()) {
            newCookie.prepareCookieBox();
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
            }
        } catch (IOException eIO) {
            System.out.println(eIO);
        }
    }
}
