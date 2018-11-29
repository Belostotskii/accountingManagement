package telran.security.util;

public enum Flag {

	CAPITAL_LATER(1),
	LOWER_CASE(2),
	DIGIT(4),
	SPEC_SYMBOL(8);
	
	private int bit;
	private Flag(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return bit;
	}
		
}
