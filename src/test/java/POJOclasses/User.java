package POJOclasses;

public class User {

    private Integer id;
    private String login;
    private String password;
    private String screenName;
    private String gender;
    private Integer age;
    private String role;

    public User(Integer id, String login, String password, String screenName, String gender, Integer age, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }

    public User(String login, String password, String screenName, String gender, Integer age, String role) {
        this.login = login;
        this.password = password;
        this.screenName = screenName;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }

    public User() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }
}
