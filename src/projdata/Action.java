package projdata;

@FunctionalInterface
public interface Action<T> {
	 public T add(T t1, T t2);
}
