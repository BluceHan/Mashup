package com.flinker.web.service;



import java.net.MalformedURLException;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/23
 * @company:
 */
public interface MashUpService {

    /**
     * an original method parsing the url of MusicBrainz
     * @param address url
     * @param param parameters
     * @return json string
     * @throws MalformedURLException
     */
    public String parseUrl(String address, String param) throws MalformedURLException;

    /**
     * a method parsing the url of MusicBrainz, and returing the final result of the api
     * @param url MusicBrainz的url
     * @return json string
     */
    public String parseUrl(String url);

    /**
     * converting the content of web page into json string
     * @param url url to a web page
     * @return
     */
    public String doConvert(String url);
}
