package boundary;

public class SimpleCurve extends Curve {
	public SimpleCurve(){
	}
	public SimpleCurve(PrimitiveCyclicWord c, SurfaceWord s) {
		super(c, s);
	}
	public SimpleCurve next(){
		Curve next = super.next();
		while(true){
			if(next.selfIntersection(true)==0){
				return new SimpleCurve((PrimitiveCyclicWord) next.baseword, sw);
			}
			next = next.next();
		}
	}
}
