package ua.lpnu.kzp;

import java.util.Properties;
import java.io.IOException;

/** Точка входу, яка лише передає керування контролеру. */
public final class Main {
    private Main() {}

    public static void main(String[] args) {
        AppArguments parsedArgs = ArgumentParser.parse(args);

        if (parsedArgs.help()) {
            System.out.println("Використання: java -jar lab.jar [--help] [--version] [--input <файл>] [--output <файл>]");
            return;
        }

        if (parsedArgs.version()) {
            System.out.println("Версія програми: " + getVersionFromProperties());
            return;
        }

        FuelController controller = new FuelController();
        controller.run(parsedArgs);
    }

 private static String getVersionFromProperties() {
        try (var is = Main.class.getResourceAsStream("/app.properties")) {
            if (is != null) {
                var props = new Properties();
                props.load(is);
                return props.getProperty("version", "unknown");
            }
        } catch (IOException ignored) {
            // Файл не знайдено або неможливо прочитати, повертаємо значення за замовчуванням
        }
        return "unknown";
    } 
}