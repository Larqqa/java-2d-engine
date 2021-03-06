import program.*;
import engine.*;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        final Settings settings = new Settings();
        final Engine engine = new Engine(settings);
        engine.start();
    }
}