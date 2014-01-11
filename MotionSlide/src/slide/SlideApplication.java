package slide;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.animation.AnimationScript;
import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.context.Application;
import br.com.etyllica.layer.TextLayer;

public abstract class SlideApplication extends Application{

	public SlideApplication(int w, int h) {
		super(w, h);
	}

	protected List<TextLayer> textList = new ArrayList<TextLayer>();
	
	private int texts = 0;
	
	protected AnimationScript createText(String text){
		
		float fontSize = 30;
		
		int x = 120;
		
		int y = 80;
		
		int offsetY = 50;
		
		final int animationDelay = 2000;
		
		final int animationTime = 3000;
		
		TextLayer textLayer = new TextLayer(x, y+offsetY*texts, text);
		
		textLayer.setBorder(true);
		
		textLayer.setSize(fontSize);
		
		FadeInAnimation fadeIn = new FadeInAnimation(animationDelay*texts, animationTime);
		
		fadeIn.setTarget(textLayer);
		
		this.scene.addAnimation(fadeIn);
		
		texts++;
		
		textList.add(textLayer);
		
		return fadeIn;
		
	}
	
}
