package slide;


import java.awt.Color;

import slide.tvroom.TelevisionUI;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class TvRoomSlide extends SlideApplication{
	
	private TelevisionUI tv;
	
	private ImageLayer background;
	
	private ImageLayer light;
		
	private int mx = 0, my = 0;
	
	boolean activate = true;
		
	public TvRoomSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;
		
		background = new ImageLayer("tvroom/living-room-tnlozkiz.jpg");		
		
		light = new ImageLayer(174, 97, "tvroom/light.png");
		
		tv = new TelevisionUI();
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.WHITE);		
		g.fillRect(x, y, w, h);
		
		for(TextLayer text: textList){
			text.draw(g);	
		}
		
		background.draw(g);
		
		if(activate){
			light.draw(g);
		}
		
		tv.draw(g);

	}

	@Override
	public void update(long now){
		
	}
	
	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		mx = event.getX();
		my = event.getY();
		
		return null;
	}
		
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyUp(KeyEvent.TSK_SETA_CIMA)){
						
			activate = !activate;
			
		}
				
		return null;
		
	}

}
