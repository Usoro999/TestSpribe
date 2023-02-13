package TestData;

import POJOclasses.User;

public class TestData {


    // -------  URLs ---------------//

    public static String url = "http://3.68.165.45/";

    // --------  ROLES ------------//
    public static String supervisor = "supervisor";
    public static String admin = "admin";
    public static String user = "user";


    // --- USERS -------------------//
    // Valid users
    public static User userWithValidDataUser = new User("UserUser1","qwerty123","UserScreenName120","male", 35,"user");
    public static User userWithValidDataAdmin = new User("UserUser2","qwerty123","UserScreenName121","male", 20,"admin");

    // User with the same login as exist user
    public static User userWithTheSameScreenNameThatIsExist = new User("UserUser6","qwerty123","UserScreenName120","female", 59,"user");

    // Users with invalid data
    public static User userWithInvalidAge60 = new User("UserUser3","qwerty123","UserScreenName1","male", 60,"admin");
    public static User userWithInvalidAge16 = new User("UserUser4","qwerty123","UserScreenName2","male", 16,"user");
    public static User userWithInvalidGender = new User("UserUser5","qwerty123","UserScreenName3","cat", 20,"user");



}
