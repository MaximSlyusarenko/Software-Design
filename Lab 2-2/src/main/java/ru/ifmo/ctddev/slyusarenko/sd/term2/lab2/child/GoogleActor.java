package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

import akka.actor.UntypedActor;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class GoogleActor extends ChildActor {

    @Override
    protected String getQueryResultString(String query) {
        return null;
    }

    @Override
    protected String getSearchSystem() {
        return null;
    }
}
