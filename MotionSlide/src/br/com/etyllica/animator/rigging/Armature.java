package br.com.etyllica.animator.rigging;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import br.com.luvia.animation.skeletal.Bone;
import br.com.luvia.animation.skeletal.Joint;
import br.com.luvia.linear.Model3D;

public class Armature {

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

	private Model3D model;
	
	public Armature(Model3D model){
		super();
		
		this.model = model;

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
		
		assignVectors();

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

	public Joint getChestJoint() {
		return chestJoint;
	}

	public void setChestJoint(Joint chestJoint) {
		this.chestJoint = chestJoint;
	}

	public Bone getNeck() {
		return neck;
	}

	public void setNeck(Bone neck) {
		this.neck = neck;
	}

	public Bone getUpperSpine() {
		return upperSpine;
	}

	public void setUpperSpine(Bone upperSpine) {
		this.upperSpine = upperSpine;
	}

	public Bone getLowerSpine() {
		return lowerSpine;
	}

	public void setLowerSpine(Bone lowerSpine) {
		this.lowerSpine = lowerSpine;
	}

	public Bone getLeftShoulder() {
		return leftShoulder;
	}

	public void setLeftShoulder(Bone leftShoulder) {
		this.leftShoulder = leftShoulder;
	}

	public Bone getLeftArm() {
		return leftArm;
	}

	public void setLeftArm(Bone leftArm) {
		this.leftArm = leftArm;
	}

	public Bone getLeftForeArm() {
		return leftForeArm;
	}

	public void setLeftForeArm(Bone leftForeArm) {
		this.leftForeArm = leftForeArm;
	}

	public Bone getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(Bone leftHand) {
		this.leftHand = leftHand;
	}

	public Bone getRightShoulder() {
		return rightShoulder;
	}

	public void setRightShoulder(Bone rightShoulder) {
		this.rightShoulder = rightShoulder;
	}

	public Bone getRightArm() {
		return rightArm;
	}

	public void setRightArm(Bone rightArm) {
		this.rightArm = rightArm;
	}

	public Bone getRightForeArm() {
		return rightForeArm;
	}

	public void setRightForeArm(Bone rightForeArm) {
		this.rightForeArm = rightForeArm;
	}

	public Bone getRightHand() {
		return rightHand;
	}

	public void setRightHand(Bone rightHand) {
		this.rightHand = rightHand;
	}

	public Bone getLeftHip() {
		return leftHip;
	}

	public void setLeftHip(Bone leftHip) {
		this.leftHip = leftHip;
	}

	public Bone getLeftLeg() {
		return leftLeg;
	}

	public void setLeftLeg(Bone leftLeg) {
		this.leftLeg = leftLeg;
	}

	public Bone getLeftThigh() {
		return leftThigh;
	}

	public void setLeftThigh(Bone leftThigh) {
		this.leftThigh = leftThigh;
	}

	public Bone getLeftFoot() {
		return leftFoot;
	}

	public void setLeftFoot(Bone leftFoot) {
		this.leftFoot = leftFoot;
	}

	public Bone getRightHip() {
		return rightHip;
	}

	public void setRightHip(Bone rightHip) {
		this.rightHip = rightHip;
	}

	public Bone getRightLeg() {
		return rightLeg;
	}

	public void setRightLeg(Bone rightLeg) {
		this.rightLeg = rightLeg;
	}

	public Bone getRightThigh() {
		return rightThigh;
	}

	public void setRightThigh(Bone rightThigh) {
		this.rightThigh = rightThigh;
	}

	public Bone getRightFoot() {
		return rightFoot;
	}

	public void setRightFoot(Bone rightFoot) {
		this.rightFoot = rightFoot;
	}
	
}
