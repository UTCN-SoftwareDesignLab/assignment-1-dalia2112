package service.activity;

import model.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    List<Activity> findByUserId(long userId, Date fromDate, Date toDate);

    void addActivity(Activity activity);

    String formatActivities(List<Activity> activities);
}
