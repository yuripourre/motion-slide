

import slide.FirstSlide;
import slide.MapSlide;
import slide.MathSlide;
import br.com.etyllica.EtyllicaFrame;

public class MotionSlide extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MotionSlide() {
		super(640, 480);
	}
	
	public static void main(String[] args){
		MotionSlide slide = new MotionSlide();
		slide.init();
	}
	
	@Override
	public void startGame() {
		
		//setMainApplication(new FirstSlide(w,h));
		//setMainApplication(new MathSlide(w,h));
		setMainApplication(new MapSlide(w,h));
		
	}
	
}
