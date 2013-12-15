

import slide.FirstSlide;
import br.com.etyllica.Etyllica;

public class MotionSlide extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MotionSlide() {
		super(640, 480);
	}
	
	@Override
	public void startGame() {
		
		setMainApplication(new FirstSlide(w,h));
		
	}
	
}
