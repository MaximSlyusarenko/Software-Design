package ru.ifmo.ctddev.slyusarenko.sd.core;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class HttpBasedProcessor {

    private CloseableHttpClient client;

    public HttpBasedProcessor() {
        // TODO: form client
    }

    public String execute(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        return client.execute(get, response -> {
            // TODO: swap with real implementation
            byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
            StringBuilder stringBuilder = new StringBuilder(new String(bytes, "UTF-8"));
            stringBuilder.insert(1, ",");
            stringBuilder.insert(1, response.getStatusLine().getStatusCode());
            return stringBuilder.toString();
        }, null);
    }
}
