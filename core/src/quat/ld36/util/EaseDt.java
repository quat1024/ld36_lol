package quat.ld36.util;

import quat.ld36.LudumDare36VideoGame;

import static java.lang.Math.pow;

public class EaseDt {
	
	public float x;
	public float lastX;
	public float tightness;
	public float target;
	
	
	public EaseDt(float x_, float tightness_) {
		x = x_;
		tightness = tightness_;
		target = x_;
	}
	
	public void update(float dt) {
		x = target + (x - target) * (float) pow(tightness, LudumDare36VideoGame.FPS * dt);
	}
	
	public float get() {
		return x;
	}
	
	public float updateAndGet(float dt) {
		this.update(dt);
		return this.get();
	}
	
	public void setTarget(float t) {
		target = t;
	}
	
	
}
