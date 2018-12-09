public abstract class Question {
	private int mLeft;
	private int mRight;
	private static int mResult;
	private final static int RANGE = 1;
	
	public Question(int pLeft, int pRight, int pResult) {
		setLeft(pLeft);
		setRight(pRight);
		setResult(pResult);
	}
	
	public static Question newQuestion() {
		int questionType = (int) (Math.random()*2 +1); //returns a random 1 or 2
		if (questionType == 1) {
			return new AddQuestion(randomNumber(), randomNumber());
			
		} else {
			return new SubQuestion(randomNumber(), randomNumber());
			
		}		
	
	}
	private void setResult(int pResult) {
		mResult = pResult;

	}
	private static int randomNumber() {
		return (int) (Math.random()*RANGE);
	}
	private void setRight(int pRight) {
		mRight = pRight;

	}

	private void setLeft(int pLeft) {
		mLeft = pLeft;
	
	}
	
	protected int getLeft() {
		return mLeft;
	}

	protected int getRight() {
		return mRight;
	}
	
	protected static int getResult() {
	 return mResult;
	}
	
	public static boolean checkAnswer(int pInt) {
		if (pInt == getResult()) {
			return true;
		}
		return false;
	}

}
