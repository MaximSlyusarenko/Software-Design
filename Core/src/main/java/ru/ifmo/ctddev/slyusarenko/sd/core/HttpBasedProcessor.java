package ru.ifmo.ctddev.slyusarenko.sd.core;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class HttpBasedProcessor {

    private CloseableHttpClient client;

    public HttpBasedProcessor() {
        // TODO: form client
        client = HttpClientBuilder.create().build();
    }

    public String execute(HttpUriRequest request) throws IOException {
        return client.execute(request, response -> {
            // TODO: swap with real implementation
//            byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
//            StringBuilder stringBuilder = new StringBuilder(new String(bytes, "UTF-8"));
//            stringBuilder.insert(1, ",");
//            stringBuilder.insert(1, response.getStatusLine().getStatusCode());
//            return stringBuilder.toString();
            return response.toString();
        }, null);
    }
}
