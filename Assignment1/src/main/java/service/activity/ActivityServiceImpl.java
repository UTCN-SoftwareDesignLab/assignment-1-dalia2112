package service.activity;

import model.Activity;
import repository.activity.ActivityRepository;

import java.util.Date;
import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> findByUserId(long userId, Date fromDate, Date toDate) {
        return activityRepository.findByUserId(userId, fromDate, toDate);
    }

    @Override
    public void addActivity(Activity activity) {

        activityRepository.addActivity(activity);
    }

    public String formatActivities(List<Activity> activities) {
        String result = "";
        for (Activity activity : activities) {
            result += activity.getDescription() + ", date: " + activity.getDate() + "\n";
        }
        return result;
    }
}
