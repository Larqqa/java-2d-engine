package program;

import lombok.Getter;
import lombok.Setter;

public class Settings {
    @Getter @Setter private String title = "Game engine";
    @Getter @Setter private int clearColor = 0xFF000000;
    @Getter @Setter private int width = 200;
    @Getter @Setter private int height = 200;
    @Getter @Setter private double scale = 2.0;
    @Getter @Setter private double frameCap = 1.0 / 60.0;
}