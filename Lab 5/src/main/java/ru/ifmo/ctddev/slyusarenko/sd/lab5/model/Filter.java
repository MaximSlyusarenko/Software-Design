package ru.ifmo.ctddev.slyusarenko.sd.lab5.model;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class Filter {
    private String filter;

    public Filter() {}
    public Filter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
