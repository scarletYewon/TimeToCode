package com.kmu.timetocode.login;

public class UserProfile {
    private static int id;
    private static String name;
    private static String email;
    private static int level;

    public static void setRef(int id, String name, String email, int level) {
        UserProfile.id = id;
        UserProfile.name = name;
        UserProfile.email = email;
        UserProfile.level = level;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserProfile.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserProfile.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserProfile.email = email;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        UserProfile.level = level;
    }
}