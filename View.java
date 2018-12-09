
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
	private Stack<Question> unansweredQ = new Stack<>();
	private Stack<Question> answeredQ = new Stack<>();
	public static Stack<Question> reportStack = new Stack<Question>();
	static int questionCounter;
	static int indexer;
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
		
		/**
		 * Question counter to count 1 thru totalQ. 
		 * Indexer to start at end of end of stack due to LIFO
		 */
		
		questionCounter = 1;
		indexer = (totalQ - 1);
		
		JPanel panelNumberOfQuestions = new JPanel(new FlowLayout());
		mNumQText = new JTextField();
		mNumQText.setColumns(10);
		mNumQText.setEditable(false);
		mNumQText.setText("Question : " + questionCounter + "/" + totalQ);
		panelNumberOfQuestions.add(mNumQText);

		JFrame mainConsole = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		mainConsole.setLayout(new FlowLayout());
		
		//Text Fields
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
		
		//Buttons disable next/previous until 1st question is answered correct. 
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
		
		//Panels
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
		// clones stack for report generation
		reportStack.addAll(unansweredQ); 

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
				
				if (Question.getResult() == newInt) {
					
					mCorrectText.setBackground(Color.GREEN);
					mNextButton.setEnabled(true);

					
					if (getIncorrectStatus() == true) {
						mIncorrectText.setBackground(null);
					}
					if (questionCounter == totalQ) {
						//If on last question, or answering only 1 question 
						//call generate report on correct answer
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
					mCorrectText.setBackground(null); 
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
			mQuestionText.setText(unansweredQ.get(indexer).toString());
			answeredQ.push(unansweredQ.pop());

		} else if (actionCommand.equals("Previous Question")) {
			mNextButton.setEnabled(true);
			decrementCounter();
			lockButtonsOnPrevious();
			resetTextColor();
			mNumQText.setText("Question : " + questionCounter + "/" + totalQ);
			mQuestionText.setText(answeredQ.get(questionCounter-1).toString());
			unansweredQ.push(answeredQ.pop());

		} else if (actionCommand.equals("Show Solution")) {
			Question solution = unansweredQ.elementAt(indexer);
			
			
			System.out.println("Solution " + solution);
			JOptionPane.showMessageDialog(null, unansweredQ.get(indexer) + " = " + Question.getResult(), "Solution Set", JOptionPane.INFORMATION_MESSAGE);

		} else if (actionCommand.equals("Generate Report")) {
			try {
				generateReport(reportStack);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
			

	/**
	 * Checks status of incorrect to reset if incorrect answer was input before a correct answer.
	 * Prevents both boxes being colored at same time after correct answer input. 
	 *
	 * @return
	 */
	private boolean getIncorrectStatus() {
		if (mIncorrectText.getBackground() == Color.red) {
			return true;
		}
		return false;
	}
	/**
	 * Disables next button if at last question. 
	 * Prevents out of bounds exceptions.
	 *
	 */
	private void lockButtonsOnNext() {
		if (((totalQ - unansweredQ.size() == answeredQ.size()) && questionCounter == answeredQ.size()) || totalQ == questionCounter) {
			mNextButton.setEnabled(false);
		}
		
		
	}
	/**
	 * Disables previous button if at question 1 (index 0) to prevent out of bounds exceptions
	 *
	 */
	private void lockButtonsOnPrevious() {
		if (questionCounter == 1) {
			mPreviousButton.setEnabled(false);
		} 
	}
	/**
	 * Resets color of incorrect and correct text fields to default
	 */
	private void resetTextColor() {
		mIncorrectText.setBackground(null);
		mCorrectText.setBackground(null);

	}
	/**
	 *  
	 * @Writer creates .txt report from stack input
	 * @param pStack
	 * @throws IOException
	 */
	private void generateReport(Stack<Question> pStack) throws IOException {
		pStack = View.reportStack;
		Writer.write(pStack);
		messageBox("Report saved to desktop.");
		System.exit(0);

	}
	/**
	 * Decrements questionCounter
	 * Indexer is opposite to traverse Stack backwards
	 * 	
	 */
	private void decrementCounter() {
		if (questionCounter == 1) {
			// do nothing
		} else {
			questionCounter--;
			indexer++;

		}

	}
	/**
	 * 
	 * Increments counter and decrements index.
	 * Indexer is opposite to traverse Stack backwards
	 */
	private void incrementCounter() {
		if (questionCounter == totalQ) {
			// do nothing
		} else {
			questionCounter++;
			indexer--;

		}

	}

	public void messageBox(String pMessage) {
		JOptionPane.showMessageDialog(this, pMessage, "Message", JOptionPane.PLAIN_MESSAGE);
	}

	public void setMain(MainTest pMain) {
		mMain = pMain;

	}

}
