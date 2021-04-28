 class TestB extends Tester2 {
	 String s =  this.toString();
	 int a = Roar();

	private int Roar() {
		System.out.println("sub");
		return 1;
	}

}

 public class Tester {
	protected void roar(Integer a) {
		a++;
	}
	
	public static void main(String[] args) throws Exception {
		Integer a = 1;
		new Tester().roar(a);
		System.out.println(a);
	}
}
 
 class Tester2 {
		
	}
