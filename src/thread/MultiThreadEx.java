package thread;

public class MultiThreadEx {

	public static void main(String[] args) {
		Thread t1 = new AlphabetThread();
		Thread t2 = new DigitThread();
		Thread t3 = new Thread( new UpperAlphabetThread() );
		
		t1.start();
		t2.start();
		t3.start();
	}
}
