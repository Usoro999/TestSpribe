package Methods;

import POJOclasses.User;
import TestData.TestData;

import java.util.List;

import static io.restassured.RestAssured.given;

public class getUsers {

    public static List<User> getUsers(){
        List<User> listOfUsers = given()
                .baseUri(TestData.url)
                .when()
                .get("player/get/all")
                .then().statusCode(200)
                .extract().body().jsonPath().getList("players.", User.class);
        return listOfUsers;
    }



        }


