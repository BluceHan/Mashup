package com.flinker.bean;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/27
 * @company:
 */
public class Album {
    private String title;
    private String id;
    private String img;

    public Album() {
    }

    public Album(String title, String id, String img) {
        this.title = title;
        this.id = id;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
