package slide;


import slide.capture.SkelAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class FirstSlide extends SlideApplication{
	
	private ImageLayer background; 
	
	public FirstSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		super.load();
		
		loading = 10;

		background = new ImageLayer("bg.png");
		
		createText("I was wondering...");
				
		createText("If there is a way");
		
		createText("To make things funnier");
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		
		background.draw(g);
		
		drawCamera(g);
		
		for(TextLayer text: textList){
			text.draw(g);	
		}
		
	}
	

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		return super.updateMouse(event);
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)){
			//returnApplication = new MathSlide(w, h);
			returnApplication = new SkelAnimation(w, h);
		}
		
		return null;
	}
	

}
