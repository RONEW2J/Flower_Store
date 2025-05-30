package com.example.ecommerce.constants;

public class PathConstants {
    public static final String API_V1 = "/api/v1";
    public static final String ORDER = "/order";
    public static final String ORDERS = "/orders";
    public static final String FLOWERS = "/flowers";
    public static final String USER = "/user";
    public static final String GRAPHQL = "/graphql";

    public static final String API_V1_ADMIN = API_V1 + "/admin";
    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String API_V1_ORDER = API_V1 + ORDER;
    public static final String API_V1_FLOWERS = API_V1 + FLOWERS;
    public static final String API_V1_REGISTRATION = API_V1 + "/registration";
    public static final String API_V1_REVIEW = API_V1 + "/review";
    public static final String API_V1_USERS = API_V1 + "/users";

    public static final String ADD = "/add";
    public static final String EDIT = "/edit";
    public static final String CART = "/cart";
    public static final String DELETE_BY_FLOWER_ID = "/delete/{flowerId}";
    public static final String ORDER_BY_EMAIL = ORDER + "/{userEmail}";
    public static final String ORDER_DELETE = ORDER + "/delete/{orderId}";
    public static final String USER_BY_ID = USER + "/{userId}";
    public static final String USER_ALL = USER + "/all";
    public static final String GRAPHQL_USER = GRAPHQL + USER;
    public static final String GRAPHQL_USER_ALL = GRAPHQL + USER + "/all";
    public static final String GRAPHQL_ORDERS = GRAPHQL + ORDERS;
    public static final String GRAPHQL_ORDER = GRAPHQL + ORDER;

    public static final String LOGIN = "/login";
    public static final String FORGOT_EMAIL = "/forgot/{email}";
    public static final String RESET = "/reset";
    public static final String CODE = "/{code}";
    public static final String RESET_CODE = RESET + CODE;
    public static final String ACTIVATE_CODE = "/activate" + CODE;
    public static final String EDIT_PASSWORD = "/edit/password";

    public static final String ORDER_ID = "/{orderId}";
    public static final String ORDER_ID_ITEMS = ORDER_ID + "/items";

    public static final String FLOWER_ID = "/{flowerId}";
    public static final String IDS = "/ids";
    public static final String SEARCH = "/search";
    public static final String SEARCH_CATEGORY = SEARCH + "/category";
    public static final String SEARCH_NAME = SEARCH + "/name";
    public static final String SEARCH_TEXT = SEARCH + "/text";
    public static final String GRAPHQL_IDS = GRAPHQL + IDS;
    public static final String GRAPHQL_FLOWERS = GRAPHQL + FLOWERS;
    public static final String GRAPHQL_FLOWER = GRAPHQL + "/flower";
    public static final String SEARCH_COLOR = SEARCH + "/color";
    public static final String SEARCH_SPECIES = SEARCH + "/species";
}
