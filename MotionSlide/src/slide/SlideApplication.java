package slide;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.animation.AnimationScript;
import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.camera.Camera;
import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.TextLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ColorFilter;
import br.com.etyllica.motion.features.Component;

public abstract class SlideApplication extends Application{

	public SlideApplication(int w, int h) {
		super(w, h);
	}

	protected List<TextLayer> textList = new ArrayList<TextLayer>();

	private int texts = 0;

	private Camera cam;

	private ColorFilter colorFilter;

	private BufferedLayer mirror;

	private Component screen = new Component(w, h);

	private Point2D point;

	@Override
	public void load() {

		loadingPhrase = "Opening Camera";

		cam = new CameraV4L4J(0);

		loadingPhrase = "Setting Filter";

		colorFilter = new ColorFilter();
		colorFilter.setBorder(20);
		colorFilter.setTolerance(20);

		mirror = new BufferedLayer(0, 0);

		loading = 5;
	}

	BufferedImage old = null;

	@Override
	public void update(long now){

		//Get the Camera image
		mirror.setBuffer(cam.getBufferedImage());

		//Normally the camera shows the image flipped, but we want to see something like a mirror
		//So we flip the image
		mirror.flipHorizontal();
		
		mirror.resize(w, h);
		
		//Now we search for the first pixel with the desired color in the whole screen
		point = colorFilter.filterFirst(mirror.getModifiedBuffer(), screen);

		old = mirror.getModifiedBuffer();

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){
			//When mouse clicks with LeftButton, the color filter tries to find
			//the color we are clicking on
			colorFilter.setColor(mirror.getModifiedBuffer().getRGB((int)event.getX(), (int)event.getY()));
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

		if(old==null){
			return;
		}

		g.setAlpha(80);

		//Draw the mirror image
		g.drawImage(old,0,0);

		//Set a Color to our Point
		g.setColor(Color.CYAN);

		//Draw our tracking point with radius = 10 pixels
		g.fillCircle((int)point.getX(), (int)point.getY(), 10);

		g.setAlpha(100);

	}
	
	

}
