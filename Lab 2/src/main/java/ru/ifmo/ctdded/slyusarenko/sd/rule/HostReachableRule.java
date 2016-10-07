package ru.ifmo.ctdded.slyusarenko.sd.rule;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Slyusarenko
 * @since 06.10.16
 */
public class HostReachableRule implements TestRule {
    private static final int TIMEOUT = 1_000;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    public @interface HostReachable {
        String value();
    }

    private static boolean checkHost(String host) {
        return nativePing(host) || nativePing6(host);
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        HostReachable hostReachable
                = description.getAnnotation(HostReachable.class);

        if (hostReachable == null) {
            return statement;
        } else if (!checkHost(hostReachable.value())) {
            return new SkipStatement(hostReachable.value());
        }

        return statement;
    }

    private static class SkipStatement extends Statement {
        private final String host;

        public SkipStatement(String host) {
            this.host = host;
        }

        @Override
        public void evaluate() throws Throwable {
            Assume.assumeTrue(false);
        }
    }

    private static boolean nativePing(String host) {
        return nativePingImpl("ping", host);
    }

    private static boolean nativePing6(String host) {
        return nativePingImpl("ping6", host);
    }

    private static boolean nativePingImpl(String cmd, String host) {
        try {
            Process pingProcess
                    = new ProcessBuilder(cmd, "-c", "1", host).start();
            return pingProcess.waitFor(TIMEOUT, TimeUnit.MILLISECONDS) && pingProcess.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
