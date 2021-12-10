package ibf2021.day4;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class fortunecookie {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        String filePath = "";
        if (null != args && args.length > 0) {
            String[] argsList = filePath.split(" ");
            for (int argsPosition = 0; argsPosition < argsList.length; argsPosition++) {
                if (argsList[argsPosition].contains("text.txt")) {
                    filePath = argsList[argsPosition];
                }
            }
        }

        Cookie setCookie = new Cookie();
        setCookie.setCookiePath(filePath);

        Socket clientSocket = new Socket("localhost", 12345);

        try (OutputStream oS = clientSocket.getOutputStream()) {
            DataOutputStream doS = new DataOutputStream(new BufferedOutputStream(oS));
            BufferedReader bRInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader bRClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage = "";
            String serverMessage = "";
            while (true) {
                clientMessage = bRInput.readLine();
                doS.writeUTF(clientMessage);
                doS.flush();
                serverMessage = bRClient.readLine();
                String removePrefix = serverMessage.replace("cookie-text ","");
                System.out.println(removePrefix);
                if (clientMessage.equals("close")) {
                    clientMessage = bRInput.readLine();
                    doS.writeUTF(clientMessage);
                    doS.flush();
                    break;
                }
            } 
        } clientSocket.close();
    }
}
