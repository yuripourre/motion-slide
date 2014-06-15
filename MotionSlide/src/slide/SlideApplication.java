package slide;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.abby.Application3D;
import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.animation.scripts.SingleIntervalAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.TextLayer;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.LeftColorFilter;
import br.com.etyllica.motion.filter.color.RightColorFilter;

public abstract class SlideApplication extends Application3D{

	public SlideApplication(int w, int h) {
		super(w, h);
	}

	protected List<TextLayer> textList = new ArrayList<TextLayer>();

	private int texts = 0;

	private Camera cam;

	private BufferedLayer bufferedLayer;
	
	private BufferedImage mirror = null;

	private Component screen = new Component(0, 0, w, h);
	
	private LeftColorFilter leftColorFilter;
	
	private RightColorFilter rightColorFilter;

	protected Component leftPoint;
	
	protected Component rightPoint;

	@Override
	public void load() {

		glViewport (0, 0, w, h);
		
		loadingPhrase = "Opening Camera";

		if(sessionMap.get("CAMERA")==null){
			cam = new CameraV4L4J(0);
			this.sessionMap.put("CAMERA", cam);
		}

		loadingPhrase = "Setting Filter";

		final int border = 10;
		
		final int tolerance = 40;
		
		BufferedImage buffer = cam.getBufferedImage(); 
		
		int w = buffer.getWidth();
		int h = buffer.getHeight();
		
		final Color color = new Color(106, 64, 52);
		
		leftColorFilter = new LeftColorFilter(w, h, color);
		
		leftColorFilter.setColor(color);
		leftColorFilter.setTolerance(tolerance);		
		leftColorFilter.getSearchStrategy().setBorder(border);
		
		rightColorFilter = new RightColorFilter(w, h, color);
		
		rightColorFilter.setColor(color);
		rightColorFilter.setTolerance(tolerance);
		rightColorFilter.getSearchStrategy().setBorder(border);
		
		
		leftPoint = leftColorFilter.filterFirst(buffer, screen);
		
		rightPoint = rightColorFilter.filterFirst(buffer, screen);

		bufferedLayer = new BufferedLayer(0, 0);

		updateAtFixedRate(5);
		
		loading = 5;
	}

	@Override
	public void timeUpdate(long now) {
		//Get the Camera image
		bufferedLayer.setBuffer(cam.getBufferedImage());

		//Normally the camera shows the image flipped, but we want to see something like a mirror
		//So we flip the image
		bufferedLayer.flipHorizontal();
		
		bufferedLayer.resize(w, h);
				
		mirror = bufferedLayer.getModifiedBuffer();
		
		//Now we search for the first pixel with the desired color in the whole screen
		leftPoint = leftColorFilter.filterFirst(mirror, screen);
		
		rightPoint = rightColorFilter.filterFirst(mirror, screen);	

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		//When mouse clicks, the color filter tries to find
		//the color we are clicking on
		
		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){	
			
			Color color = new Color(mirror.getRGB((int)event.getX(), (int)event.getY()));
			
			System.out.println("Red: "+color.getRed());
			System.out.println("Green: "+color.getGreen());
			System.out.println("Blue: "+color.getBlue());
			
			leftColorFilter.setColor(new Color(mirror.getRGB((int)event.getX(), (int)event.getY())));
			
		}else if(event.isButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)){
			
			rightColorFilter.setColor(new Color(mirror.getRGB((int)event.getX(), (int)event.getY())));
			
		}

		return GUIEvent.NONE;
	}

	protected SingleIntervalAnimation createText(String text){

		float fontSize = 30;

		int x = 0;

		int y = 80;

		int offsetY = 50;

		final int animationDelay = 2000;

		final int animationTime = 3000;

		TextLayer textLayer = new TextLayer(0, y+offsetY*texts, text);

		textLayer.centralizeX(-w/6, w);

		textLayer.setBorder(true);

		textLayer.setSize(fontSize);

		FadeInAnimation fadeIn = new FadeInAnimation(animationDelay*texts, animationTime);

		fadeIn.setTarget(textLayer);

		this.scene.addAnimation(fadeIn);

		texts++;

		textList.add(textLayer);

		return fadeIn;

	}

	public void draw(Graphic g){

		for(TextLayer text: textList){
			text.draw(g);	
		}

	}

	protected void drawCamera(Graphic g) {

		if(mirror==null){
			return;
		}

		g.setAlpha(80);

		//Draw the mirror image
		g.drawImage(mirror, 0, 0);

		//Set a Color to our Point
		g.setColor(Color.CYAN);
		g.fillCircle((int)leftPoint.getX(), (int)leftPoint.getY(), 10);
		
		g.setColor(Color.RED);
		g.fillCircle((int)rightPoint.getX(), (int)rightPoint.getY(), 10);

		g.setAlpha(100);

	}
	
	

}
