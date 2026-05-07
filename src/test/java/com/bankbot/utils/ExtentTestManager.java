package com.bankbot.utils;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void createTest(String testName) {
        EXTENT_TEST.set(ExtentManager.getExtentReports().createTest(testName));
    }

    public static ExtentTest getTest() {
        ExtentTest test = EXTENT_TEST.get();
        if (test == null) {
            throw new IllegalStateException("ExtentTest is not initialized for this thread.");
        }
        return test;
    }

    public static void unload() {
        EXTENT_TEST.remove();
    }
}
