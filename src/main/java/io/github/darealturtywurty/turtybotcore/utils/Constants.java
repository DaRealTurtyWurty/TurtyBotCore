package io.github.darealturtywurty.turtybotcore.utils;

import java.awt.Color;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.github.darealturtywurty.turtybotcore.utils.json.StringDateMapAdapter;

public final class Constants {
    // Discord
    public static final long TEST_SERVER_ID = 819294753732296776L;
    // Time conversion
    public static final long SECOND_TO_MILLI = 1000L;
    
    public static final long MINUTE_TO_MILLI = 60000L;
    public static final long HOUR_TO_MILLI = (long) 3.6e+6;
    public static final long DAY_TO_MILLI = (long) 8.64e+7;
    public static final long WEEK_TO_MILLI = (long) 6.048e+8;
    public static final long MONTH_TO_MILLI = (long) 2.628e+9;
    public static final long YEAR_TO_MILLI = (long) 3.154e+10;
    // Regexs
    public static final Pattern URL_PATTERN = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    
    // Utility
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().disableHtmlEscaping()
            .registerTypeAdapter(new TypeToken<Map<String, Date>>() {
            }.getType(), new StringDateMapAdapter()).create();
    
    public static final Logger LOGGER = Logger.getGlobal();
    public static final DateFormat DATE_FORMAT = DateFormat.getInstance();
    public static final Random RANDOM = new Random();
    
    private Constants() {
    }
    
    // Colours
    public static final class ColourConstants {
        public static final Color BROWN = new Color(102, 51, 0);
        public static final Color DARK_BLUE = new Color(0, 0, 204);
        public static final Color DARK_BROWN = new Color(51, 0, 0);
        public static final Color DARK_GREEN = new Color(0, 153, 0);
        public static final Color DARK_RED = new Color(204, 0, 0);
        public static final Color DARK_YELLOW = new Color(255, 204, 0);
        public static final Color GOLD = new Color(255, 204, 51);
        public static final Color LIGHT_BLUE = new Color(51, 153, 255);
        public static final Color LIGHT_BROWN = new Color(153, 102, 0);
        public static final Color LIGHT_GREEN = new Color(0, 255, 51);
        public static final Color LIGHT_ORANGE = new Color(255, 153, 0);
        public static final Color LIGHT_RED = new Color(255, 51, 51);
        public static final Color LIGHT_YELLOW = new Color(255, 255, 153);
        public static final Color PURPLE = new Color(102, 0, 153);
        
        private ColourConstants() {
        }
    }
}
