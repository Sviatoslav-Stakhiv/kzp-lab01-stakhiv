package ua.lpnu.kzp;

public final class ArgumentParser {
    private ArgumentParser() {}

    public static AppArguments parse(String[] args) {
        String input = "data/input.csv"; // Значення за замовчуванням
        String output = "out/report.txt";
        boolean help = false;
        boolean version = false;

        for (int i = 0; i < args.length; i++) {
            if ("--help".equals(args[i])) { help = true; }
            else if ("--version".equals(args[i])) { version = true; }
            else if ("--input".equals(args[i]) && i + 1 < args.length) { input = args[++i]; }
            else if ("--output".equals(args[i]) && i + 1 < args.length) { output = args[++i]; }
        }
        return new AppArguments(input, output, help, version);
    }
}