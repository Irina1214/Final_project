package helpers;

/**
 * Класс для хранения данных пользователя.
 *
 * @author Zakirova Irina
 */
public class UserContext {
    private String email;
    private String password;
    private String createdAdTitle;

    public void setUserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setCreatedAdTitle(String title) {
        this.createdAdTitle = title;
    }

    public void clear() {
        email = null;
        password = null;
        createdAdTitle = null;
    }
}