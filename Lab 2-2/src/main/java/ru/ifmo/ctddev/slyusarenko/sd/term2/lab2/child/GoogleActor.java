package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import ru.ifmo.ctddev.slyusarenko.sd.core.HttpBasedProcessor;

import java.io.IOException;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class GoogleActor extends ChildActor {

    @Override
    protected String getSearchSystem() {
        return "google.com";
    }

    @Override
    protected int getPort() {
        return 8890;
    }
}
