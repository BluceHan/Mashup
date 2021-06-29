package com.flinker.web.service.impl;

import com.google.gson.*;
import com.flinker.bean.CommonResult;
import com.flinker.bean.Album;
import com.flinker.web.service.MashUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/23
 * @company:
 */
@Slf4j
@Service
public class MashUpServiceImpl implements MashUpService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public String doConvert(String url) {
        //params of HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.add("user-agent", "Apache-HttpClient/4.5.13 (Java/1.8.0_181)");
        headers.add("Accept", "application/json");

        //Encapsulation
        HttpEntity<Gson> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    @Override
    public String parseUrl(String mbid) {
        String musicUrl = "https://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
        String doExchange = doConvert(musicUrl);
        CommonResult commonResult = new CommonResult();

        commonResult.setMbid(mbid);
        JsonObject jsonObject = new JsonParser().parse(doExchange).getAsJsonObject();

        JsonArray relations = jsonObject.getAsJsonArray("relations");
        String wikidata = null;

        //traverse all the relations and find the one whose type equals "wikidata"
        for (int i = 0; i < relations.size(); i++) {
            JsonObject wikidataUrl = relations.get(i).getAsJsonObject();
            if ("wikidata".equals(wikidataUrl.get("type").toString().replaceAll("\"", ""))) {
                wikidata = wikidataUrl.getAsJsonObject("url").get("resource").getAsString();
                break;
            }
        }
        String description = parseWikidata(wikidata.substring(wikidata.lastIndexOf('/')+1));
        commonResult.setDescription(description);

        JsonArray releases = jsonObject.getAsJsonArray("release-groups");
        List<Album> albums = Collections.synchronizedList(new ArrayList<>());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (int i = 0; i < releases.size(); i++) {
            JsonObject object = releases.get(i).getAsJsonObject();

            String id = object.get("id").getAsString();
            String title = object.get("title").getAsString();
            executor.execute(() -> {
                Album album = new Album();
                String s = parseCover(id);
                album.setImg(s == null?"not fond 404":s);
                album.setId(id);
                album.setTitle(title);
                albums.add(album);
            });
        }
        executor.shutdown();
        while (executor.getCompletedTaskCount() < releases.size()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(albums.size());
        commonResult.setAlbums(albums);
        return new Gson().toJson(commonResult);
    }

    /**
     * parse the content of Wikidata
     * @param identifier identifier like Q11649
     * @return title linking to WikiPedia
     */
    private String parseWikidata(String identifier) {
        String wikipediaUrl = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+identifier+"&format=json&props=sitelinks";
        String doExchange = doConvert(wikipediaUrl);
        JsonObject jsonObject = new JsonParser().parse(doExchange).getAsJsonObject();
        JsonObject entities = jsonObject.getAsJsonObject("entities").getAsJsonObject(identifier);
        JsonObject sitelinks = entities.getAsJsonObject("sitelinks");
        JsonObject enwiki = sitelinks.getAsJsonObject("enwiki");
        String title = enwiki.get("title").getAsString();
        return parseWikipedia(title);
    }

    /**
     * parse the content of Wikipedia
     * @param title
     * @return description of an artist
     */
    private String parseWikipedia(String title) {
        String wikiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles="+title;
        String doExchange = doConvert(wikiUrl);
        JsonObject jsonObject = new JsonParser().parse(doExchange).getAsJsonObject();
        JsonObject query = jsonObject.getAsJsonObject("query");
        JsonObject pages = query.getAsJsonObject("pages");
        Iterator<String> iterator = pages.keySet().iterator();
        String pageid = "";
        if (iterator.hasNext()){
            pageid = iterator.next();
        }
        JsonObject test = pages.getAsJsonObject(pageid);
        JsonElement extract = test.get("extract");
        return extract.toString().substring(extract.toString().indexOf("<p>"));
    }

    /**
     * parse Cover Art Archive
     * @param id id of the cover img
     * @return url path to the cover img
     */
    private String parseCover(String id) {
        String coverUrl = "https://coverartarchive.org/release-group/"+id;
        String exchange = null;
        try {
            exchange = doConvert(coverUrl);
        } catch (Exception exception) {
            return null;
        }
        JsonObject jsonObject = new JsonParser().parse(exchange).getAsJsonObject();
        JsonArray images = jsonObject.getAsJsonArray("images");
        String img = images.get(0).getAsJsonObject().get("image").getAsString();
        return img;
    }

}


