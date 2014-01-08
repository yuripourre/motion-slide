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
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class MathSlide extends SlideApplication{
	
	private TextLayer mathText;
	
	private ImageLayer background;
	
	private Button addition;
	
	private Button subtraction;
	
	private Button multiplication;
	
	private Button division;
	
	private Button result;
	
	public MathSlide(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {
		
		loading = 10;
		
		background = new ImageLayer("math/notebook-paper.jpg");
		
		int margin = 86;
		
		mathText = new TextLayer(margin, 80, "1+1=2");
		mathText.setSize(32f);
		mathText.setColor(Color.BLACK);
		mathText.setFontName("Calligraserif.ttf");
				
		loading = 30;
				
		AnimationScript fadeIn = createText("Things like Math");
		
		FadeOutAnimation fadeOut = new FadeOutAnimation(fadeIn.getTarget(), 4000, 3000);
		
		fadeIn.setNext(fadeOut);
		
		loading = 60;
		
		createButtons();
				
		loading = 100;
		
	}
	
	private void createButtons(){
		
		int buttonSize = 75;
		
		int offsetButtonsX = w-buttonSize*2;
		
		int offsetButtonsY = 50;
		
		int spacing = 10;
				
		TextLabel additionLabel = new TextLabel("+", 20);
				
		addition = new Button(offsetButtonsX, offsetButtonsY, buttonSize, buttonSize);
		addition.setLabel(additionLabel);
		this.add(addition);
		
		TextLabel subtractionLabel = new TextLabel("-", 20);
		
		subtraction = new Button(offsetButtonsX, offsetButtonsY+buttonSize+spacing, buttonSize, buttonSize);
		subtraction.setLabel(subtractionLabel);
		this.add(subtraction);
		
		TextLabel multiplicationLabel = new TextLabel("x", 20);
		
		multiplication = new Button(offsetButtonsX, offsetButtonsY+(buttonSize+spacing)*2, buttonSize, buttonSize);
		multiplication.setLabel(multiplicationLabel);
		this.add(multiplication);
		
		TextLabel divisionLabel = new TextLabel("÷", 20);
		
		division = new Button(offsetButtonsX, offsetButtonsY+(buttonSize+spacing)*3, buttonSize, buttonSize);
		division.setLabel(divisionLabel);
		this.add(division);
		
		TextLabel resultLabel = new TextLabel("=", 20);
		
		result = new Button(offsetButtonsX, offsetButtonsY+(buttonSize+spacing)*4, buttonSize, buttonSize);
		result.setLabel(resultLabel);
		this.add(result);
	}

	@Override
	public void draw(Graphic g) {
		
		background.draw(g);
		
		mathText.draw(g);		
		
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
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			returnApplication = new MapSlide(w, h);
		}
		
		return null;
	}
	

}
