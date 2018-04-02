package repository.activity;

import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.AuthenticationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Tables.ACTIVITY;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Activity> findByUserId(long userId, Date fromDate, Date toDate) {
        List<Activity> activities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + ACTIVITY + " WHERE userid = ? AND date BETWEEN ? AND ?"
            );
            preparedStatement.setLong(1, userId);
            preparedStatement.setDate(2, new java.sql.Date(fromDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(toDate.getTime()));
            ResultSet activityResultSet = preparedStatement.executeQuery();
            while (activityResultSet.next()) {
                Activity activity = getActivityFromResultSet(activityResultSet);
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    public void addActivity(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO activity values (null, ?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );
            insertStatement.setString(1, activity.getDescription());
            insertStatement.setDate(2, new java.sql.Date(activity.getDate().getTime()));
            insertStatement.setLong(3, activity.getUserId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Activity getActivityFromResultSet(ResultSet rs) throws SQLException {
        Activity activity = new ActivityBuilder()
                .setDescription(rs.getString("description"))
                .setDate(new java.sql.Date(rs.getDate("date").getTime()))
                .setUserId(rs.getLong("userId"))
                .build();
        return activity;
    }
}
