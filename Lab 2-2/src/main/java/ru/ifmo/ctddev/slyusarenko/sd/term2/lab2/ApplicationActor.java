package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ru.ifmo.ctddev.slyusarenko.sd.core.FastScanner;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class ApplicationActor extends UntypedActor {

    private int queryNumber = 0;

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner();
        ActorSystem actorSystem = ActorSystem.create("SearchSystem");
        ActorRef applicationActor = actorSystem.actorOf(Props.create(ApplicationActor.class), "ApplicationActor");
        while (true) {
            System.out.print(">");
            String query = scanner.next().toLowerCase();
            if ("exit".equals(query)) {
                break;
            } else {
                applicationActor.tell(query, ActorRef.noSender());
            }
        }
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            String query = (String) message;
            ActorRef actor = getContext().actorOf(Props.create(MasterActor.class), query + (queryNumber++));
            actor.tell(new Query(query, queryNumber - 1), ActorRef.noSender());
        } else if (message instanceof QueryResults) {
            System.out.println(message.toString());
        } else {
            throw new UnsupportedOperationException("Only String queries or QueryResults are supported");
        }
    }
}
