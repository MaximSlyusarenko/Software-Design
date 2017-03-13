package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

import akka.actor.UntypedActor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import ru.ifmo.ctddev.slyusarenko.sd.core.HttpBasedProcessor;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.QueryResult;

import java.io.IOException;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public abstract class ChildActor extends UntypedActor {

    protected Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String queryResultString = getQueryResultString((String) message);
            QueryResult result = gson.fromJson(queryResultString, QueryResult.class);
            result.setSearchSystem(getSearchSystem());
            getContext().parent().tell(result, getContext().self());
            getContext().stop(self());
        } else {
            throw new UnsupportedOperationException("Only String queries are supported");
        }
    }

    private String getQueryResultString(String query) throws IOException {
        HttpUriRequest get = RequestBuilder.get().setUri("http://localhost:" + getPort()).build();
        return new HttpBasedProcessor().execute(get);
    }

    protected abstract String getSearchSystem();

    protected abstract int getPort();

}
