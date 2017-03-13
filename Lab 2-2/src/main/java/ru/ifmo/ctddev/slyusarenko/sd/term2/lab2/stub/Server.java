package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.stub;

import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Call;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;

/**
 * @author Maxim Slyusarenko
 * @since 13.03.17
 */
public class Server {



    public static void main(String[] args) {
        StubServer server = new StubServer(8888).run();
        StubHttp.whenHttp(server)
                .match(Condition.method(Method.GET))
                .then(Action.stringContent("{\"results\":[{\"url\":\"mail.ru\",\"header\":\"Mail1\",\"text\":\"First\"}," +
                        "{\"url\":\"mail.ru\",\"header\":\"Mail2\",\"text\":\"Second\"}," +
                        "{\"url\":\"mail.ru\",\"header\":\"Mail3\",\"text\":\"Third\"}," +
                        "{\"url\":\"mail.ru\",\"header\":\"Mail4\",\"text\":\"Fourth\"}," +
                        "{\"url\":\"mail.ru\",\"header\":\"Mail5\",\"text\":\"Fifth\"}]}"));
        StubServer server1 = new StubServer(8889).run();
        StubHttp.whenHttp(server1)
                .match(Condition.method(Method.GET))
                .then(Action.stringContent("{\"results\":[{\"url\":\"yandex.ru\",\"header\":\"Yandex1\",\"text\":\"First\"}," +
                        "{\"url\":\"yandex.ru\",\"header\":\"Yandex2\",\"text\":\"Second\"}," +
                        "{\"url\":\"yandex.ru\",\"header\":\"Yandex3\",\"text\":\"Third\"}," +
                        "{\"url\":\"yandex.ru\",\"header\":\"Yandex4\",\"text\":\"Fourth\"}," +
                        "{\"url\":\"yandex.ru\",\"header\":\"Yandex5\",\"text\":\"Fifth\"}]}"));
        StubServer server2 = new StubServer(8890).run();
        StubHttp.whenHttp(server2)
                .match(Condition.method(Method.GET))
                .then(Action.stringContent("{\"results\":[{\"url\":\"google.com\",\"header\":\"Google1\",\"text\":\"First\"}," +
                        "{\"url\":\"google.com\",\"header\":\"Google2\",\"text\":\"Second\"}," +
                        "{\"url\":\"google.com\",\"header\":\"Google3\",\"text\":\"Third\"}," +
                        "{\"url\":\"google.com\",\"header\":\"Google4\",\"text\":\"Fourth\"}," +
                        "{\"url\":\"google.com\",\"header\":\"Google5\",\"text\":\"Fifth\"}]}"));
        while (true);
    }
}
