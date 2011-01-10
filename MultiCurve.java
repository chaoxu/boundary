package boundary;
import java.util.*;
public class MultiCurve {
	SurfaceWord sw;
	ArrayList<Curve> curves;
	
	public int size(){
		return curves.size();
	}
	
	public int length(){
		int sum=0;
		for(int i=0;i<curves.size();i++){
			sum+=curves.get(i).length();
		}
		return sum;
	}
}
