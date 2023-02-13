package Tests;

import TestListener.ListenerLogToAllure;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static Methods.deleteUsers.deleteUsers;
import static Methods.getUsers.getUsers;



@Listeners(ListenerLogToAllure.class)
public class CleanTest {

    @Test(priority = 0, description = "Delete Created Users Who Are Left After Previous Tests")
    public void Clean_The_DBase_After_Testing() {

        deleteUsers(getUsers());
    }
}
