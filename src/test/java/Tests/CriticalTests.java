package Tests;

import POJOclasses.User;
import Specifications.Specification;
import TestData.TestData;
import TestListener.ListenerLogToAllure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners(ListenerLogToAllure.class)
public class CriticalTests {


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "We Can Create New Users By The Admin Role")
    public void Can_We_Create_A_New_User_As_Admin(){

        given()
                .baseUri(TestData.url)
                .param("age",TestData.userWithValidDataUser.getAge())
                .param("login", TestData.userWithValidDataUser.getLogin())
                .param("password",TestData.userWithValidDataUser.getPassword())
                .param("screenName",TestData.userWithValidDataUser.getScreenName())
                .param("gender", TestData.userWithValidDataUser.getGender())
                .param("role", TestData.userWithValidDataUser.getRole())
                .when()
                .get("player/create/" + TestData.admin)
                .then().statusCode(200)
                .extract().as(User.class);

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "We Can't Create New Users With The Same Login Or ScreenName")
    public void Can_We_Create_A_New_User_That_Is_Exist(){

        // Create a new user
        given()
                .baseUri(TestData.url)
                .param("age",TestData.userWithValidDataUser.getAge())
                .param("login", TestData.userWithValidDataUser.getLogin())
                .param("password",TestData.userWithValidDataUser.getPassword())
                .param("screenName",TestData.userWithValidDataUser.getScreenName())
                .param("gender", TestData.userWithValidDataUser.getGender())
                .param("role", TestData.userWithValidDataUser.getRole())
                .when()
                .get("player/create/" + TestData.supervisor)
                .then().statusCode(200)
                .extract().as(User.class);

        // Create a new user with the same 'ScreenName'
        given()
                .baseUri(TestData.url)
                .param("age",TestData.userWithTheSameScreenNameThatIsExist.getAge())
                .param("login", TestData.userWithTheSameScreenNameThatIsExist.getLogin())
                .param("password",TestData.userWithTheSameScreenNameThatIsExist.getPassword())
                .param("screenName",TestData.userWithTheSameScreenNameThatIsExist.getScreenName())
                .param("gender", TestData.userWithTheSameScreenNameThatIsExist.getGender())
                .param("role", TestData.userWithTheSameScreenNameThatIsExist.getRole())
                .when()
                .get("player/create/" + TestData.supervisor)
                .then().statusCode(403)
                .extract().as(User.class);

    }

    // From my point of view, the password should be always required!
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "The User can't be created without password")
    public void Can_We_Create_A_New_User_Without_Password(){
        Specification.installSpecification(Specification.requestSpecification(TestData.url), Specification.responseSpec403());

        given()
                .param("age",TestData.userWithValidDataUser.getAge())
                .param("login", TestData.userWithValidDataUser.getLogin())
                .param("password","")
                .param("screenName",TestData.userWithValidDataUser.getScreenName())
                .param("gender", TestData.userWithValidDataUser.getGender())
                .param("role", TestData.userWithValidDataUser.getRole())
                .when()
                .get("player/create/" + TestData.supervisor)
                .then().extract().as(User.class);

    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "As User We Can't Delete Another User")
    public void Can_We_Delete_Another_User_As_User(){

        User responceUser = given()
                .baseUri(TestData.url)
                .param("age",TestData.userWithValidDataUser.getAge())
                .param("login", TestData.userWithValidDataUser.getLogin())
                .param("password",TestData.userWithValidDataUser.getPassword())
                .param("screenName",TestData.userWithValidDataUser.getScreenName())
                .param("gender", TestData.userWithValidDataUser.getGender())
                .param("role", TestData.userWithValidDataUser.getRole())
                .when()
                .get("player/create/" + TestData.supervisor)
                .then().statusCode(200)
                .extract().as(User.class);

        JSONObject newUserId = new JSONObject().put("playerId", responceUser.getId());
        given()
                .baseUri(TestData.url)
                .contentType(ContentType.JSON)
                .body(newUserId.toString())
                .when()
                .delete("player/delete/" + TestData.user)
                .then().statusCode(403);

    }

    @Test(description = "As Admin We Can Update User With User Role")
    public void Can_We_Update_The_User_As_Admin(){

        User editedUser = TestData.userWithValidDataUser;
        editedUser.setScreenName("Racer");
        given()
                .baseUri(TestData.url)
                .contentType(ContentType.JSON)
                .body(editedUser)
                .when()
                .patch("player/update/" +TestData.admin+ "/"+ editedUser.getId())
                .then().statusCode(200)
                .body("screenName", equalTo("Racer"));

    }


}
