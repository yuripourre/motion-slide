

import slide.FirstSlide;
import slide.LastSlide;
import slide.MathSlide;
import slide.TvRoomSlide;
import slide.capture.SkelAnimation;
import slide.geography.GeographySlide;
import br.com.abby.loader.MeshLoader;
import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;

public class MotionSlide extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MotionSlide() {
		super(960, 540);
	}
	
	public static void main(String[] args){
		MotionSlide slide = new MotionSlide();
		//slide.setUndecorated(true);
		slide.addLoader(MeshLoader.getInstance());
		slide.init();
	}
	
	@Override
	public Application startApplication() {
		
		//return new FirstSlide(w,h);
		//return new MathSlide(w,h);
		//return new MapSlide(w,h);
		//return new TvRoomSlide(w,h);
		
		return new SkelAnimation(w,h);
		
		//return new LastSlide(w,h);
		
		//Resources:		
		//Math Background: http://www.iwallscreen.com/stock/engineering-graph-sheets-crumpled-notebook-paper.jpg
		//TV Room: http://home-improvement-review.com/wp-content/uploads/2013/12/living-room-ideas-with-tv-on-wallothers-tv-wall-mounting-ideas-modern-living-room-tv-wall-mount-tnlozkiz.jpg
	}
	
}
