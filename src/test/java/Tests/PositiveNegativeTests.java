package Tests;

import DataProviderClass.DataProviderClass;
import POJOclasses.User;
import Specifications.Specification;
import TestData.TestData;
import TestListener.ListenerLogToAllure;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners(ListenerLogToAllure.class)
public class PositiveNegativeTests {
    private User createdUser = new User();
    private User createdAdmin = new User();
    private JSONObject createdUserIdJson;
    private JSONObject createdAdminIdJson;


    @Test(priority = 0, description = "Create User And Admin By Supervisor With Valid Data", dataProvider = "usersWithValidData", dataProviderClass = DataProviderClass.class)
    public void Create_User_Positive_Test(User user, String role) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec200());

        User responseUser = given()
                .param("age", user.getAge())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("screenName", user.getScreenName())
                .param("gender", user.getGender())
                .param("role", user.getRole())
                .when()
                .get("player/create/" + role)
                .then().log().body()
                .extract().as(User.class);

        // Since we create two types of User (admin and user) -> save them for further tests
        if (responseUser.getLogin().equals("UserUser1")) {
            createdUser = responseUser;
        } else {
            createdAdmin = responseUser;
        }

        // Save ids of created users for further tests
        createdUserIdJson = new JSONObject().put("playerId", createdUser.getId());
        createdAdminIdJson = new JSONObject().put("playerId", createdAdmin.getId());

    }

    @Test(priority = 1, description = "Create The User And Admin With Invalid Data", dataProvider = "usersWithInvalidData", dataProviderClass = DataProviderClass.class)
    public void Create_User_Negative_Test(User user, String role) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec400());
        given()
                .param("age", user.getAge())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("screenName", user.getScreenName())
                .param("gender", user.getGender())
                .param("role", user.getRole())
                .when()
                .get("player/create/" + role);
    }


    @Test(priority = 2, description = "Get UserInfo With Valid (exist) Id", dataProvider = "createdUsersWithValidData",
            dataProviderClass = DataProviderClass.class, dependsOnMethods = "Create_User_Positive_Test")
    public void Get_UserInfo_Positive_Test(User user, User admin) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec200());

        given()
                //.body(newUserIdJson.toString())
                .body(createdUserIdJson.toString())
                .when()
                .post("/player/get")
                .then()
                .body("age", equalTo(user.getAge()))
                .body("login", equalTo(user.getLogin()))
                .body("password", equalTo(user.getPassword()))
                .body("screenName", equalTo(user.getScreenName()))
                .body("gender", equalTo(user.getGender()))
                .body("role", equalTo(user.getRole()));

        given()
                .body(createdAdminIdJson.toString())
                .when()
                .post("/player/get")
                .then()
                .body("age", equalTo(admin.getAge()))
                .body("login", equalTo(admin.getLogin()))
                .body("password", equalTo(admin.getPassword()))
                .body("screenName", equalTo(admin.getScreenName()))
                .body("gender", equalTo(admin.getGender()))
                .body("role", equalTo(admin.getRole()));

    }

    @Test(priority = 3, description = "Get UserInfo With Negative Id", dependsOnMethods = "Create_User_Positive_Test")
    public void Get_UserInfo_Negative_Test() {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec404());
        JSONObject invalidIdJson = new JSONObject().put("playerId", createdUser.getId() + 10);

        given()
                .body(invalidIdJson.toString()).log().body()
                .when()
                .post("/player/get");

    }

    @Test(priority = 4, description = "Update Created Users by supervisor and admin", dataProvider = "listAllRoles",
            dataProviderClass = DataProviderClass.class, dependsOnMethods = "Create_User_Positive_Test")
    public void Update_Created_User_Positive_Test(String supervisor, String admin, String user) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec200());

        createdUser.setScreenName("Alibaba");
        given()
                .body(createdUser)
                .when()
                .patch("player/update/" + supervisor + "/" + createdUser.getId())
                .then().log().body()
                .body("screenName", equalTo("Alibaba"));

        createdAdmin.setGender("female");
        given()
                .body(createdUser)
                .when()
                .patch("player/update/" + admin + "/" + createdUser.getId())
                .then().log().body()
                .body("gender", equalTo("female"));


    }

    @Test(priority = 5, description = "Update Created Users by user role", dependsOnMethods = "Create_User_Positive_Test")
    public void Update_Created_User_Negative_Test() {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec403());
        createdUser.setAge(50);
        given()
                .body(createdUser)
                .when()
                .patch("player/update/" + TestData.user + "/" + createdUser.getId());

    }

    @Test(priority = 6, description = "Delete new created users by supervisor and admin", dataProvider = "listWhoCanDeleteUsers",
            dataProviderClass = DataProviderClass.class, dependsOnMethods = "Create_User_Positive_Test")
    public void Delete_User_Positive_Test(String supervisor, String admin) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec204());

        given()
                .body(createdUserIdJson.toString())
                .when()
                .delete("player/delete/" + supervisor);

        given()
                .body(createdAdminIdJson.toString())
                .when()
                .delete("player/delete/" + admin);

    }


    @Test(priority = 7, description = "Delete new created users with invalid id by supervisor and admin", dataProvider = "listWhoCanDeleteUsers",
            dataProviderClass = DataProviderClass.class, dependsOnMethods = "Create_User_Positive_Test")
    public void Delete_User_Negative_Test(String supervisor, String admin) {
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec403());
        JSONObject newUserInvalidIdJson = new JSONObject().put("playerId", createdUser.getId() + 10);
        JSONObject newAdminInvalidIdJson = new JSONObject().put("playerId", createdAdmin.getId() + 10);
        given()
                .body(newUserInvalidIdJson.toString())
                .when()
                .delete("player/delete/" + supervisor);

        given()
                .body(newAdminInvalidIdJson.toString())
                .when()
                .delete("player/delete/" + admin);

    }


}



