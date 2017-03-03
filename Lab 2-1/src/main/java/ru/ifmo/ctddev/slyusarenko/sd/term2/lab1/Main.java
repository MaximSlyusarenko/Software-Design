package ru.ifmo.ctddev.slyusarenko.sd.term2.lab1;

/**
 * @author Maxim Slyusarenko
 * @since 02.03.17
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        while (true) {
            main.x();
            Thread.sleep(500);
        }
    }

    @Profileble
    public void x() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
