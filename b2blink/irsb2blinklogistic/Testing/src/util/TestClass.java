package util;

public class TestClass {
	public static void main(String args[]) {
		Double a = 12892535D;
		float f1 = a.floatValue();
		float f2 = 12_892_535;
		System.out.println(Float.parseFloat("1.2892535E7"));
		System.out.println(f1 + " - " + f2);
	}
}
