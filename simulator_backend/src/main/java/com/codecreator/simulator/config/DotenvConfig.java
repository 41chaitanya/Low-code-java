package com.codecreator.simulator.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    static {
        try {
            System.out.println("🔍 Attempting to load .env file...");
            System.out.println("Working directory: " + System.getProperty("user.dir"));

            Dotenv dotenv = Dotenv.configure()
                    .directory(".")
                    .ignoreIfMissing()
                    .load();

            System.out.println("📁 .env file found and loaded!");

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                System.out.println("✅ Loaded: " + entry.getKey() + " = "
                        + (entry.getKey().contains("PASSWORD") ? "***" : entry.getValue()));
            });
        } catch (Exception e) {
            System.err.println("❌ Failed to load .env file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void logEnvironment() {
        String password = System.getProperty("DB_PASSWORD");
        System.out.println("\n=== Environment Check ===");
        System.out.println("DB_PASSWORD loaded: " + (password != null ? "YES" : "NO"));
        if (password != null) {
            System.out.println("Password length: " + password.length());
        }
        System.out.println("=========================\n");
    }
}
