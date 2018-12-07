import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Writer {

	public static void write(Stack<Question> pStack) throws IOException {
		String userHomeFolder = System.getProperty("user.home") + "\\Desktop";
		File reportFile = new File(userHomeFolder, "Report.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(reportFile));
		try {
			for (int i = 1; i <= View.totalQ; i++) {
				out.write("Question " + i + ": " + View.reportStack.peek() + " --- SOLUTION: "); 																									
				//TODO Add: Solution string, user tries per question, something else??
				View.reportStack.pop();
				out.newLine();
			}

		} finally {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(reportFile);
				out.close();
			}

		}
	}
}
