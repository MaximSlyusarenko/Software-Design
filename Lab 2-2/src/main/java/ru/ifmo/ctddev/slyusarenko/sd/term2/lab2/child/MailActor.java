package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2.child;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
public class MailActor extends ChildActor {

    @Override
    protected String getSearchSystem() {
        return "mail.ru";
    }

    @Override
    protected int getPort() {
        return 8888;
    }
}
