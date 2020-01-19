package mysko.pilzhere.mode7racer.inputs.base;

public class Command {
	public Object cmd;
	public String label;
	public String id;
	public Command(Object cmd, String id, String label) {
		super();
		this.cmd = cmd;
		this.id = id;
		this.label = label;
	}
	
}