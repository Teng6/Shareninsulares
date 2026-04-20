package com.shareninsulares.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            String firebaseJson = System.getenv("FIREBASE_CONFIG_JSON");

            GoogleCredentials credentials;

            if (firebaseJson != null && !firebaseJson.isEmpty()) {
                credentials = GoogleCredentials.fromStream(
                        new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8))
                );
            } else {
                credentials = GoogleCredentials.fromStream(
                        getClass().getClassLoader().getResourceAsStream("firebase-service-account.json")
                );
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}