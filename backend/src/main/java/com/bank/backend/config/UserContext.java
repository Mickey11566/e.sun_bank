package com.bank.backend.config;

/**
 * 用於儲存當前請求的使用者資訊 (ThreadLocal)
 */
public class UserContext {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static void setUser(String user) {
        currentUser.set(user);
    }

    public static String getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
