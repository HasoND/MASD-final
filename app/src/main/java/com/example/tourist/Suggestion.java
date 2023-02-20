package com.example.tourist;

public class Suggestion {

    private String title;
    private String suggestUrl;
    private String suggestThrumb;

    public Suggestion(String title, String suggestUrl, String suggestThrumb) {
        this.title = title;
        this.suggestUrl = suggestUrl;
        this.suggestThrumb = suggestThrumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuggestUrl() {
        return suggestUrl;
    }

    public void setSuggestUrl(String suggestUrl) {
        this.suggestUrl = suggestUrl;
    }

    public String getSuggestThrumb() {
        return suggestThrumb;
    }

    public void setSuggestThrumb(String suggestThrumb) {
        this.suggestThrumb = suggestThrumb;
    }
}
