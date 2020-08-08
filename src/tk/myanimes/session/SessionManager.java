package tk.myanimes.session;

import tk.myanimes.io.Database;
import tk.myanimes.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final SessionManager instance = new SessionManager();

    private final Map<String, Long> users = new ConcurrentHashMap<>();

    public static SessionManager instance() {
        return instance;
    }

    public void registerSession(HttpServletRequest request, UserInfo userInfo) {
        users.put(request.getSession().getId(), userInfo.getId());
    }

    public void unregisterSession(HttpServletRequest request) {
        users.remove(request.getSession().getId());
    }

    public boolean isAuthenticated(HttpServletRequest request) {
        return users.containsKey(request.getSession().getId());
    }

    public UserInfo getCurrentUser(HttpServletRequest request) throws SQLException {
        var user = users.get(request.getSession().getId());
        return user == null ? null : Database.getUserInfo(user);
    }

}
