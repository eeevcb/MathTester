public class AddQuestion extends Question {

	public AddQuestion(int p_left, int p_right) {
		super(p_left, p_right, p_left + p_right);
	}

	@Override
	public String toString() {
		return "" + getLeft() + " + " + getRight();
	}

	public String queryString() {
		return toString() + " = ?";
	}

	public String solnString() {
		return toString() + " = " + getResult();
	}

}