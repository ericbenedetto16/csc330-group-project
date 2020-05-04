package finalproject;

public class Program {
	protected static final int WINDOW_WIDTH = 825, WINDOW_HEIGHT = 710, CANVAS_WIDTH = WINDOW_WIDTH/3, CANVAS_HEIGHT = WINDOW_HEIGHT;
	public static boolean paused = false;
	
	public Program() {
		new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public static void main(String[] args) {
		new Program();
	}

}
