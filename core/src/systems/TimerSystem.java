package systems;

public class TimerSystem {
	
	private long startTime;
	private long target;
	private String text;
	boolean running = true;
	float hour = 0; 
	float min = 0; 
	float sec = 0;
	float multiplier = 4f;
	long lastTarget;
	String timerType;
	boolean repeating;
	
	public TimerSystem(String timerType, long target, boolean repeating) {
		this.timerType = timerType;
		this.target = target * 1000;
		this.startTime = System.currentTimeMillis();
		this.repeating = repeating;
		this.lastTarget = target * 1000;
	}
	
	public TimerSystem() {}

	public boolean isRepeating() {
		return repeating;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}

	public long getTarget() {
		return target;
	}

	public void setTarget(long target) {
		this.target = target;
	}
	
	public void zeroTimer() {
		this.startTime = System.currentTimeMillis();
		this.target = this.lastTarget;
	}

	public boolean update() {

		if (System.currentTimeMillis() - startTime <= this.target) {
			
			sec+=1 * multiplier;
			
			if (sec >= 60) {
				min +=1;
				sec = 0; 
			}
			
			if (min >= 60) {
				hour +=1;
				min = 0;
			}

		}
		
		
		if (System.currentTimeMillis() - startTime >= this.target && this.repeating) {
			long tmp2 = System.currentTimeMillis() - startTime;
			System.out.println(this.timerType + "need more + " + tmp2);
			zeroTimer();
			return false;
		}
		
		
		if (System.currentTimeMillis() - startTime >= this.target) {
			long tmp = System.currentTimeMillis() - startTime;
			System.out.println(this.timerType + "Finished in + " + tmp);
			return true;

		}
		
		return true;

		
			
	}

	
	public long currentTime() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}

	public void timeInGame() {
		System.out.println("Time in game min : "+ min);
		System.out.println("Time in game sec: "+ sec);
		System.out.println("Time in game hour: "+ hour);
	}
	
	public String gameTime() {
		return "Clock is: "+hour +" : "+min;
	}

	public float getHour() {
		return hour;
	}

	public void setHour(float hour) {
		this.hour = hour;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
	
}
