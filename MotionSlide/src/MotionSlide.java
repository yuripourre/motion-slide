

import slide.FirstSlide;
import slide.MapSlide;
import slide.MathSlide;
import slide.TvRoomSlide;
import br.com.etyllica.EtyllicaFrame;

public class MotionSlide extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MotionSlide() {
		super(960, 540);
	}
	
	public static void main(String[] args){
		MotionSlide slide = new MotionSlide();
		//slide.setUndecorated(true);
		slide.init();
	}
	
	@Override
	public void startGame() {
		
		//setMainApplication(new FirstSlide(w,h));
		//setMainApplication(new MathSlide(w,h));
		//setMainApplication(new MapSlide(w,h));
		setMainApplication(new TvRoomSlide(w,h));
		
		//Math Background: http://www.iwallscreen.com/stock/engineering-graph-sheets-crumpled-notebook-paper.jpg
		//TV Room: http://home-improvement-review.com/wp-content/uploads/2013/12/living-room-ideas-with-tv-on-wallothers-tv-wall-mounting-ideas-modern-living-room-tv-wall-mount-tnlozkiz.jpg
	}
	
}
