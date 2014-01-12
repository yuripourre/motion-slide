package slide.capture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.eanimator.rigging.Armature;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.gui.Spinner;
import br.com.etyllica.linear.Point2D;
import br.com.luvia.Application3D;
import br.com.luvia.animation.skeletal.Bone;
import br.com.luvia.linear.Model3D;
import br.com.luvia.loader.MeshLoader;


public class SkelAnimation extends Application3D{

	public SkelAnimation(int w, int h) {
		super(w,h);
	}

	private List<Bone> bones = new ArrayList<Bone>();

	private Model3D model;
	private boolean hideModel = false;

	private double sceneAngleY = 0;
	private double sceneAngleX = 0;

	private Spinner<Integer> initSpinner;
	private Spinner<Integer> finalSpinner;

	private Set<Integer> selectionBucket = new HashSet<Integer>();

	private double zoomFactor = 1.4;
	
	private Point2D center;
	
	private Armature armature;

	private int mx = 0;
	private int my = 0;

	@Override
	public void load() {

		glViewport (0, 0, w, h);
		
		center = new Point2D(w/2, h/2);

		loading = 20;
		enableTextureDefault();

		loading = 41;
		glMatrixMode(GL_PROJECTION);

		loading = 52;
		glLoadIdentity();

		loading = 55;
		gluPerspective(60.0, (double)w/(double)h,1.0, 20.0);

		loading = 67;
		glMatrixMode(GL_MODELVIEW);

		loading = 79;
		glLoadIdentity();

		loading = 88;
		//glTranslatef (0.0f, 0.0f, -5.0f);

		//Load Model (http://thefree3dmodels.com/stuff/characters/male_asian_warrior/14-1-0-4187)
		model = MeshLoader.getInstance().loadModel("oriental/oriental.obj");

		loading = 90;

		System.out.println("Model has "+model.getVertexes().size()+" vertexes.");

		model.setColor(new Color(0x33,0x33,0x33));

		model.setDrawFaces(true);
		model.setDrawTexture(true);
		model.setDrawVertices(false);

		loading = 91;

		//model.setX(0.6);
		model.setY(-1);
		model.setZ(-5);

		loading = 95;

		model.setVertexSelection(selectionBucket);

		loading = 96;
		
		armature = new Armature(model);
	
		assignBones(armature);

		loading = 100;
	}
	
	private void assignBones(Armature armature){
		
		bones.add(armature.getNeck());
		bones.add(armature.getUpperSpine());

		bones.add(armature.getLowerSpine());

		bones.add(armature.getLeftShoulder());
		bones.add(armature.getLeftArm());
		bones.add(armature.getLeftForeArm());
		bones.add(armature.getLeftHand());

		bones.add(armature.getRightShoulder());
		bones.add(armature.getRightArm());
		bones.add(armature.getRightForeArm());
		bones.add(armature.getRightHand());

		bones.add(armature.getLeftHip());
		bones.add(armature.getLeftLeg());
		bones.add(armature.getLeftThigh());
		bones.add(armature.getLeftFoot());

		bones.add(armature.getRightHip());
		bones.add(armature.getRightLeg());
		bones.add(armature.getRightThigh());
		bones.add(armature.getRightFoot());

	}	

	public void updateSelection(){

		List<Integer> intList = new ArrayList<Integer>();

		for(int i = initSpinner.getValue();i<finalSpinner.getValue();i++){
			intList.add(i);
		}

		model.getVertexSelection().clear();
		model.getVertexSelection().addAll(intList);

	}

	private boolean rotate = false;

	float rotationAngle = 0.5f;

	float pivotPrecision = 0.05f;
	float jointPrecision = 0.01f;

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)){
			
		}		

		if(event.isKeyDown(KeyEvent.TSK_ESPACO)){

			rotate = true;
			sceneAngleY += 5;

		}else if(event.isKeyUp(KeyEvent.TSK_ESPACO)){
			rotate = false;
		}
		
		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		if(rotate){
			sceneAngleY+=4;
		}

		//glClear(GL_COLOR_BUFFER_BIT);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		glColor3f(1.0f, 1.0f, 1.0f);

		/*glDisable(GL.GL_DEPTH_TEST);
		glDisable(GL.GL_TEXTURE_2D);
		glDisable(GL.GL_CULL_FACE);*/

		//Scene Translate 
		glTranslated(model.getX(), model.getY(), model.getZ());

		glRotated(sceneAngleX, 1, 0, 0);
		glRotated(sceneAngleY, 0, 1, 0);

		glScaled(zoomFactor, zoomFactor, zoomFactor);
		//armature.getChestJoint().draw(this);

		if(!hideModel){
			model.draw(this);
		}

		glFlush(g);
	}
		
	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		mx = event.getX();
		my = event.getY();
						
		return GUIEvent.NONE;
	}
	
	private double dinAngle = 0;
	
	public void update(long now){
		
		if(mx>center.getX()){
		
			rotateBone(armature.getLeftArm(), -90, true);
			
		}else{
			
			rotateBone(armature.getRightArm(), 90, false);
			
		}
		
	}
		
	private void rotateBone(Bone bone, int offsetAngle, boolean inverted){
		
		double y = my;
		
		if(inverted){
			y = h-my;
		}
		
		double libertyDegree = 180;
				
		double difAngle = offsetAngle+((y)*libertyDegree)/h;
				
		if(dinAngle!=difAngle-bone.getAngleZ()){
			
			dinAngle = difAngle-bone.getAngleZ();

			bone.rotateZOver(dinAngle);
					
		}
		
	}

}