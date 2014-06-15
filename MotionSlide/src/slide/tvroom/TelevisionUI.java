package slide.tvroom;

import java.awt.Color;

import br.com.etyllica.core.Drawable;
import br.com.etyllica.core.graphics.Graphic;

public class TelevisionUI implements Drawable{

	private int volume = 15;
	
	private int h = 0;
	
	private int w = 0;
	
	public TelevisionUI(int w, int h){
		super();
		
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.GREEN);
		
		for(int i=0; i<volume; i++){
			
			g.fillRect(900, (h-60)-i*30, 40, 10);
			
		}
		
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}	
	
}
