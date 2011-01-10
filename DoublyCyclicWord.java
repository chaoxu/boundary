package boundary;

public class DoublyCyclicWord extends CyclicWord{
	public DoublyCyclicWord() {
	}
	public DoublyCyclicWord(int[] p, int b) {
		super(p, b);
	}
	public DoublyCyclicWord next(){
		CyclicWord v = super.next();
		while(true){
			if(v.isDoublySmallest()){
				return new DoublyCyclicWord(v.word, base);
			}
			v = v.next();
		}
	}	
}
