package inf112.starhunt.model;

/**
 * TODO: javadoc
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class Triple<A, B, C>{
    private final A first;
    private final B second;
    private final C third;

    /**
     * TODO: javadoc
     *
     * @param first
     * @param second
     * @param third
     */
    public Triple(A first, B second, C third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }
}
