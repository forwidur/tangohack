package io.flux.pipe.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    private static final String projectsUrl = "https://polson.flux.io/p/aroJ3KnpdK8rrYkJO/api/"
            + "value/key/v/40cbd9274bcd75be09695b0ad6694737";
    private static final String auth_ = "auth=MTQzNTQ0ODQ5N3xfN09yNngxRFJRSVNHejF5ZDI0TDF2NXo0XzdJc"
            + "kxlV3dLT3huMXAtTVhHdlpFekNraDFTakFfMmxjUWRUalJNSk04RFc5TWEzX0kxTkhrUlp3WEt0SE9NZ0gya"
            + "kY2dkJDQy15ZDlsY0QySzZqbUlVRTJ4Wmd6alFQdEVmRlBFMHUzd1NUTXJlY1ZVckM5dkVxTzJ6MlUtMUpWb"
            + "2pVN0lFUzk1emVBTDFnRXNQV1NwRnZqUmpfN1h2OU9iWGhLSWJ4VXMwdHQ2dXJSQUdFVlZiUm5ydWduMXFJc"
            + "GR0dl80SURPTENPODhLd2J6YUtZMldBcmRhN2RZUkNnWmx3YXNBSjRzWWdnWmw2N0ViZ1p2Nko3blRTTE51S"
            + "kJXMzBKSXVyQmRTWnlZc091TVU0azhKUmFnLUU1RklyYm9vUWNqeUNjN2szeWFwaTI0VnVDSmNibjBzdlJqc"
            + "W1uM2F8QerFYL-vXba0gpnDZU2dO0YEu3pW_3pJ7U66buCmZZA=;";
    private static final String meta_ = "eyJhZ2VudCI6eyJwcm9ncmFtIjoiZ3Jhc3Nob3BwZXIiLCJwcm9ncmFtVmVyc2lvbiI6IllXNTVJQ3NnYjJ4a0lDWWdaR0YwWVE9PSIsInBsdWdpblZlcnNpb24iOiIxLjIuMyIsInBsYXRmb3JtIjoib3N4LzEwLjkifSwic291cmNlIjp7Im1haW5GaWxlIjoiTXlTY3JpcHQuZ2gifX0=";
    private static URL url_;

    static {
        url_ = null;
        try {
            url_ = new URL(projectsUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection conn;

    public Client () {
    }

    public void PutKey(String val) {
        try {
            conn = (HttpURLConnection) url_.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Cookie", auth_);
            conn.setRequestProperty("Flux-Meta", meta_);

            OutputStream os = conn.getOutputStream();
            os.write(val.getBytes());
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String result = null;
            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();

            Log.e("WHATEV", result);
            conn.disconnect();
        } catch (IOException e) {
            Log.e("WHATEV", "FAILED: " + e);
            System.err.println("Could not connect to " + projectsUrl + ": " + e.getLocalizedMessage());
        }
    }
}
