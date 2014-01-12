package slide.geography;

import br.com.etyllica.core.Drawable;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.TextLayer;

public class Continent implements Drawable{
	
	private TextLayer text;
	
	private String name;
	
	private BufferedLayer layer;
	
	private boolean onMouse = false;
		
	public Continent(String name, BufferedLayer layer) {
		super();
		this.name = name;
		this.layer = layer;
		
		text = new TextLayer(layer.getX(), layer.getY(), name);
		text.setBorder(true);
		text.setSize(18);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedLayer getLayer() {
		return layer;
	}

	public void setLayer(BufferedLayer layer) {
		this.layer = layer;
	}

	@Override
	public void draw(Graphic g) {

		layer.draw(g);
		
		if(onMouse){
			text.draw(g);
		}
		
	}
	
	public boolean verifyColision(int mx, int my){
				
		if(layer.colideAlphaPoint(mx, my)){
			
			onMouse = true;
			
			layer.offsetRGB(-80, 80, -70);
			
			text.setCoordinates(mx-20, my-20);
					
		}else{
			
			onMouse = false;
			
			layer.resetImage();
			
		}
		
		return onMouse;
		
	}
	
}
