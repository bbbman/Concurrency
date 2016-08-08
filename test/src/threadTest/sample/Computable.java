package threadTest.sample;

public interface Computable<A,V> {
	V compute(A arg) throws InterruptedException;
}
