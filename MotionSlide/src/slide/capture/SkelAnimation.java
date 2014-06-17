package slide.capture;

import java.awt.Color;

import org.jgl.GL;

import slide.SlideApplication;
import br.com.abby.animation.skeletal.Bone;
import br.com.abby.linear.Model3D;
import br.com.abby.linear.Point3D;
import br.com.abby.loader.MeshLoader;
import br.com.etyllica.animator.control.RigIKControl;
import br.com.etyllica.animator.rigging.Armature;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;


public class SkelAnimation extends SlideApplication {

	public SkelAnimation(int w, int h) {
		super(w,h);
	}
	
	private Model3D model;
	
	private boolean hideModel = false;

	private double sceneAngleY = 0;
	
	private boolean xray = true;

	private Armature armature;

	private RigIKControl leftArmControl;

	private RigIKControl rightArmControl;
	
	private boolean rotate = false;

	@Override
	public void load() {
		super.load();		

		loading = 20;
		enableTextureDefault();

		loading = 41;
		glMatrixMode(GL_PROJECTION);

		loading = 52;
		glLoadIdentity();

		loading = 55;
		gluPerspective(60.0, (double)w/(double)h, 0.01, 20.0);

		loading = 67;
		glMatrixMode(GL_MODELVIEW);

		loading = 79;
		glLoadIdentity();

		loading = 88;
		//glTranslatef (0.0f, 0.0f, -5.0f);

		//Load Model (http://thefree3dmodels.com/stuff/characters/male_asian_warrior/14-1-0-4187)
		model = MeshLoader.getInstance().loadModel("oriental/oriental.obj");
		//model.setScale(3);
		
		loading = 90;

		System.out.println("Model has "+model.getVertexes().size()+" vertexes.");

		model.setColor(new Color(0x33,0x33,0x33));

		loading = 91;

		model.setX(-0.4);
		model.setY(-1.6);
		model.setZ(-0.6);

		loading = 95;

		model.setDrawFaces(true);
		model.setDrawTexture(false);

		loading = 96;

		armature = new Armature(model);

		generateRigControls();
			
		updateAtFixedRate(200);
				
		loading = 100;
	}
	
	private void generateRigControls() {

		//final Vec3D kneeDirection = new Vec3D(1,0,0);
		
		//Left Arm Control
		leftArmControl = new RigIKControl(510, 150, Color.GREEN);

		leftArmControl.setAnchor(new Point3D(373,355, 0));

		leftArmControl.setBoneA(armature.getLeftArm(), 185);

		leftArmControl.setBoneB(armature.getLeftForeArm(), 160);

		//Right Arm Control
		rightArmControl = new RigIKControl(210, 160, Color.BLUE);

		rightArmControl.setAnchor(new Point3D(128,355));

		rightArmControl.setBoneA(armature.getRightArm(), 190);

		rightArmControl.setBoneB(armature.getRightForeArm(), 180);

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_J)) {
			model.setX(model.getX()-.1);
			System.out.println("mX = "+model.getX());
		}
		else if(event.isKeyDown(KeyEvent.TSK_L)) {
			model.setX(model.getX()+.1);
			System.out.println("mX = "+model.getX());
		}
		if(event.isKeyDown(KeyEvent.TSK_K)) {
			model.setY(model.getY()-.1);
			System.out.println("mY = "+model.getY());
		}
		else if(event.isKeyDown(KeyEvent.TSK_I)) {
			model.setY(model.getY()+.1);
			System.out.println("mY = "+model.getY());
		}
		
		if(event.isKeyDown(KeyEvent.TSK_U)) {
			model.setZ(model.getZ()-.1);
			System.out.println("mZ = "+model.getZ());
		}
		else if(event.isKeyDown(KeyEvent.TSK_O)) {
			model.setZ(model.getZ()+.1);
			System.out.println("mZ = "+model.getZ());
		}

		if(event.isKeyDown(KeyEvent.TSK_H)) {

			if(hideModel==false) {
				hideModel = true;
			}else{
				hideModel = false;
			}

		}else if(event.isKeyDown(KeyEvent.TSK_T)) {

			if(model.isDrawTexture()==false) {
				model.setDrawTexture(true);
			}else{
				model.setDrawTexture(false);
			}

		}else if(event.isKeyDown(KeyEvent.TSK_F)) {

			if(model.isDrawFaces()==false) {
				model.setDrawFaces(true);
			}else{
				model.setDrawFaces(false);
			}

		}else if(event.isKeyDown(KeyEvent.TSK_X)) {
			if(!xray) {
				xray = true;
			}else{
				xray = false;
			}
		}

		if(event.isKeyDown(KeyEvent.TSK_ESPACO)) {
			rotate = true;
		}

		if(event.isKeyUp(KeyEvent.TSK_ESPACO)) {
			rotate = false;
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {
		super.draw(g);
		
		if(rotate) {
			sceneAngleY+=4;
		}

		//glClear(GL_COLOR_BUFFER_BIT);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		glColor3f(1.0f, 1.0f, 1.0f);

		glDisable(GL.GL_DEPTH_TEST);
		glDisable(GL.GL_TEXTURE_2D);
		glDisable(GL.GL_CULL_FACE);


		//Scene Translate 
		glTranslated(model.getX(), model.getY(), model.getZ());

		glRotated(sceneAngleY, 0, 1, 0);

		armature.getChestJoint().draw(this);

		if(!hideModel) {
			model.draw(this);
			//model.drawVertexes(this);
		}

		if(xray) {
			armature.getChestJoint().draw(this);	
		}

		//glPopMatrix();

		//Draw IK

		glFlush(g);
		
		leftArmControl.draw(g);

		rightArmControl.draw(g);
				
		drawCamera(g);
	}
		
	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		super.updateMouse(event);
		
		//leftArmControl.handleMouse(event);		

		//rightArmControl.handleMouse(event);
						
		return GUIEvent.NONE;
	}
	
	@Override
	public void timeUpdate(long now) {
		super.update(now);
		super.timeUpdate(now);
		
		leftArmControl.setCoordinates(leftPoint.getX(), leftPoint.getY());
		leftArmControl.calculate();
		
		rightArmControl.setCoordinates(rightPoint.getX(), rightPoint.getY());
		rightArmControl.calculate();
		
	}
	
}
