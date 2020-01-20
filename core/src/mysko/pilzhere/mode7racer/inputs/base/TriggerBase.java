package mysko.pilzhere.mode7racer.inputs.base;

public abstract class TriggerBase<T extends ControllerBase> {

	public boolean wasOn;

	public abstract boolean isOn(T controller);

	public abstract String format();
}
