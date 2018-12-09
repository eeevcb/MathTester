import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Writer {

	public static void write(Stack<Question> pStack) throws IOException {
		float percent = ((2 * 100f)/Integer.parseInt(View.q));
		String percentVal = Integer.toString((int)percent);
		String userHomeFolder = System.getProperty("user.home") + "\\Desktop";
		File reportFile = new File(userHomeFolder, "Report.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(reportFile));
		
		ArrayList<String> solutionStrings = new ArrayList<>();
		
		
		for (int i = 1; i <= View.totalQ; i++) {
			solutionStrings.add(View.reportStack.get(i-1).toString());
			
		}
		Collections.reverse(solutionStrings);
		try {
			for (int i = 1; i <= View.totalQ; i++) {
				
				if ( i < 10) {
					out.write("Question 0" + i + ": " + View.reportStack.pop() + " | SOLUTION: " + solutionStrings.get(i-1) + " = " 
							+ " | Correct on first guess? " + " Y |"); 																									
					//TODO Finish 
					out.newLine();
				} else {
					out.write("Question " + i + ": " + View.reportStack.pop() + " | SOLUTION: " + solutionStrings.get(i-1) + " = " 
							+ " | Correct on first guess? " + " Y |"); 																									
					//TODO Finish 
					out.newLine();
				}
				
			}
		

		} finally {
			out.newLine();
			out.write("Total correct on first attempt: " + "/" + View.totalQ); //TODO add first guess correct counter
			out.newLine();
			out.write("Precent correct on first attempt: " + percentVal +"%"); //TODO returns 0.0%?
			
			if (Desktop.isDesktopSupported() == true) {
				Desktop.getDesktop().open(reportFile);
				out.close();
			}

		}
	}
}
