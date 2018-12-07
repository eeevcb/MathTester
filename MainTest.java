import javax.swing.JFrame;

public class MainTest {
	public View mView;	
	public View getView() {
		return mView;
	}
	
	public void setView(View pView) {
		mView = pView;
	}

	public static void main(String [] args) {
		new MainTest().run();
	}
	
	void run() {
		JFrame.setDefaultLookAndFeelDecorated(false);
		setView(new View(this));
	}
}
