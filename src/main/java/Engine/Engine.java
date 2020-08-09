package Engine;

public class Engine {
    private final GameSettings settings;
    private final Game game;
    private Window window;
    private Renderer renderer;
    private Input input;
    private static boolean running = false;

    public Engine(Game game, GameSettings settings) {
        this.game = game;
        this.settings = settings;
    }

    public void start() {
        if (running)
            return;
        run();
    }

    public static void stop() {
        running = false;
    }

    private void init() {
        window = new Window(settings);
        renderer = new Renderer(window);
        input = new Input(window);

        game.setRenderer(renderer);
        game.setInput(input);

        running = true;
    }

    private void run() {
        init();

        double firstTime;
        double lastTime = System.nanoTime() / 1e9d;
        double passedTime;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int fps = 0; // Frames per second
        int updates = 0;
        int ups = 0; // Updates per second
        boolean shouldRender;
        double cap = settings.getFpscap();

        while(isRunning()) {
            firstTime = System.nanoTime() / 1e9d;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;
            frameTime += passedTime;
            shouldRender = false;

            // This loop runs at least once per frame
            // Also handles render lag and rendering cap
            while(unprocessedTime >= cap && unprocessedTime != 0) {
                unprocessedTime -= cap;

                // Update game
                game.update();

                updates++;
                shouldRender = true;
            }

            if(frameTime >= 1.0) {
                frameTime -= frameTime;

                fps = frames;
                frames = 0;

                ups = updates;
                updates = 0;

                System.out.println("updates: " + ups + " fps: " + fps);
            }

            if(shouldRender) {
                renderer.clear();
                game.render();
                window.update();

                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        cleanUp();
    }

    private void cleanUp() {
        window.dispose();
    }

    private boolean isRunning() {
        return running;
    }
}
