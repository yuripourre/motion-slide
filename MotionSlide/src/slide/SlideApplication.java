package slide;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.animation.AnimationScript;
import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.TextLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.LeftToRightSearch;
import br.com.etyllica.motion.filter.search.RightToLeftSearch;
import br.com.luvia.Application3D;

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
	
	private LeftToRightSearch leftColorFilter;
	private ColorStrategy leftColorStrategy;
	
	private RightToLeftSearch rightColorFilter;
	private ColorStrategy rightColorStrategy;

	protected Point2D leftPoint;
	
	protected Point2D rightPoint;

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
		
		final int tolerance = 16;
		
		final Color color = new Color(106, 64, 52);
		
		//Setting Left Filter
		leftColorFilter = new LeftToRightSearch();
		leftColorFilter.setBorder(border);
		
		leftColorStrategy = new ColorStrategy(color, tolerance);
		
		leftColorFilter.setColorStrategy(leftColorStrategy);
		
		leftPoint = new Point2D(0, 0);

		//Setting Right Filter
		rightColorFilter = new RightToLeftSearch();
		rightColorFilter.setBorder(border);
		
		rightColorStrategy = new ColorStrategy(color, tolerance);
		rightColorFilter.setColorStrategy(rightColorStrategy);
		
		rightPoint = new Point2D(0, 0);

		bufferedLayer = new BufferedLayer(0, 0);

		updateAtFixedRate(5);
		
		loading = 5;
	}

	@Override
	public void timeUpdate(long now){
		//Get the Camera image
		bufferedLayer.setBuffer(cam.getBufferedImage());

		//Normally the camera shows the image flipped, but we want to see something like a mirror
		//So we flip the image
		bufferedLayer.flipHorizontal();
		
		bufferedLayer.resize(w, h);
				
		mirror = bufferedLayer.getModifiedBuffer();
		
		//Now we search for the first pixel with the desired color in the whole screen
		Component leftComponent = leftColorFilter.filterFirst(mirror, screen);
		leftPoint.setLocation(leftComponent.getX(), leftComponent.getY());
		
		Component rightComponent = rightColorFilter.filterFirst(mirror, screen);
		rightPoint.setLocation(rightComponent.getX(), rightComponent.getY());	

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		//When mouse clicks, the color filter tries to find
		//the color we are clicking on
		
		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){	
			
			Color color = new Color(mirror.getRGB((int)event.getX(), (int)event.getY()));
			
			System.out.println("Red: "+color.getRed());
			System.out.println("Green: "+color.getGreen());
			System.out.println("Blue: "+color.getBlue());
			
			leftColorStrategy.setColor(mirror.getRGB((int)event.getX(), (int)event.getY()));
			
		}else if(event.onButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)){
			
			rightColorStrategy.setColor(mirror.getRGB((int)event.getX(), (int)event.getY()));
			
		}

		return GUIEvent.NONE;
	}

	protected AnimationScript createText(String text){

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

		g.setAlpha(50);

		//Draw the mirror image
		g.drawImage(mirror, 0, 0);

		g.setColor(Color.BLACK);
		g.drawShadow(10, 60, "Right Point ("+rightPoint.getX()+", "+rightPoint.getY()+")");
		g.drawShadow(10, 80, "Left Point ("+leftPoint.getX()+", "+leftPoint.getY()+")");
		
		//Set a Color to our Point
		g.setColor(Color.CYAN);
		g.fillCircle((int)leftPoint.getX(), (int)leftPoint.getY(), 10);
		
		g.setColor(Color.RED);
		g.fillCircle((int)rightPoint.getX(), (int)rightPoint.getY(), 10);

		g.setAlpha(100);

	}
	
	

}
