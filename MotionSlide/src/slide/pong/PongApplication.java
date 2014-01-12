package slide.pong;

import java.awt.Color;
import java.util.Random;

import slide.SlideApplication;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.pong.Ball;
import br.com.etyllica.pong.Paddle;

public class PongApplication extends SlideApplication{

	private Paddle paddle1;
	
	private Paddle paddle2;
	
	private Ball ball;

	public PongApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
				
		paddle1 = new Paddle(1, 20,20);
		paddle2 = new Paddle(2, w-20-15,20);
		
		ball = new Ball(w/2-30/2, h/2-30/2);

		updateAtFixedRate(5);

		loading = 100;
	}

	@Override
	public void draw(Graphic g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);

		drawLines(g);

		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);

	}

	private void drawLines(Graphic g){

		g.setColor(Color.BLACK);

		//Draw Line
		int lineWidth = 10;
		g.fillRect(w/2-lineWidth/2, 0, lineWidth, h);
				
		//Drawing Score
		g.setFont(g.getFont().deriveFont(22f));
		g.write(w/2-80, 50, Integer.toString(paddle1.getScore()));
		g.write(w/2+80, 50, Integer.toString(paddle2.getScore()));

	}

	private final int PADDLE_KEYBOARD_SPEED = 20;
	private boolean paddleUP = false;
	private boolean paddleDOWN = false;

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		return null;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.getX()<w/2){
			paddle1.setY(event.getY());	
		}else{
			paddle2.setY(event.getY());
		}
		
		return null;
	}

	@Override
	public void timeUpdate(long now){

		if(ball.getX()<0){
			
			paddle2.setScore(paddle2.getScore()+1);
			resetBall();
			
		}else if(ball.getX()>w){
			
			paddle1.setScore(paddle1.getScore()+1);
			resetBall();
			
		}

		if((ball.getY()<0)||(ball.getY()>h)){
			ball.hitVertical();
		}

		if(paddleUP)
			paddle2.setY(paddle2.getY()-PADDLE_KEYBOARD_SPEED);
		else if (paddleDOWN){
			paddle2.setY(paddle2.getY()+PADDLE_KEYBOARD_SPEED);
		}
		
		ball.move();

		if(paddle1.collideBall(ball)){
						
			if(ball.getIncX()<0){
				ball.setIncX(-ball.getIncX());
			}
			
		}
		
		if(paddle2.collideBall(ball)){
						
			if(ball.getIncX()>0){
				ball.setIncX(-ball.getIncX());
			}
			
		}
		
	}
	
	private void resetBall(){
		ball.setCoordinates(w/2-30/2, h/2-30/2);
		ball.setHittedBy(0);
		ball.setMoveAngle(new Random().nextInt(360));
	}

	/*private boolean colideCircleRect(Layer rect, Layer circle){

		double circleDistanceX = Math.abs(circle.getX() - rect.getX());
		double circleDistanceY = Math.abs(circle.getY() - rect.getY());

		if (circleDistanceX > (rect.getW()/2 + circle.getW()/2)) { return false; }
		if (circleDistanceY > (rect.getH()/2 + circle.getW()/2)) { return false; }

		if (circleDistanceX <= (rect.getW()/2)) { return true; } 
		if (circleDistanceY <= (rect.getH()/2)) { return true; }

		double cornerDistance_sq = (circleDistanceX - rect.getW()/2)*(circleDistanceX - rect.getW()/2) +
				(circleDistanceY - rect.getH()/2)*(circleDistanceY - rect.getH()/2);

		return (cornerDistance_sq <= (circle.getW()));
	}*/


}
