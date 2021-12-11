package ibf2021.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Cookie {
    private String cookiePath = "";
    
    List<String> cookieBox = new ArrayList<>();

    public void setCookiePath(String path) {
        this.cookiePath = path;
    }

    public void prepareCookieBox() throws IOException {
        //String cookieFileName = cookiePath;
        String cookieMessage;
        File dbFile = new File(cookiePath);
        try (Reader newReader = new FileReader(dbFile)) {
            BufferedReader newBR = new BufferedReader(newReader);
            while (null != (cookieMessage = newBR.readLine())) {
                cookieBox.add(cookieMessage);
            }
        } catch (FileNotFoundException fnf2) {}
    }

    public String getCookie () {
        Integer numCookie = cookieBox.size();
        Integer randomRoll = (int)(Math.random()*(numCookie-0));
        return cookieBox.get(randomRoll);
    }

}
