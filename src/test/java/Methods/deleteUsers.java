package Methods;

import POJOclasses.User;
import TestData.TestData;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;

public class deleteUsers {

    public static void deleteUsers(List<User> listOfUsers) {
        for (User user : listOfUsers) {
            if (user.getScreenName().equals("UserScreenName3")) {
                JSONObject id = new JSONObject().put("playerId", user.getId());
                given()
                        .baseUri(TestData.url)
                        .contentType(ContentType.JSON)
                        .body(id.toString())
                        .when()
                        .delete("player/delete/supervisor")
                        .then().statusCode(204);

            }

        }
        for (User user : listOfUsers) {
            if (user.getScreenName().equals("UserScreenName120")) {
                JSONObject id = new JSONObject().put("playerId", user.getId());
                given()
                        .baseUri(TestData.url)
                        .contentType(ContentType.JSON)
                        .body(id.toString())
                        .when()
                        .delete("player/delete/supervisor")
                        .then().statusCode(204);

            }

        }
    }
}