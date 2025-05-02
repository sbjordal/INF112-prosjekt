package inf112.starhunt.model;

/**
 * Helper class for simplifying code logic.
 *
 * @param <A> first
 * @param <B> second
 * @param <C> third
 */
public class Triple<A, B, C>{
    private final A first;
    private final B second;
    private final C third;

    /**
     * Creates a new Triple object.
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
