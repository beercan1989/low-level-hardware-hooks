package co.uk.baconi.utils;

public final class StringUtil {
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String COLON = ":";
    public static final String HYPHEN = "-";
    public static final String UNDER_SCORE = "_";

    private StringUtil() {
    }

    public static String concat(final Object... objects) {
        final StringBuilder sb = new StringBuilder();
        for (final Object object : objects) {
            sb.append(object);
        }
        return sb.toString().trim();
    }

    public static String join(final String separator, final String... strings) {
        final StringBuilder sb = new StringBuilder();
        for (final String string : strings) {
            sb.append(string);
            sb.append(separator);
        }
        return sb.toString().trim();
    }
}
