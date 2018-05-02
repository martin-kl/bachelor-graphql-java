import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> userList;
    private int newId;

    public UserRepository() {
        this.userList = new ArrayList<>();

        userList.add(new User("1000", "Martin Klampfer", "e1526110@student.tuwien.ac.at", "test"));
        userList.add(new User("1001", "Max Mustermann", "max.mustermann@mail.com", "test"));
        userList.add(new User("1002", "Silvia Musterfrau", "silvia.musterfrau@mail.com", "test"));
        newId = 1002;
    }

    public User findByEmail(String email) {
        for(User user : userList) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User findById(String id) {
        for(User user : userList) {
            if(user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        userList.add(user);
    }

    public String getId() {
        ++newId;
        return newId + "";
    }
}
