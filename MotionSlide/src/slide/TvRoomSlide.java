package slide;


import java.awt.Color;

import slide.capture.SkelAnimation;
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
		
		tv = new TelevisionUI(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;
				
		background = new ImageLayer("tvroom/living-room-tnlozkiz.jpg");		
		
		light = new ImageLayer(174, 97, "tvroom/light.png");
		
		createText("Things like control your house...");
				
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(x, y, w, h);
				
		background.draw(g);
		
		if(activate){
			light.draw(g);
		}
		
		tv.draw(g);
		
		super.draw(g);
		
	}

	@Override
	public void update(long now){
		
		if(mx>w/2){
			
			tv.setVolume(15-((my*15)/h));
			
		}else{
			
			light.setOpacity(255-(my*255)/h);
			
		}
		
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
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			returnApplication = new SkelAnimation(w, h);
		}
				
		return null;
		
	}

}
