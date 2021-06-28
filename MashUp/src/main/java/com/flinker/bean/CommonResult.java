package com.flinker.bean;


import java.util.List;

public class CommonResult {
    private String mbid;
    private String description;
    private List<Album> albums;

    public CommonResult() {
    }

    public CommonResult(String mbid, String description, List<Album> albums) {
        this.mbid = mbid;
        this.description = description;
        this.albums = albums;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
