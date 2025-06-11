package CoffeeExampleSpringTemplate.model;

public class EuropeanCode {

    private int FirstDig;
    private int SecondDig;
    private int ThirdDig;
    

    public EuropeanCode() {
    }

    public EuropeanCode(int FirstDig, int SecondDig, int ThirdDig) {
        this.FirstDig = FirstDig;
        this.SecondDig = SecondDig;
        this.ThirdDig = ThirdDig;
    }

    public int getFirstDig() {
        return FirstDig;
    }

    public void setFirstDig(int FirstDig) {
        this.FirstDig = FirstDig;
    }

    public int getSecondDig() {
        return SecondDig;
    }

    public void setSecondDig(int SecondDig) {
        this.SecondDig = SecondDig;
    }

    public int getThirdDig() {
        return ThirdDig;
    }

    public void setThirdDig(int ThirdDig) {
        this.ThirdDig = ThirdDig;
    }

    @Override
    public String toString() {
        return "EuropeanCode{" + "FirstDig=" + FirstDig + ", SecondDig=" + SecondDig + ", ThirdDig=" + ThirdDig + '}';
    }

    
}