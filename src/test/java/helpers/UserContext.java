package helpers;

/**
 * Класс для хранения данных пользователя.
 *
 * @author Zakirova Irina
 */
public class UserContext {
    private String email;
    private String password;
    private String accessToken;
    private Integer userId;
    private String userName;
    private String createdAdTitle;
    private String createdAdDescription;
    private String createdAdPrice;
    private String createdAdId;
    private boolean createdViaApi = false;

    public void setUserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public void setCreatedViaApi(boolean viaApi) {
        this.createdViaApi = viaApi;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCreatedAdTitle() {
        return createdAdTitle;
    }

    public void setCreatedAdTitle(String title) {
        this.createdAdTitle = title;
    }

    public void setCreatedAdDescription(String description) {
        this.createdAdDescription = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreatedAdPrice(String price) {
        this.createdAdPrice = price;
    }

    public String getCreatedAdId() {
        return createdAdId;
    }

    public void setCreatedAdId(String id) {
        this.createdAdId = id;
    }

    public void clear() {
        email = null;
        password = null;
        accessToken = null;
        userId = null;
        userName = null;
        createdAdTitle = null;
        createdAdDescription = null;
        createdAdPrice = null;
        createdAdId = null;
        createdViaApi = false;
    }

    public boolean isCreatedViaApi() {
        return createdViaApi;
    }

    public String getCreatedAdDescription() {
        return createdAdDescription;
    }

    public String getCreatedAdPrice() {
        return createdAdPrice;
    }

    public String getAccessToken() {
        return accessToken;
    }
}