package logparser;

import java.util.regex.Pattern;

/**
 * Created by Animesh on 2/3/2016.
 */
public class Constants {
    public static final Pattern LOG_PATTERN = Pattern.compile("logtest-\\d{4}-\\d{2}-\\d{2}\\.log");
}
