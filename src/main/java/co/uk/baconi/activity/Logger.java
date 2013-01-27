package co.uk.baconi.activity;

import static co.uk.baconi.utils.StringUtil.COMMA;
import static co.uk.baconi.utils.StringUtil.join;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class Logger {
    private final File loggingFile;
    private final boolean writeToFile;
    private final boolean writeToConsole;

    private Logger(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        this.loggingFile = loggingFile;
        this.writeToFile = writeToFile;
        this.writeToConsole = writeToConsole;

        if (headings != null && headings.length > 1) {
            final String headingString = join(COMMA, headings);
            if (this.writeToFile && !loggingFile.exists()) {
                logToFile(headingString);
            }
            if (this.writeToConsole) {
                logToConsole(headingString);
            }
        }
    }

    public void log(final String logEntry) {
        logToConsole(logEntry);
        logToFile(logEntry);
    }

    private void logToFile(final String logEntry) {
        if (writeToFile) {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(loggingFile, true));
                bw.write(logEntry);
                bw.newLine();
                bw.flush();
            } catch (final Throwable t) {
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (final Throwable t) {
                    }
                }
            }
        }
    }

    private void logToConsole(final String logEntry) {
        if (writeToConsole) {
            System.out.println(logEntry);
        }
    }

    public static Logger getLogger(final String filename, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        return getLogger(new File(filename), writeToFile, writeToConsole, headings);
    }

    public static Logger getLogger(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        return new Logger(loggingFile, writeToFile, writeToConsole, headings);
    }

    public static Logger getLogger(final String filename) {
        return getLogger(new File(filename));
    }

    public static Logger getLogger(final File loggingFile) {
        return new Logger(loggingFile, true, true);
    }
}
