package util;

public class Util {
    public static String extractTagName(String tag) {
        return tag.replace("/", "").split(" ")[0].toLowerCase();
    }
}