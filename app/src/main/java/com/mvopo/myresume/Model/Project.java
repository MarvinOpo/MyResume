package com.mvopo.myresume.Model;

import java.util.List;

/**
 * Created by mvopo on 7/18/2018.
 */

public class Project {
    private String title, body;
    private int[] images;
    private int currentPage = 0;

    public Project(String title, String body, int[] images) {
        this.title = title;
        this.body = body;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int[] getImages() {
        return images;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getImageCount(){
        return images.length;
    }

    public void incrementPage() {
        this.currentPage++;
    }
}
