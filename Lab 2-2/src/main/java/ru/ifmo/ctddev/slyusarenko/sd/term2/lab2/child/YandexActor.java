package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

import akka.actor.UntypedActor;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class YandexActor extends ChildActor {

    @Override
    protected String getSearchSystem() {
        return "yandex.ru";
    }

    @Override
    protected int getPort() {
        return 8889;
    }
}
