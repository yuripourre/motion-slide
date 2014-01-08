package slide.tvroom;

import java.awt.Color;

import br.com.etyllica.core.Drawable;
import br.com.etyllica.core.video.Graphic;

public class TelevisionUI implements Drawable{

	private int volume = 15;
	
	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.GREEN);
		
		for(int i=0; i<volume; i++){
			
			g.fillRect(900, 60+i*30, 40, 10);
			
		}
		
	}
	
}
