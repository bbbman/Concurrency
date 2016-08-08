package designmodel.statepattern;

public class Context {
	
	private State state;
	
	public Context(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void method() {
		System.out.println("×´Ì¬Îª£º" + state.getVaule());
		if(state.getVaule().equals("state1"))
			state.method1();
		else if(state.getVaule().equals("state2"))
		    state.method2();
	}
}
