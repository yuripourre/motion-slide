package slide;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.TextLayer;

public class FirstSlide extends Application{

	private List<TextLayer> textList = new ArrayList<TextLayer>();
	
	private int texts = 0;
	
	public FirstSlide(int w, int h) {
		super(w, h);
	}
	
	
	
	@Override
	public void load() {
		
		loading = 10;

		createText("I was wondering...");
				
		createText("If there is a way");
		
		createText("To make things funnier");
		
		loading = 100;
		
	}
	
	private void createText(String text){
		
		int x = 120;
		int y = 50;
		
		int offsetY = 30;
		
		final int animationDelay = 2000;
		
		final int animationTime = 3000;
		
		TextLayer textLayer = new TextLayer(x, y+offsetY*texts, text);
		
		FadeInAnimation fadeIn = new FadeInAnimation(animationDelay*texts, animationTime);
		
		fadeIn.setTarget(textLayer);
		
		this.scene.addAnimation(fadeIn);
		
		texts++;
		
		textList.add(textLayer);		
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.BLUE);		
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
		
		return null;
	}
	

}
