package inf112.starhunt.model;

public class Triple<A, B, C>{
    public final A first;
    public final B second;
    public final C third;

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
