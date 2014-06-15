package slide.geography;


import java.awt.Color;

import slide.SlideApplication;
import slide.TvRoomSlide;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class GeographySlide extends SlideApplication{
	
	private ImageLayer map;
	
	private Continent africa;
	
	private Continent antarctica;
	
	private Continent australia;
	
	private Continent asia;
	
	private Continent europe;
	
	private Continent northAmerica;
	
	private Continent southAmerica;
	
	private int mx = 0, my = 0;
		
	public GeographySlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;
		
		map = new ImageLayer("geo/map.png");		
		map.centralizeX(0, w);
		map.centralizeY(0, h);
		
		southAmerica = new Continent("South America", new BufferedLayer(map.getX()+142, map.getY()+133, "geo/south_america.png"));
		
		northAmerica = new Continent("North America", new BufferedLayer(map.getX()+59, map.getY()+6, "geo/north_america.png"));
		
		africa = new Continent("Africa", new BufferedLayer(map.getX()+248, map.getY()+84, "geo/africa.png"));
		
		antarctica = new Continent("Antarctica", new BufferedLayer(map.getX()+113, map.getY()+274, "geo/antarctica.png"));
		
		australia = new Continent("Australia", new BufferedLayer(map.getX()+457, map.getY()+160, "geo/australia.png"));
		
		asia = new Continent("Asia", new BufferedLayer(map.getX()+315, map.getY()+12, "geo/asia.png"));
		
		europe = new Continent("Europe", new BufferedLayer(map.getX()+248, map.getY()+10, "geo/europe.png"));
		
		createText("Things like Geography...");
		
		loading = 100;
		
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, w, h);
				
		map.draw(g);
		
		africa.draw(g);
		
		antarctica.draw(g);
		
		australia.draw(g);
		
		asia.draw(g);
		
		europe.draw(g);
		
		northAmerica.draw(g);
		
		southAmerica.draw(g);
		
		super.draw(g);
	}

	@Override
	public void update(long now){
	
		africa.verifyColision(mx, my);
		
		antarctica.verifyColision(mx, my);
		
		australia.verifyColision(mx, my);
		
		asia.verifyColision(mx, my);
		
		europe.verifyColision(mx, my);
		
		southAmerica.verifyColision(mx, my);
		
		northAmerica.verifyColision(mx, my);

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
