package slide;


import java.awt.Color;

import br.com.etyllica.animation.AnimationScript;
import br.com.etyllica.animation.scripts.FadeOutAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.label.TextLabel;
import br.com.etyllica.layer.TextLayer;

public class MathSlide extends SlideApplication{
	
	private Button addition;
	
	private Button subtraction;
	
	private Button multiplication;
	
	private Button division;
	
	public MathSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;

		AnimationScript fadeIn = createText("Things like Math");
		
		FadeOutAnimation fadeOut = new FadeOutAnimation(fadeIn.getTarget(), 4000, 3000);
		
		fadeIn.setNext(fadeOut);
		
		TextLabel additionLabel = new TextLabel("+", 20);
				
		addition = new Button(300, 90, 50, 50);
		addition.setLabel(additionLabel);
		this.add(addition);
		
		TextLabel subtractionLabel = new TextLabel("-", 20);
		
		subtraction = new Button(300, 90+50+10, 50, 50);
		subtraction.setLabel(subtractionLabel);
		this.add(subtraction);
		
		TextLabel multiplicationLabel = new TextLabel("x", 20);
		
		multiplication = new Button(300, 90+(50+10)*2, 50, 50);
		multiplication.setLabel(multiplicationLabel);
		this.add(multiplication);
		
		TextLabel divisionLabel = new TextLabel("÷", 20);
		
		division = new Button(300, 90+(50+10)*3, 50, 50);
		division.setLabel(divisionLabel);
		this.add(division);
		
		loading = 100;
		
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
