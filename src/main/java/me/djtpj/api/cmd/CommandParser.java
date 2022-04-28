package me.djtpj.api.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    public static String[] parseArgs(String base) {
        // TODO: 4/24/2022 parse based on info inside of quotations

        ArrayList<String> parsed = new ArrayList<>();

        if (!base.contains("\"")) {
            for (String s : base.split(" ")) {
                parsed.add(s);
            }
        }

        else {
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(base);

            while (m.find()) {
                parsed.add(m.group(1).replace("\"", ""));
            }
        }

        return parsed.toArray(new String[parsed.size()]);
    }

    public static String[] trimToIndex(int index, String... args) {
        ArrayList<String> trimmed = new ArrayList<>(Arrays.asList(args));

//        for (String s : args) {
//            if (Arrays.asList(args).indexOf(s) <= index - 1) {
//                trimmed.remove(s);
//            }
//        }

        trimmed = new ArrayList<>(trimmed.subList(index, trimmed.size()));

        return trimmed.toArray(new String[trimmed.size()]);
    }
}
