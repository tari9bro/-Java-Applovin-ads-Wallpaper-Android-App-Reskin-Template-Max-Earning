package com.varxiodz.akebichanwallpapers.notification;

public class NotificationDetails {
    private String title;
    private String message;
    private String url;
    private int icon;

    public NotificationDetails(String title, String message, String url, int icon) {
        this.title = title;
        this.message = message;
        this.url = url;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public int getIcon() {
        return icon;
    }
}


