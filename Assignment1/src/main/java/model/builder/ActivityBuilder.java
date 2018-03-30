package model.builder;

import model.Account;
import model.Activity;

import java.util.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder() {
        activity = new Activity();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setUserId(Long id) {
        activity.setUserId(id);
        return this;
    }

    public ActivityBuilder setDescription(String description) {
        activity.setDescription(description);
        return this;
    }

    public ActivityBuilder setDate(Date date) {
        activity.setDate(date);
        return this;
    }


    public Activity build() {
        return activity;
    }
}
