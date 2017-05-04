package thread;

public class UpperAlphabetThread extends UpperAlphabet implements Runnable {

	@Override
	public void run() {
		int count = chars.length;
		for(int i = 0; i < count; i++ ){
			System.out.print( chars[ i ]);
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
