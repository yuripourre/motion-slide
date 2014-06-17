package slide;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.abby.Application3D;
import br.com.etyllica.animation.scripts.FadeInAnimation;
import br.com.etyllica.animation.scripts.SingleIntervalAnimation;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.graphics.SVGColor;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.layer.GeometricLayer;
import br.com.etyllica.layer.TextLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.TrackingByColorFilter;
import br.com.etyllica.motion.filter.TrackingByMultipleColorFilter;
import br.com.etyllica.motion.filter.color.LeftColorFilter;
import br.com.etyllica.motion.filter.color.RightColorFilter;
import br.com.etyllica.motion.filter.validation.MinDensityValidation;

public abstract class SlideApplication extends Application3D{

	public SlideApplication(int w, int h) {
		super(w, h);
	}

	protected List<TextLayer> textList = new ArrayList<TextLayer>();

	private int texts = 0;

	private Camera cam;

	private BufferedLayer bufferedLayer;

	protected BufferedImage mirror = null;

	private int tolerance = 18;

	private TrackingByColorFilter filter;

	private List<Component> components = new ArrayList<Component>();

	protected Component screen = new Component(0, 0, w, h);

	private LeftColorFilter leftColorFilter;

	private RightColorFilter rightColorFilter;

	protected Component leftPoint = new Component(0, 100, 1, 1);

	protected Component rightPoint = new Component(500, 100, 1, 1);

	private final GeometricLayer colorLayer = new GeometricLayer(0, 100, 40, 30);

	@Override
	public void load() {

		glViewport (0, 0, w, h);

		loading = 1;

		loadingPhrase = "Opening Camera";

		initCamera();

		int width = screen.getW();
		int height = screen.getH();

		bufferedLayer = new BufferedLayer(width, height);

		Color filterColor = new Color(72,135,166);

		filter = new TrackingByColorFilter(width, height, filterColor, tolerance);
		filter.addComponentStrategy(new MinDensityValidation(10));

		loadingPhrase = "Setting Filter";
		loading = 2;

		final int border = 10;

		final int tolerance = 30;

		final Color color = new Color(40, 122, 158);

		loading = 3;

		leftColorFilter = new LeftColorFilter(w, h, color);

		leftColorFilter.setColor(color);
		leftColorFilter.setTolerance(tolerance);		
		leftColorFilter.getSearchStrategy().setBorder(border);

		rightColorFilter = new RightColorFilter(w, h, color);

		rightColorFilter.setColor(color);
		rightColorFilter.setTolerance(tolerance);
		rightColorFilter.getSearchStrategy().setBorder(border);

		updateAtFixedRate(5);

		loading = 5;
	}

	private void initCamera() {

		cam = new CameraV4L4J(0);

	}

	@Override
	public void timeUpdate(long now) {		

		if(cam == null) 
			return;

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

		components = filter.filter(mirror, screen);

		calculateCentroid(components);

	}

	private void calculateCentroid(List<Component> components) {
		
		if(components.size() == 0) {
			return;
		}
		
		int centroidX = 0;

		int centroidY = 0;

		for(Component component: components) {
			centroidX += component.getCenter().getX();
			centroidY += component.getCenter().getY();
		}

		centroidX /= components.size();
		centroidY /= components.size();

		rightPoint.setLocation(centroidX, centroidY);
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_EQUALS)) {

			tolerance += 1;
			filter.setTolerance(tolerance);

		}

		if(event.isKeyDown(KeyEvent.TSK_MINUS)) {

			tolerance -= 1;
			filter.setTolerance(tolerance);

		}

		return GUIEvent.NONE;

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		//When mouse clicks, the color filter tries to find
		//the color we are clicking on

		if(mirror != null) {

			if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {

				Color color = new Color(mirror.getRGB((int)event.getX(), (int)event.getY()));

				System.out.println("Red: "+color.getRed());
				System.out.println("Green: "+color.getGreen());
				System.out.println("Blue: "+color.getBlue());

				leftColorFilter.setColor(new Color(mirror.getRGB((int)event.getX(), (int)event.getY())));

			}else if(event.isButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)){

				rightColorFilter.setColor(new Color(mirror.getRGB((int)event.getX(), (int)event.getY())));

			}

			if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {

				filter.setColor(new Color(mirror.getRGB((int)event.getX(), (int)event.getY())));
			}

		}

		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {

			System.out.println("mx: "+event.getX());

			System.out.println("my: "+event.getY());

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

		g.setColor(SVGColor.CORAL);

		for(Component component: new CopyOnWriteArrayList<Component>(components)) {
			g.drawRect(component.getRectangle());						
		}

	}

	protected void drawCamera(Graphic g) {

		g.setAlpha(50);

		//Draw the mirror image
		if(mirror != null){
			g.drawImage(mirror, 0, 0);
		}		

		//Set a Color to our Point
		g.setColor(Color.CYAN);
		g.fillCircle((int)leftPoint.getX(), (int)leftPoint.getY(), 10);

		g.setColor(Color.RED);
		g.fillCircle((int)rightPoint.getX(), (int)rightPoint.getY(), 10);

		g.setAlpha(100);

		g.setColor(filter.getColor());
		g.fillRect(colorLayer);

		g.setColor(Color.WHITE);
		g.drawShadow(10, 60, "Right Point ("+rightPoint.getX()+", "+rightPoint.getY()+")");
		g.drawShadow(10, 80, "Left Point ("+leftPoint.getX()+", "+leftPoint.getY()+")");

		g.drawStringShadow(colorLayer.getX(), colorLayer.getY(), colorLayer.getW(), colorLayer.getH(), Integer.toString(tolerance));

	}



}
