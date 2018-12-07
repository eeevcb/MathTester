public class SubQuestion extends Question {
	public SubQuestion(int p_left, int p_right) {
		super(p_left, p_right, p_left - p_right);
	}

	public String toString() {
		return "" + getLeft() + " - " + getRight();
	}

	public String queryString() {
		return toString() + " = ?";
	}

	public String solnString() {
		return toString() + " = " + getResult();
	}
}