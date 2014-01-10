package slide.capture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgl.GL;
import org.lwjgl.util.vector.Vector3f;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.gui.Spinner;
import br.com.etyllica.linear.Point2D;
import br.com.luvia.Application3D;
import br.com.luvia.animation.skeletal.Bone;
import br.com.luvia.animation.skeletal.Joint;
import br.com.luvia.linear.Model3D;
import br.com.luvia.loader.MeshLoader;


public class SkelAnimation extends Application3D{

	public SkelAnimation(int w, int h) {
		super(w,h);
	}

	//Chest is the Pivot Joint
	private Joint chestJoint;

	private Bone neck;
	private Bone upperSpine;
	private Bone lowerSpine;

	//Left Arm
	private Bone leftShoulder;
	private Bone leftArm;
	private Bone leftForeArm;
	private Bone leftHand;

	//Right Arm
	private Bone rightShoulder;
	private Bone rightArm;
	private Bone rightForeArm;
	private Bone rightHand;

	//Left Leg
	private Bone leftHip;
	private Bone leftLeg;
	private Bone leftThigh;
	private Bone leftFoot;

	//Right Leg
	private Bone rightHip;
	private Bone rightLeg;
	private Bone rightThigh;
	private Bone rightFoot;

	private List<Bone> bones = new ArrayList<Bone>();

	private Model3D model;
	private boolean hideModel = false;

	private double sceneAngleY = 0;
	private double sceneAngleX = 0;

	private Bone activeBone;

	private Spinner<Integer> initSpinner;
	private Spinner<Integer> finalSpinner;

	private Set<Integer> selectionBucket = new HashSet<Integer>();

	//private double zoomFactor = 2;
	private double zoomFactor = 1.4;
	//private double zoomFactor = 0.2;

	private int specialVertex = 350;//0;

	private boolean xray = true;
	
	private Point2D center;

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

		loading = 91;

		//model.setX(0.6);
		model.setY(-1);
		model.setZ(-5);

		loading = 95;

		model.setDrawFaces(true);
		model.setDrawTexture(false);

		model.setVertexSelection(selectionBucket);

		loading = 96;

		//Dummy Pivot
		//chestJoint = new Joint(0.025,1.35,model.getZ()-0.1);
		//chestJoint = new Joint(0,1.35,0);
		chestJoint = new Joint(0.035,1.35,-0.15);

		//Neck Bone
		upperSpine = new Bone(0.23);
		chestJoint.addBone(upperSpine);
		upperSpine.rotateZOver(90);
		upperSpine.rotateXOver(355);

		//Head Bone
		neck = new Bone(0.15);
		upperSpine.getEnd().addBone(neck);
		neck.rotateZOver(90);
		neck.rotateXOver(12);

		//LeftShoulder bone
		leftShoulder = new Bone(0.22);
		chestJoint.addBone(leftShoulder);
		leftShoulder.rotateZOver(30);

		//LeftArm Bone
		leftArm = new Bone(0.300);
		leftShoulder.getEnd().addBone(leftArm);
		leftArm.rotateZOver(323);

		//LeftForeArm
		leftForeArm = new Bone(0.25);
		leftArm.getEnd().addBone(leftForeArm);
		leftForeArm.rotateZOver(340);
		leftForeArm.rotateYOver(334);

		//LeftHandBone
		leftHand = new Bone(0.2);
		leftForeArm.getEnd().addBone(leftHand);
		leftHand.rotateZOver(351);
		leftHand.rotateYOver(342);


		rightShoulder = new Bone(0.22);
		chestJoint.addBone(rightShoulder);
		rightShoulder.rotateZOver(150);

		rightArm = new Bone(0.300);
		rightShoulder.getEnd().addBone(rightArm);
		rightArm.rotateZOver(217);

		rightForeArm = new Bone(0.25);
		rightArm.getEnd().addBone(rightForeArm);

		rightForeArm.rotateZOver(200);
		rightForeArm.rotateYOver(30);

		rightHand = new Bone(0.2);
		rightForeArm.getEnd().addBone(rightHand);
		rightHand.rotateZOver(186);
		rightHand.rotateYOver(20);


		//Lower Spine Bone - Connects chest to hip
		lowerSpine = new Bone(0.39);
		chestJoint.addBone(lowerSpine);
		lowerSpine.rotateZOver(270);
		lowerSpine.rotateYOver(10);

		//Left Leg
		leftHip = new Bone(0.12);
		lowerSpine.getEnd().addBone(leftHip);
		leftHip.rotateZOver(340);

		leftLeg = new Bone(0.48);
		leftHip.getEnd().addBone(leftLeg);
		leftLeg.rotateZOver(275);
		leftLeg.rotateYOver(370);
		leftLeg.rotateXOver(359);

		leftThigh = new Bone(0.4);
		leftLeg.getEnd().addBone(leftThigh);
		leftThigh.rotateZOver(270);
		leftThigh.rotateXOver(5);

		leftFoot = new Bone(0.25);
		leftThigh.getEnd().addBone(leftFoot);
		leftFoot.rotateZOver(355);
		leftFoot.rotateYOver(290);

		//Right Leg
		rightHip = new Bone(0.12);
		lowerSpine.getEnd().addBone(rightHip);
		rightHip.rotateZOver(200);

		rightLeg = new Bone(0.48);
		rightHip.getEnd().addBone(rightLeg);
		rightLeg.rotateZOver(265);
		rightLeg.rotateYOver(370);
		rightLeg.rotateXOver(359);

		rightThigh = new Bone(0.4);
		rightLeg.getEnd().addBone(rightThigh);
		rightThigh.rotateZOver(270);
		rightThigh.rotateXOver(5);

		rightFoot = new Bone(0.25);
		rightThigh.getEnd().addBone(rightFoot);
		rightFoot.rotateZOver(355);
		rightFoot.rotateYOver(250);

		assignBones();		
		//Define activeBone
		activateBone(leftArm);

		assignVectors();

		loading = 100;
	}

	private void assignBones(){
		bones.add(neck);
		bones.add(upperSpine);

		bones.add(lowerSpine);

		bones.add(leftShoulder);
		bones.add(leftArm);
		bones.add(leftForeArm);
		bones.add(leftHand);

		bones.add(rightShoulder);
		bones.add(rightArm);
		bones.add(rightForeArm);
		bones.add(rightHand);

		bones.add(leftHip);
		bones.add(leftLeg);
		bones.add(leftThigh);
		bones.add(leftFoot);

		bones.add(rightHip);
		bones.add(rightLeg);
		bones.add(rightThigh);
		bones.add(rightFoot);

	}

	private void assignVectors(){

		//Right
		Integer[] headSelection = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 16, 19, 18, 21, 20, 23, 22, 25, 24, 27, 26, 29, 28, 31, 30, 34, 35, 32, 33, 38, 39, 36, 37, 42, 43, 40, 41, 46, 47, 44, 45, 51, 50, 49, 48, 55, 54, 53, 52, 59, 58, 57, 56, 63, 62, 61, 60, 68, 69, 70, 71, 64, 65, 66, 67, 76, 77, 78, 79, 72, 73, 74, 75, 85, 84, 87, 86, 81, 80, 83, 82, 93, 92, 95, 94, 89, 88, 91, 90, 102, 103, 100, 101, 98, 99, 96, 97, 110, 111, 108, 109, 106, 107, 104, 105, 186, 187, 190, 191, 188, 189, 193, 192};
		neck.addVectors(arrayToList(headSelection));

		//Left Arm
		Integer[] leftArmSelection = {239, 238, 234, 233, 232, 231, 230, 229, 228, 227, 221, 222, 244, 240, 210};
		leftArm.addVectors(arrayToList(leftArmSelection));

		Integer[] leftForeArmSelection = {237, 255, 250, 263, 251, 248, 249, 246, 247, 264, 245, 242, 243, 241};
		leftForeArm.addVectors(arrayToList(leftForeArmSelection));

		Integer[] leftHandSelection = {137, 136, 139, 138, 141, 140, 143, 142, 129, 128, 131, 130, 133, 132, 135, 134, 144, 145, 146, 147, 148, 119, 118, 117, 116, 115, 114, 113, 112, 127, 126, 125, 124, 123, 122, 121, 120};
		leftHand.addVectors(arrayToList(leftHandSelection));

		//Right Arm
		Integer[] rightArmSelection = {278, 277, 283, 282, 287, 286, 285, 284, 288, 289, 292, 293, 294, 295, 299, 269};
		rightArm.addVectors(arrayToList(rightArmSelection));

		Integer[]rightForeArmSelection = {305, 304, 306, 308, 315, 314, 296, 297, 298, 300, 301, 302, 303};
		rightForeArm.addVectors(arrayToList(rightForeArmSelection));

		Integer[] rightHandSelection = {152, 153, 154, 155, 156, 157, 158, 159, 149, 150, 151, 171, 170, 169, 168, 175, 174, 173, 172, 163, 162, 161, 160, 167, 166, 165, 164, 184, 185, 178, 179, 176, 177, 182, 183, 180, 181};
		rightHand.addVectors(arrayToList(rightHandSelection));

		Integer[] upperSpineSelection = {275, 272, 236, 276, 281, 226, 225, 220, 291, 217, 218, 219, 212, 208, 270, 209, 271, 211};
		upperSpine.addVectors(arrayToList(upperSpineSelection));
		
		Integer[] lowerSpineSelection = {274, 273, 279, 280, 258, 259, 256, 223, 257, 216, 260, 261, 213, 267, 214, 215, 268, 307, 309, 235, 311, 313, 312, 317, 224, 254, 290, 252, 253};
		Integer[] clothesSelection = {205, 410, 411, 408, 207, 206, 409, 201, 414, 200, 415, 412, 202, 413, 197, 199, 198, 407, 399, 359, 352, 353, 364, 417, 416, 419, 418, 421, 361, 420, 423, 422};
		Integer[] beltSelection = {204, 262, 398, 351, 360, 195, 362, 405, 310, 266, 196, 265, 316, 194, 203};
		
		lowerSpine.addVectors(arrayToList(lowerSpineSelection, clothesSelection, beltSelection));

		//Left Leg
		Integer[] leftLegSelection = {338, 350, 349, 356, 357, 358, 354, 355, 335, 363};
		leftLeg.addVectors(arrayToList(leftLegSelection));

		Integer[] leftThighSelection = {343, 337, 336, 326, 327, 324, 325, 322, 323, 334, 332, 333, 330, 331, 328, 329};
		leftThigh.addVectors(arrayToList(leftThighSelection));

		Integer[] leftFootSelection = {342, 341, 340, 339, 320, 321, 348, 347, 346, 345, 319, 344, 318};
		leftFoot.addVectors(arrayToList(leftFootSelection));

		//Right Leg
		Integer[] rightLegSelection = {383, 386, 396, 397, 402, 403, 400, 401, 406, 404};
		rightLeg.addVectors(arrayToList(rightLegSelection));

		Integer[] rightThighSelection = {373, 372, 375, 374, 369, 371, 370, 381, 380, 382, 376, 377, 379, 378, 385, 384};
		rightThigh.addVectors(arrayToList(rightThighSelection));

		Integer[] rightFootSelection = {395, 394, 393, 392, 368, 387, 365, 366, 367, 391, 390, 389, 388};
		rightFoot.addVectors(arrayToList(rightFootSelection));		

	}

	private List<Vector3f> arrayToList(Integer[] ...selections){

		List<Vector3f> vectors = new ArrayList<Vector3f>();
		
		for(Integer[] selection : selections){

			for(int i=0;i<selection.length;i++){
				vectors.add(model.getVertexes().get(selection[i]));
			}
			
		}

		return vectors;
	}

	public void updateSelection(){

		List<Integer> intList = new ArrayList<Integer>();

		for(int i = initSpinner.getValue();i<finalSpinner.getValue();i++){
			intList.add(i);
		}

		model.getVertexSelection().clear();
		model.getVertexSelection().addAll(intList);

	}

	private void activateBone(Bone bone){
		activeBone = bone;
		resetColor();
		bone.setColor(Color.BLUE);
	}

	private void resetColor(){

		for(Bone bone: bones){
			bone.setColor(new Color(0x66,0x44,0x44));
		}

	}

	private boolean ctrl = false;
	private boolean rotate = false;

	float rotationAngle = 0.5f;

	float pivotPrecision = 0.05f;
	float jointPrecision = 0.01f;

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_J)){
			model.setX(model.getX()-.1);
			System.out.println("mX = "+model.getX());
		}
		else if(event.isKeyDown(KeyEvent.TSK_L)){
			model.setX(model.getX()+.1);
			System.out.println("mX = "+model.getX());
		}
		if(event.isKeyDown(KeyEvent.TSK_K)){
			model.setY(model.getY()-.1);
			System.out.println("mY = "+model.getY());
		}
		else if(event.isKeyDown(KeyEvent.TSK_I)){
			model.setY(model.getY()+.1);
			System.out.println("mY = "+model.getY());
		}

		if(event.isKeyDown(KeyEvent.TSK_H)){
			if(hideModel==false){
				hideModel = true;
			}else{
				hideModel = false;
			}
		}else if(event.isKeyDown(KeyEvent.TSK_T)){

			if(model.isDrawTexture()==false){
				model.setDrawTexture(true);
			}else{
				model.setDrawTexture(false);
			}

		}else if(event.isKeyDown(KeyEvent.TSK_F)){

			if(model.isDrawFaces()==false){
				model.setDrawFaces(true);
			}else{
				model.setDrawFaces(false);
			}

		}else if(event.isKeyDown(KeyEvent.TSK_X)){
			if(!xray){
				xray = true;
			}else{
				xray = false;
			}
		}

		if(event.isKeyDown(KeyEvent.TSK_O)){
			chestJoint.setZ(chestJoint.getZ()+jointPrecision);
			System.out.println("chestJoint.Z = "+chestJoint.getZ());
		}
		else if(event.isKeyDown(KeyEvent.TSK_P)){
			chestJoint.setZ(chestJoint.getZ()-jointPrecision);
			System.out.println("chestJoint.Z = "+chestJoint.getZ());
		}

		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){

			if(!ctrl){
				activeBone.rotateYOver(10);
			}else{
				activeBone.rotateYUnsigned(+rotationAngle);
			}
			System.out.println("Bone.y = "+activeBone.getAngleY());
		}
		else if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){

			if(!ctrl){
				activeBone.rotateYOver(-10);
			}else{
				activeBone.rotateYUnsigned(-rotationAngle);
			}

			System.out.println("Bone.y = "+activeBone.getAngleY());
		}
		if(event.isKeyDown(KeyEvent.TSK_SETA_CIMA)){

			if(!ctrl){
				activeBone.rotateZOver(10);
			}else{
				activeBone.rotateZUnsigned(+rotationAngle);
			}

			System.out.println("Bone.z = "+activeBone.getAngleZ());
		}
		else if(event.isKeyDown(KeyEvent.TSK_SETA_BAIXO)){

			if(!ctrl){
				activeBone.rotateZOver(-10);
			}else{
				activeBone.rotateZUnsigned(-rotationAngle);
			}

			System.out.println("Bone.z = "+activeBone.getAngleZ());
		}
		if(event.isKeyDown(KeyEvent.TSK_ABRE_COLCHETES)){
			if(!ctrl){
				activeBone.rotateXOver(10);
			}else{
				activeBone.rotateXUnsigned(+rotationAngle);	
			}
			System.out.println("Bone.x = "+activeBone.getAngleX());

		}
		else if(event.isKeyDown(KeyEvent.TSK_FECHA_COLCHETES)){
			if(!ctrl){
				activeBone.rotateXOver(-10);
			}else{
				activeBone.rotateXUnsigned(-rotationAngle);	
			}
			System.out.println("Bone.x = "+activeBone.getAngleX());
		}

		if(event.isKeyDown(KeyEvent.TSK_W)){
			chestJoint.translate(0, +pivotPrecision, 0);
			//activeBone.translate(0, +0.1, 0);
			System.out.println("PivotY = "+chestJoint.getY());
		}else if(event.isKeyDown(KeyEvent.TSK_S)){
			chestJoint.translate(0, -pivotPrecision, 0);
			//activeBone.translate(0, -0.1, 0);
			System.out.println("PivotY = "+chestJoint.getY());
		}else if(event.isKeyDown(KeyEvent.TSK_A)){
			chestJoint.translate(-pivotPrecision, 0, 0);
			//activeBone.translate(-0.1, 0, 0);
			System.out.println("PivotX = "+chestJoint.getX());
		}else if(event.isKeyDown(KeyEvent.TSK_D)){
			chestJoint.translate(+pivotPrecision, 0, 0);
			//activeBone.translate(+0.1, 0, 0);
			System.out.println("PivotX = "+chestJoint.getX());
		}else if(event.isKeyDown(KeyEvent.TSK_C)){
			chestJoint.translate(0, 0, -pivotPrecision);
			//activeBone.translate(+0.1, 0, 0);
			System.out.println("PivotZ = "+chestJoint.getZ());
		}else if(event.isKeyDown(KeyEvent.TSK_V)){
			chestJoint.translate(0, 0, +pivotPrecision);
			//activeBone.translate(+0.1, 0, 0);
			System.out.println("PivotZ = "+chestJoint.getZ());
		}



		if(event.isKeyDown(KeyEvent.TSK_0)){
			activateBone(neck);
		}
		if(event.isKeyDown(KeyEvent.TSK_9)){
			activateBone(upperSpine);
		}

		if(event.isKeyDown(KeyEvent.TSK_1)){
			activateBone(leftArm);
		}else if(event.isKeyDown(KeyEvent.TSK_2)){
			activateBone(leftForeArm);
		}else if(event.isKeyDown(KeyEvent.TSK_3)){
			activateBone(leftHand);
		}

		else if(event.isKeyDown(KeyEvent.TSK_4)){	
			activateBone(rightArm);
		}else if(event.isKeyDown(KeyEvent.TSK_5)){
			activateBone(rightForeArm);
		}else if(event.isKeyDown(KeyEvent.TSK_6)){
			activateBone(rightHand);
		}

		else if(event.isKeyDown(KeyEvent.TSK_7)){
			activateBone(leftLeg);
		}
		else if(event.isKeyDown(KeyEvent.TSK_8)){
			activateBone(leftThigh);
		}
		else if(event.isKeyDown(KeyEvent.TSK_9)){
			activateBone(leftFoot);
		}

		else if(event.isKeyDown(KeyEvent.TSK_Z)){
			activateBone(lowerSpine);
		}else if(event.isKeyDown(KeyEvent.TSK_X)){
			activateBone(leftThigh);
		}else if(event.isKeyDown(KeyEvent.TSK_C)){
			activateBone(rightThigh);
		}

		if(event.isKeyDown(KeyEvent.TSK_ESPACO)){

			rotate = true;
			sceneAngleY += 5;

		}else if(event.isKeyDown(KeyEvent.TSK_SHIFT_ESQUERDA)){

			sceneAngleY -= 5;

		}else if(event.isKeyDown(KeyEvent.TSK_SHIFT_DIREITA)){

			sceneAngleY += 5;

		}

		if(event.isKeyDown(KeyEvent.TSK_CTRL_DIREITA)||event.isKeyDown(KeyEvent.TSK_CTRL_ESQUERDA)){
			ctrl = true;
		}
		else if(event.isKeyUp(KeyEvent.TSK_CTRL_DIREITA)||event.isKeyDown(KeyEvent.TSK_CTRL_ESQUERDA)){
			ctrl = false;
		}


		if(event.isKeyUp(KeyEvent.TSK_ESPACO)){
			rotate = false;
		}

		if(event.isKeyDown(KeyEvent.TSK_M)){

			sceneAngleX += 5;

		}else if(event.isKeyDown(KeyEvent.TSK_N)){

			sceneAngleX -= 5;

		}
		else if(event.isKeyDown(KeyEvent.TSK_B)){

			sceneAngleX = 90;

		}

		if(event.isKeyDown(KeyEvent.TSK_ENTER)){

			System.out.println("Vertice "+specialVertex+" inserido no bucket");
			selectionBucket.add(specialVertex);
			System.out.println(selectionBucket);

		}else if(event.isKeyDown(KeyEvent.TSK_R)){

			System.out.println("Vertice "+specialVertex+" removido do bucket");
			selectionBucket.remove(specialVertex);

			System.out.println(selectionBucket);

		}else if(event.isKeyDown(KeyEvent.TSK_ESC)){

			System.out.println("Bucket esvaziado");
			selectionBucket.clear();
			System.out.println(selectionBucket);

		}

		//Walking with special vertex
		if(event.isKeyDown(KeyEvent.TSK_PONTO)){

			if(specialVertex<model.getVertexes().size()){
				specialVertex++;
			}

			model.specialVertex = this.specialVertex;

			System.out.println("Special Vertex = "+this.specialVertex);
		}
		if(event.isKeyDown(KeyEvent.TSK_VIRGULA)){
			specialVertex--;
			model.specialVertex = this.specialVertex;

			System.out.println("Special Vertex = "+this.specialVertex);
		}

		//TODO Test
		//updateSelection();

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

		//glPushMatrix();

		//Change to Default Zoom
		//glScaled(zoomFactor, zoomFactor, zoomFactor);

		//if(model.isDrawTexture()){

		glDisable(GL.GL_DEPTH_TEST);
		glDisable(GL.GL_TEXTURE_2D);
		glDisable(GL.GL_CULL_FACE);
		//}

		//Scene Translate 
		glTranslated(model.getX(), model.getY(), model.getZ());

		glRotated(sceneAngleX, 1, 0, 0);
		glRotated(sceneAngleY, 0, 1, 0);


		glScaled(zoomFactor, zoomFactor, zoomFactor);
		chestJoint.draw(this);

		if(!hideModel){
			model.draw(this);
		}

		if(xray){
			chestJoint.draw(this);	
		}

		//glPopMatrix();

		glFlush(g);
	}

	private int mx = 0;
	private int my = 0;
		
	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		mx = event.getX();
		my = event.getY();
						
		return GUIEvent.NONE;
	}
	
	private double dinAngle = 0;
	
	public void update(long now){
		
		if(mx>center.getX()){
		
			rotateBone(leftArm, -90, true);
			
		}else{
			
			rotateBone(rightArm, 90, false);
			
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