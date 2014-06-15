package slide;


import java.awt.Color;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.TextLayer;

public class LastSlide extends SlideApplication{
	
	public LastSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;

		createText("I know there are similar technologies");
				
		createText("But I wanted something");
		
		createText("Easy to use and integrate");
		
		createText("Open Source");
		
		createText("Cheap");
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.BLACK);		
		g.fillRect(x, y, w, h);
		
		for(TextLayer text: textList){
			text.draw(g);	
		}
		
	}
	

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)){
			returnApplication = new MathSlide(w, h);
		}
		
		return null;
	}
	

}
