package demo.shop.domain.models.view.usersmodels.statsmodels;

import java.util.List;

public class AllUserStatsModel {
    private int userCount;
    private List<UserStatsModel> users;

    public AllUserStatsModel(int userCount, List<UserStatsModel> users) {
        this.userCount = userCount;
        this.users = users;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<UserStatsModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserStatsModel> users) {
        this.users = users;
    }
}
