package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child.GoogleActor;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child.MailActor;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child.YandexActor;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class MasterActor extends UntypedActor {

    private List<QueryResult> results = new ArrayList<>();

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Query) {
            String query = ((Query) message).getQuery();
            int number = ((Query) message).getQueryNumber();
            createChildActor("Google", query, number);
            createChildActor("Yandex", query, number);
            createChildActor("Mail", query, number);
            getContext().setReceiveTimeout(Duration.create(1, TimeUnit.SECONDS));
        } else if (message instanceof QueryResult) {
            results.add((QueryResult) message);
            if (results.size() == 3) {
                sendToParent();
            }
        } else if (message instanceof ReceiveTimeout) {
            sendToParent();
        } else {
            throw new UnsupportedOperationException("Can work only with Query or QueryResult");
        }
    }

    private void sendToParent() {
        getContext().parent().tell(new QueryResults(results), getContext().self());
        getContext().setReceiveTimeout(Duration.Undefined());
        getContext().stop(self());
    }

    private void createChildActor(String searchSystem, String query, int number) {
        ActorRef child = getContext().actorOf(Props.create(getClassBySearchSystemName(searchSystem)), searchSystem + "-" + query + "-" + number);
        child.tell(query, self());
    }

    private Class getClassBySearchSystemName(String name) {
        if ("Google".equals(name)) {
            return GoogleActor.class;
        } else if ("Yandex".equals(name)) {
            return YandexActor.class;
        } else if ("Mail".equals(name)) {
            return MailActor.class;
        } else {
            throw new IllegalArgumentException("Name can be Google, Yandex or Mail");
        }
    }
}
