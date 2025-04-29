package inf112.starhunt.model;

public class Triple<A, B, C>{
    public final A first; // TODO: gjøre privat og heller bruke get-ere?
    public final B second; // TODO: samme her
    public final C third; // TODO: samme her

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
