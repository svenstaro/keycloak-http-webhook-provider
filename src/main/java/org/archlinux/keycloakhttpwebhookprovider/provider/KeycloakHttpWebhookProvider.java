package org.archlinux.keycloakhttpwebhookprovider.provider;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class KeycloakHttpWebhookProvider implements EventListenerProvider {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient httpClient = new OkHttpClient();
    private String serverUrl;
    private String username;
    private String password;

    public KeycloakHttpWebhookProvider(String serverUrl, String username, String password) {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
    }

    private void sendJson(String jsonString) throws IOException {
        Request.Builder request_builder = new Request.Builder().url(this.serverUrl).addHeader("User-Agent", "Keycloak Webhook");

        if (this.username != null && this.password != null) {
            String credential = Credentials.basic(this.username, this.password);
            request_builder.addHeader("Authorization", credential);
        }

        Request request = request_builder.post(RequestBody.create(jsonString, JSON)).build();

        Response response = httpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        response.close();
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("Event Occurred:" + toString(event));
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        System.out.println("Admin Event Occurred:" + toString(adminEvent));
    }

    @Override
    public void close() {}

    private String toString(Event event) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(event);
            sendJson(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private String toString(AdminEvent adminEvent) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(adminEvent);
            sendJson(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
