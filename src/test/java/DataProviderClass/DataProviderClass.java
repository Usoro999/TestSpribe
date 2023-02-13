package DataProviderClass;

import TestData.TestData;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider
    public static Object[][] usersWithValidData() {
        return new Object[][]{{TestData.userWithValidDataUser, TestData.supervisor},
                {TestData.userWithValidDataAdmin, TestData.supervisor}};
    }

    @DataProvider
    public static Object[][] usersWithInvalidData() {
        return new Object[][]{
                {TestData.userWithInvalidAge60, TestData.supervisor},
                {TestData.userWithInvalidAge16, TestData.supervisor},
                {TestData.userWithInvalidGender, TestData.supervisor}}
    ;}

    @DataProvider
    public static Object[][] listWhoCanDeleteUsers(){
        return new Object[][]{
                {TestData.supervisor, TestData.admin}
        };
    }

    @DataProvider
    public static Object[][] createdUsersWithValidData() {
        return new Object[][]{
                {TestData.userWithValidDataUser, TestData.userWithValidDataAdmin}};
    }

    @DataProvider
    public static Object[][] listAllRoles(){
        return new Object[][]{
                {TestData.supervisor, TestData.admin, TestData.user}
        };
    }

    @DataProvider
    public static Object[][] userWithValidData() {
        return new Object[][]{{TestData.userWithTheSameScreenNameThatIsExist, TestData.supervisor}};
    }

}