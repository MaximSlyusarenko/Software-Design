package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

import akka.actor.UntypedActor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.QueryResult;

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
            getContext().self().tell(result, getContext().parent());
            getContext().stop(self());
        } else {
            throw new UnsupportedOperationException("Only String queries are supported");
        }
    }

    protected abstract String getQueryResultString(String query);

    protected abstract String getSearchSystem();

}
