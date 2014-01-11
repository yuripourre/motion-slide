package slide;


import java.awt.Color;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class MapSlide extends SlideApplication{
	
	private ImageLayer map;
	
	private BufferedLayer africa;
	
	private BufferedLayer antarctica;
	
	private BufferedLayer australia;
	
	private BufferedLayer asia;
	
	private BufferedLayer europa;
	
	private BufferedLayer northAmerica;
	
	private BufferedLayer southAmerica;
	
	private int mx = 0, my = 0;
		
	public MapSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;
		
		map = new ImageLayer("geo/map.png");		
		map.centralizeX(0, w);
		map.centralizeY(0, h);
		
		southAmerica = new BufferedLayer(map.getX()+142, map.getY()+133, "geo/south_america.png");
		
		northAmerica = new BufferedLayer(map.getX()+59, map.getY()+6, "geo/north_america.png");
		
		africa = new BufferedLayer(map.getX()+248, map.getY()+84, "geo/africa.png");
		
		antarctica = new BufferedLayer(map.getX()+113, map.getY()+274, "geo/antarctica.png");
		
		australia = new BufferedLayer(map.getX()+457, map.getY()+160, "geo/australia.png");
		
		asia = new BufferedLayer(map.getX()+315, map.getY()+12, "geo/asia.png");
		
		europa = new BufferedLayer(map.getX()+248, map.getY()+10, "geo/europa.png");
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.WHITE);		
		g.fillRect(x, y, w, h);
		
		for(TextLayer text: textList){
			text.draw(g);	
		}
		
		map.draw(g);
		
		africa.draw(g);
		
		antarctica.draw(g);
		
		australia.draw(g);
		
		asia.draw(g);
		
		europa.draw(g);
		
		northAmerica.draw(g);
		
		southAmerica.draw(g);
		
	}

	@Override
	public void update(long now){
	
		verifyColision(africa);
		
		verifyColision(antarctica);
		
		verifyColision(australia);
		
		verifyColision(asia);
		
		verifyColision(europa);
		
		verifyColision(southAmerica);
		
		verifyColision(northAmerica);
		
	}
	
	private void verifyColision(BufferedLayer layer){
		
		if(layer.colideAlphaPoint(mx, my)){
			layer.offsetRGB(-80, 80, -70);
		}else{
			layer.resetImage();
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
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			returnApplication = new TvRoomSlide(w, h);
		}
		
		return null;
	}
	

}
