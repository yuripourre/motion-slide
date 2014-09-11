package slide;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import slide.geography.GeographySlide;
import br.com.etyllica.animation.scripts.AnimationScript;
import br.com.etyllica.animation.scripts.FadeOutAnimation;
import br.com.etyllica.animation.scripts.SingleIntervalAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.label.TextLabel;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.TextLayer;

public class MathSlide extends SlideApplication{
	
	private List<TextLayer> mathTexts = new ArrayList<TextLayer>();
		
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
		
		createLine("1-1=2");
		
		createLine("15x4=60");
		
		loading = 30;
				
		SingleIntervalAnimation fadeIn = createText("Things like Math");
		
		FadeOutAnimation fadeOut = new FadeOutAnimation(fadeIn.getTarget(), 4000, 3000);
		
		fadeIn.setNext(fadeOut);
		
		loading = 60;
		
		createButtons();
				
		loading = 100;
		
	}
	
	private TextLayer createLine(String text){
		
		final int margin = 86;
		
		final int offsetY = 82;
		
		final int spacing = 24;
		
		TextLayer mathText = new TextLayer(margin, offsetY+spacing*mathTexts.size(), text);
		mathText.setSize(28f);
		mathText.setColor(Color.BLACK);
		mathText.setFontName("Calligraserif.ttf");
		
		mathTexts.add(mathText);
		
		return mathText;
		
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
		
		for(TextLayer mathText: mathTexts){
			mathText.draw(g);	
		}
		
		super.draw(g);
		
	}
	

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			returnApplication = new GeographySlide(w, h);
		}
		
		return null;
	}
	

}
