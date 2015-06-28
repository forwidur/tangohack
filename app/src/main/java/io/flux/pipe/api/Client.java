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
    private static final String key = "40fd2eab8722dddc346e8d5e47f04b9d";
    private static final String projectsUrl = "https://polson.flux.io/p/aroJ3KnpdK8rrYkJO/api/"
            + "value/key/v/" + key;
    private static final String auth_ =
            "auth=MTQzNTUxMDYxMnxuUm9ZVzcxaGdtdVZsOEFNUG5DS2lJaWhDaVZLUUlQRE9RR3ZoNXNHWXY4UEQxYmtuQ0p6WXFJUk1tQmpHSjdLRmRGT2FDeUtxMTM4VVNhekhETlN0OERCb29GTXoyaE1zWFhOVXVVUVBTYlIxR3gyVjFYMC1MUUF2TFRSOTUtR3V5UmdZRGVtTDN3Mk5KM1E4cmNSNnYxNUdVakpmdU42U09VdXZpaDZjaHozWkNJaTltaFRTTnN4SXo1TEFmUFhoT2d6Z0xqcURCVjIwTHcwdVM1dThBYzFvR0ttbV9uSlRfeGhPXzJVSHUxcmJsOE4zOVFQQnhQV1JoVk9FZDIyLUc1SXp1YWw4aFFOOFlMekg1Wk03d0IzTV92UXBtdVIzakhaRW9zRE9OQ2h0X255TnhrTjQzOGMzUHpDVmYwSGU3cWJINzY5alV3WDRuTWlnOXRKczFycURMVUx8Eqrf8fht4fUhDT9qFWT9DWcoQTmZGfk0X1bSJf9TSnk=; Path=/;";
    private static final String meta_ =
            "eyJhZ2VudCI6eyJwcm9ncmFtIjoiZ3Jhc3Nob3BwZXIiLCJwcm9ncmFtVmVyc2lvbiI6IllXNTVJQ3NnYjJ4a0lDWWdaR0YwWVE9PSIsInBsdWdpblZlcnNpb24iOiIxLjIuMyIsInBsYXRmb3JtIjoib3N4LzEwLjkifSwic291cmNlIjp7Im1haW5GaWxlIjoiVGFuZ28ifX0=";
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
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", auth_);
            conn.setRequestProperty("Flux-Meta", meta_);

            OutputStream os = conn.getOutputStream();
            os.write(val.getBytes());
            os.flush();
            os.close();
            conn.getInputStream().close();
            conn.disconnect();
        } catch (IOException e) {
            Log.e("WHATEV", "FAILED: " + e);
            System.err.println("Could not connect to " + projectsUrl + ": " + e.getLocalizedMessage());
        }
    }
}
