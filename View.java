
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 300;

	private JButton mCheckButton, mPreviousButton, mExitButton, mNextButton, mSolutionButton, mReportButton;
	@SuppressWarnings("unused")
	private MainTest mMain;
	private JTextField mAnswerText, mQuestionText, mNumQText, mIncorrectText, mCorrectText;
	Stack<Question> unansweredQ = new Stack<>();
	Stack<Question> answeredQ = new Stack<>();
	public static Stack<Question> reportStack = new Stack<Question>();
	static int questionCounter;

	static JFrame frame = new JFrame();
	public static String q = (String) JOptionPane.showInputDialog(frame,
			"How many questions would you like to answer?");

	static int totalQ = tryParse(q);

	public static Integer tryParse(String q) {

		try {
			if (Integer.parseInt(q) <= 0) {
				JOptionPane.showMessageDialog(frame, "Input must be positive. Exiting.", "Warning!",
						JOptionPane.WARNING_MESSAGE);
				System.exit(DISPOSE_ON_CLOSE);

			}

			return Integer.parseInt(q);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Input not an integer. Exiting", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			System.exit(DISPOSE_ON_CLOSE);
			return null;
		}
	}

	public View(MainTest pMain) {
		setMain(pMain);
		setQuestion(totalQ);
		questionCounter = totalQ - (totalQ - 1);

		JPanel panelNumberOfQuestions = new JPanel(new FlowLayout());
		mNumQText = new JTextField();
		mNumQText.setColumns(10);
		mNumQText.setEditable(false);
		mNumQText.setText("Question : " + questionCounter + "/" + totalQ);
		panelNumberOfQuestions.add(mNumQText);

		JFrame mainConsole = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		mainConsole.setLayout(new FlowLayout());

		JPanel panelQuestion = new JPanel(new FlowLayout());
		mQuestionText = new JTextField();
		mQuestionText.setColumns(10);
		mQuestionText.setEditable(false);
		mIncorrectText = new JTextField();
		mIncorrectText.setColumns(10);
		mIncorrectText.setText("Incorrect.");
		mIncorrectText.setEditable(false);
		mCorrectText = new JTextField();
		mCorrectText.setColumns(10);
		mCorrectText.setText("Correct!");
		mCorrectText.setEditable(false);
		String str = unansweredQ.peek().toString();
		mQuestionText.setText("What is " + str + " ?");

		panelQuestion.add(mIncorrectText);
		panelQuestion.add(mQuestionText);
		panelQuestion.add(mCorrectText);

		JPanel panelAnswers = new JPanel(new FlowLayout());
		panelAnswers.add(new JLabel("Your answer here: "));
		panelAnswers.requestFocusInWindow();
		mAnswerText = new JTextField();
		mAnswerText.setColumns(20);
		mAnswerText.setHorizontalAlignment(JTextField.CENTER);
		mAnswerText.addActionListener(this);
		panelAnswers.add(mAnswerText);

		JPanel panelButtons = new JPanel(new FlowLayout());
		JPanel panelButtons2 = new JPanel(new FlowLayout());

		mCheckButton = new JButton();
		mCheckButton.setText("Check Answer");
		mCheckButton.addActionListener(this);
		mPreviousButton = new JButton();
		mPreviousButton.setText("Previous Question");
		mPreviousButton.addActionListener(this);
		mPreviousButton.setEnabled(false);
		mNextButton = new JButton();
		mNextButton.setText("Next Question");
		mNextButton.addActionListener(this);
		mNextButton.setEnabled(false);
		mExitButton = new JButton();
		mExitButton.setText("Exit");
		mExitButton.addActionListener(this);
		mSolutionButton = new JButton();
		mSolutionButton.setText("Show Solution");
		mSolutionButton.addActionListener(this);
		mReportButton = new JButton();
		mReportButton.setText("Generate Report");
		mReportButton.addActionListener(this);

		panelButtons.add(mCheckButton);
		panelButtons.add(mPreviousButton);
		panelButtons.add(mNextButton);
		panelButtons2.add(mSolutionButton);
		panelButtons2.add(mReportButton);
		panelButtons2.add(mExitButton);

		JPanel panelMain = new JPanel(new FlowLayout());
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		panelMain.add(panelQuestion, BorderLayout.CENTER);
		panelMain.add(panelAnswers);
		panelMain.add(panelButtons);
		panelMain.add(panelButtons2);
		panelMain.add(panelNumberOfQuestions);

		mainConsole.getRootPane().setDefaultButton(mCheckButton);
		mainConsole.setFont(new Font("System", Font.BOLD, 25));
		Font f = mainConsole.getFont();
		FontMetrics fm = mainConsole.getFontMetrics(f);
		int x = fm.stringWidth("Adam's Math Tester");
		int y = fm.stringWidth(" ");
		int z = mainConsole.getWidth() / 2 - (x / 2);
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "Adam's Math Tester");

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panelMain);

		// set Vis
		pack();
		mainConsole.setLocationRelativeTo(null);
		setVisible(true);
		mAnswerText.requestFocusInWindow();
		// Center the program on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		reportStack.addAll(unansweredQ); // clones stack for report generation

	}

	// Recursive add questions to unanswered Stack
	private void setQuestion(int pIn) {
		if (pIn == 1) {
			unansweredQ.push(Question.newQuestion());
		} else {
			unansweredQ.push(Question.newQuestion());
			setQuestion(pIn - 1);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String actionCommand = e.getActionCommand();

		if (actionCommand.equals("Exit")) {
			System.exit(0);
		}

		else if (actionCommand.equals("Check Answer")) {

			boolean answerClose = false;
			String checker = mAnswerText.getText().trim();

			try {

				int newInt = Integer.parseInt(checker);
				if (Question.checkAnswer(newInt) == true) {
					mCorrectText.setBackground(Color.GREEN);
					mNextButton.setEnabled(true);

					if (getIncorrectStatus() == true) {
						mIncorrectText.setBackground(null);
					}
					if (questionCounter == totalQ) {
						try {
							generateReport(reportStack);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				} else if (Question.checkAnswer(newInt + 1) == true || Question.checkAnswer(newInt - 1) == true) {
					answerClose = true;
					mIncorrectText.setBackground(Color.red);

				} else {
					mIncorrectText.setBackground(Color.red);
				}
				if (answerClose == true) {
					messageBox("So close! Try again!");

				}

			} catch (NumberFormatException format) {
				messageBox("Input must be an Integer.");
			}
		} else if (actionCommand.equals("Next Question")) {
			mPreviousButton.setEnabled(true);
			incrementCounter();
			lockButtonsOnNext();
			resetTextColor();
			mNumQText.setText("Question : " + questionCounter + "/" + totalQ);
			mQuestionText.setText(unansweredQ.peek().toString());
			answeredQ.push(unansweredQ.pop());

		} else if (actionCommand.equals("Previous Question")) {

			decrementCounter();
			lockButtonsOnPrevious();
			resetTextColor();
			mNumQText.setText("Question : " + questionCounter + "/" + totalQ);
			mQuestionText.setText(answeredQ.peek().toString());
			unansweredQ.push(answeredQ.pop());

		} else if (actionCommand.equals("Show Solution")) {
			Question currentQuestion = getCurrentQuestion();

			System.out.println("Current Question " + currentQuestion);
			System.out.println();
			System.out.println("Question Counter " + questionCounter);
			System.out.println();
			System.out.println("Report Stack " + reportStack);
			System.out.println();
			System.out.println("Unanswered Stack" + unansweredQ);
			System.out.println();
			System.out.println("Solution " + solution);
			JOptionPane.showMessageDialog(null, solution, "Solution for: ", JOptionPane.INFORMATION_MESSAGE);

		} else if (actionCommand.equals("Generate Report")) {
			try {
				generateReport(reportStack);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static Question getCurrentQuestion() {
		int indexer = View.questionCounter - 1;
		System.out.println("Indexer " + indexer);
		return View.reportStack.get((View.totalQ - 1) - indexer);

	}

	private boolean getIncorrectStatus() {
		if (mIncorrectText.getBackground() == Color.red) {
			return true;
		}
		return false;
	}

	private void lockButtonsOnNext() {
		if (questionCounter == totalQ) {
			mNextButton.setEnabled(false);
		}
	}

	private void lockButtonsOnPrevious() {
		if (questionCounter == 1) {
			mPreviousButton.setEnabled(false);
		}

	}

	private void resetTextColor() {
		mIncorrectText.setBackground(null);
		mCorrectText.setBackground(null);

	}

	private void generateReport(Stack<Question> pStack) throws IOException {
		pStack = View.reportStack;
		Writer.write(pStack);
		messageBox("Report saved to desktop.");
		System.exit(0);

	}

	private void decrementCounter() {
		if (questionCounter == 1) {
			// do nothing
		} else {
			questionCounter--;

		}

	}

	private void incrementCounter() {
		if (questionCounter == totalQ) {
			// do nothing
		} else {
			questionCounter++;

		}

	}

	public void messageBox(String pMessage) {
		JOptionPane.showMessageDialog(this, pMessage, "Message", JOptionPane.PLAIN_MESSAGE);
	}

	public void setMain(MainTest pMain) {
		mMain = pMain;

	}

}
