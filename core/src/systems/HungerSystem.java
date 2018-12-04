package systems;

public class HungerSystem {

	TimerSystem hungerTimer;
	long interval; 
	float modifier; 
	float starvation;
	
	public HungerSystem() {
		hungerTimer = new TimerSystem("starvation", 2, true);
		this.interval = 10;
		this.modifier = 1;
		this.starvation = 100;
	}
	
	public void update() {
		boolean tmp = this.getHungerTimer().update();
		if (tmp == false) {
			//System.out.println("U need to eat");
			this.starvation -= 2 * modifier;
			//System.out.println(this.starvation);
			
		}
	}

	public TimerSystem getHungerTimer() {
		return hungerTimer;
	}

	public void setHungerTimer(TimerSystem hungerTimer) {
		this.hungerTimer = hungerTimer;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public float getModifier() {
		return modifier;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	public float getStarvation() {
		return starvation;
	}

	public void setStarvation(float starvation) {
		this.starvation = starvation;
	}
	
	
	
	
}
