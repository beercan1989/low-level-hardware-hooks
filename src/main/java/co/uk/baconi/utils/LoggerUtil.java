package co.uk.baconi.utils;

import static co.uk.baconi.keylogger.framework.constants.Strings.COMMA;
import static co.uk.baconi.utils.StringUtil.join;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import co.uk.baconi.keylogger.framework.constants.Numbers;

public final class LoggerUtil {
    private final File loggingFile;
    private final boolean writeToFile;
    private final boolean writeToConsole;

    private LoggerUtil(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        this.loggingFile = loggingFile;
        this.writeToFile = writeToFile;
        this.writeToConsole = writeToConsole;

        if (headings != null && headings.length > Numbers.ONE) {
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

    public static LoggerUtil getLogger(final String filename, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        return getLogger(new File(filename), writeToFile, writeToConsole, headings);
    }

    public static LoggerUtil getLogger(final File loggingFile, final boolean writeToFile, final boolean writeToConsole,
            final String... headings) {
        return new LoggerUtil(loggingFile, writeToFile, writeToConsole, headings);
    }

    public static LoggerUtil getLogger(final String filename) {
        return getLogger(new File(filename));
    }

    public static LoggerUtil getLogger(final File loggingFile) {
        return new LoggerUtil(loggingFile, true, true);
    }
}
