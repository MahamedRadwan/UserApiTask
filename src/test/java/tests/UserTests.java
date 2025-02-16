package tests;

import api.UserAPI;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonReaderUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserTests {

    private static int userId; // Store created user ID

    @DataProvider(name = "paginationData")
    public Object[][] paginationData() {
        return new Object[][] { {0}, {1}, {2}, {3}, {4}}; // Example: Testing different pages
    }


    @DataProvider(name = "userDataFromJson")
    public Object[][] userDataFromJson() {
        List<Map<String, String>> jsonData = JsonReaderUtil.readJsonData();
        return jsonData.stream()
                .map(data -> new Object[]{data.get("name"), data.get("job"),
                            data.get("updatedName"),data.get("updatedJob")
                })
                .toArray(Object[][]::new);
    }

    @Test(priority = 1, dataProvider = "userDataFromJson")
    public void testCreateUser(String name, String job) {
        Response response = UserAPI.createUser(name, job);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 201, "Status Code Mismatch");
        Assert.assertEquals(response.jsonPath().getString("name"), name, "Name Mismatch");
        Assert.assertEquals(response.jsonPath().getString("job"), job, "Job Mismatch");

        // Store created user ID
        userId = response.jsonPath().getInt("id");

        // Validate response time
        Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < 2000, "Response took too long!");

        // Validate response headers
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Content-Type Mismatch");
    }

    @Test(priority = 2,dataProvider = "userDataFromJson" ,dependsOnMethods = "testCreateUser")
    public void testUpdateUser(String updatedName , String updatedJob) {

        Response response = UserAPI.updateUser(userId, updatedName, updatedJob);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code Mismatch");
        Assert.assertEquals(response.jsonPath().getString("name"), updatedName, "Name Update Mismatch");
        Assert.assertEquals(response.jsonPath().getString("job"), updatedJob, "Job Update Mismatch");
    }

    @Test(priority = 3, dataProvider = "paginationData")
    public void testListUsers(int page) {
        Response response = UserAPI.listUsers(page);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code Mismatch");
        Assert.assertFalse(response.jsonPath().getList("data").isEmpty(), "User list is empty");
        // Validate response time
        Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS) < 1500, "Response took too long!");

        System.out.println("Users on page " + page + ": " + response.getBody().asString());
    }
    @Test(priority = 4, dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        Response response = UserAPI.deleteUser(userId);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 204, "Status Code Mismatch");
    }

    @Test(priority = 5)
    public void testCreateUserWithMissingFields() {
        Response response = UserAPI.createUser("","-"); // Empty name & job
        // Expected to fail with 400 Bad Request
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request for missing fields");
    }

    @Test(priority = 6)
    public void testCreateUserWithNullValues() {
        Response response = UserAPI.createUser(null, null); // Null values

        // Expected to fail with 400 Bad Request
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request for null values");
    }

    @Test(priority = 7)
    public void testUpdateNonExistingUser() {
        int nonExistingUserId = 45345; // Assuming this user ID doesn’t exist
        Response response = UserAPI.updateUser(nonExistingUserId, "Ghost User", "Ghost Job");
        // Expected to fail with 404 Not Found
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found for non-existing user");
    }

    @Test(priority = 8)
    public void testDeleteNonExistingUser() {
        int nonExistingUserId = 42342; // Assuming this user ID doesn’t exist
        Response response = UserAPI.deleteUser(nonExistingUserId);
        // Expected to fail with 404 Not Found
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found for deleting non-existing user");
    }

    @Test(priority = 9)
    public void testRateLimiting() {
        // Simulate sending multiple requests to check rate limiting (if applicable)
        for (int i = 0; i < 10; i++) {
            Response response = UserAPI.listUsers(1);
            Assert.assertEquals(response.getStatusCode(), 200, "Unexpected response status on repeated requests");
        }
    }
}
