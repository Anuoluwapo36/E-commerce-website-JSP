package com.centrum.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class AppCleanupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Thread mysqlCleanupThread = null;

        // Step 1: Find the stuck MySQL thread by name
        Thread[] allThreads = new Thread[Thread.activeCount() * 2]; // Double size to avoid missing threads
        Thread.enumerate(allThreads);
        
        for (Thread thread : allThreads) {
            if (thread != null && thread.getName().equals("mysql-cj-abandoned-connection-cleanup")) {
                mysqlCleanupThread = thread;
                System.out.println("⚠️ Found stuck MySQL thread—forcing interrupt");
                thread.interrupt(); // Force the thread to exit the blocked state
                thread.setContextClassLoader(ClassLoader.getSystemClassLoader()); // Break link to old ClassLoader
                break;
            }
        }

        // Step 2: Shutdown the thread (now it can respond)
        try {
            if (mysqlCleanupThread != null && mysqlCleanupThread.isAlive()) {
                AbandonedConnectionCleanupThread.checkedShutdown();
                // Wait 100ms for the thread to terminate (safety net)
                mysqlCleanupThread.join(100);
                if (mysqlCleanupThread.isAlive()) {
                    System.err.println("⚠️ MySQL thread still alive—marking as daemon");
                    mysqlCleanupThread.setDaemon(true); // JVM will kill daemon threads on exit
                }
            }
            System.out.println("✅ MySQL cleanup thread handled successfully");
        } catch (java.lang.InterruptedException e) {
            System.err.println("⚠️ Interrupted while shutting down MySQL thread: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("❌ Failed to handle MySQL thread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {}
}