package api;

import io.restassured.response.Response;
import models.User;
import utils.RestUtils;
import config.ConfigManager;

public class UserAPI {
    private static final String BASE_URL = ConfigManager.getProperty("baseUrl");
    private static final String USERS_ENDPOINT = "/api/users";
    private static final String USERS_WITH_ID = USERS_ENDPOINT + "/{id}";
    private static final String LIST_USERS = USERS_ENDPOINT + "?page={page}";

    public static Response createUser(String name, String job) {
        return RestUtils.postRequest(BASE_URL + USERS_ENDPOINT, new User(name, job));
    }

    public static Response updateUser(int id, String name, String job) {
        return RestUtils.putRequest(BASE_URL + USERS_WITH_ID.replace("{id}", String.valueOf(id)), new User(name, job));
    }

    public static Response listUsers(int page) {  // Now dynamic
        return RestUtils.getRequest(BASE_URL + LIST_USERS.replace("{page}", String.valueOf(page)));
    }

    public static Response deleteUser(int id) {
        return RestUtils.deleteRequest(BASE_URL + USERS_WITH_ID.replace("{id}", String.valueOf(id)));
    }
}
