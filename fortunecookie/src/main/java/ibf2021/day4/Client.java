package ibf2021.day4;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        int socketPortNum = 0;
        String hostAddress = "";
        
        for (String arg : args) {
            if (arg.contains(":")) {
                String[] hostPortConfig = arg.split(":");
                for (String configNet : hostPortConfig) {
                    if (configNet.matches("\\d+")) {
                        socketPortNum = Integer.parseInt(configNet);
                    } else {
                        hostAddress = configNet;
                    }
                }
            }
        }

        Socket clientSocket = new Socket(hostAddress, socketPortNum);

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
                if (null != serverMessage) {
                    String removePrefix = serverMessage.replace("cookie-text ","");
                    System.out.println(removePrefix);
                }
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
