package platformer.code.gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import platformer.code.gameengine.PhysicsObject;
import platformer.code.gameengine.graphics.MyGraphics;
import platformer.code.gameengine.hitbox.RectHitbox;
import platformer.code.gamelogic.Main;
import platformer.code.gamelogic.level.Level;
import platformer.code.gamelogic.tiles.Tile;

public class Player extends PhysicsObject{
	private static final float DEFAULT_WALK_SPEED = 400f;
	private static final float WATER_SLOWDOWN_MULTIPLIER = 0.20f;
	private static final float GAS_LIFT_FORCE = 0.35f;

	public float walkSpeed = DEFAULT_WALK_SPEED;
	public float jumpPower = 1350;

	private boolean isJumping = false;
	private boolean inGas = false;

	public Player(float x, float y, Level level) {
	
		super(x, y, level.getLevelData().getTileSize(), level.getLevelData().getTileSize(), level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, width -offset, height - offset);
	}

	public float getBaseSpeed() {
    	return DEFAULT_WALK_SPEED;
	}

	public void setSpeed(float newSpeed) {
    	this.walkSpeed = newSpeed;
	}

	public void updateMovementSpeed(boolean inWater) {
		if (inWater) {
			this.walkSpeed = getBaseSpeed() * WATER_SLOWDOWN_MULTIPLIER;
		} else {
			this.walkSpeed = getBaseSpeed();
		}
	}

	public void setInGas(boolean inGas) {
		this.inGas = inGas;
	}

	@Override
	public void update(float tslf) {
		if (inGas) {
			movementVector.y = -jumpPower * GAS_LIFT_FORCE;
		}

		super.update(tslf);

		movementVector.x = 0;
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed;
		}
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed;
		}
		if(PlayerInput.isJumpKeyDown() && !isJumping) {
			movementVector.y = -jumpPower;
			isJumping = true;
		}
		if (inGas) {
			movementVector.y = -jumpPower * GAS_LIFT_FORCE;
		}

		isJumping = true;
		if(collisionMatrix[BOT] != null) isJumping = false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(Main.DEBUGGING) {
			for (int i = 0; i < closestMatrix.length; i++) {
				Tile t = closestMatrix[i];
				if(t != null) {
					g.setColor(Color.RED);
					g.drawRect((int)t.getX(), (int)t.getY(), t.getSize(), t.getSize());
				}
			}
		}
		
		hitbox.draw(g);
	}
}
