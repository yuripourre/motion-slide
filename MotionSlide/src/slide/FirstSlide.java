package slide;


import java.awt.Color;

import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.TextLayer;

public class FirstSlide extends Application{

	TextLayer text;
	
	String title = "I was wondering...";
	
	public FirstSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;

		text = new TextLayer(20, 50, title);		
		
		FadeInAnimation fadeIn = new FadeInAnimation(2000);
		
		fadeIn.setTarget(text);
		
		this.scene.addAnimation(fadeIn);
		
		
		
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.BLUE);		
		g.fillRect(x, y, w, h);
		
		g.setColor(Color.WHITE);
		text.draw(g);
	}
	

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		return null;
	}
	

}
