package com.flinker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/23
 * @company:
 */
public class UrlUtils {

    private UrlUtils() {}

    public static String parseUrl(URL url) {
        String result = "";
        BufferedReader read = null;

        try {
            // create connection
            URLConnection connection = url.openConnection();
            // set properties
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();

            //get all the headers
            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // BufferedReader for istream to read the response of URL
            read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
