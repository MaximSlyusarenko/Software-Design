package ru.ifmo.ctddev.slyusarenko.sd.core;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class HttpBasedProcessor {

    private CloseableHttpClient client;

    public HttpBasedProcessor() {
        client = HttpClientBuilder.create().build();
    }

    public String execute(HttpUriRequest request) throws IOException {
        return client.execute(request, response -> {
            InputStream in = response.getEntity().getContent();
            return IOUtils.toString(in, "UTF-8");
        }, null);
    }
}
