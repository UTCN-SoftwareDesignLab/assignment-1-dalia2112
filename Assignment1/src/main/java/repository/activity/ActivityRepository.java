package repository.activity;

import model.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityRepository {

    List<Activity> findByUserId(long userId, Date fromDate, Date toDate);

    void addActivity(Activity activity);
}
