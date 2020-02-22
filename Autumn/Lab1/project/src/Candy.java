import java.lang.IllegalArgumentException;


class Candy {
    private String name;
    private float weight;
    private float amountOfSugar;

    Candy(String name, float weight, float amountOfSugar) throws IllegalArgumentException {
        this.name = name;
        if (weight <= 0f) {
            throw new IllegalArgumentException("Weight mustn't be negative or zero");
        }
            this.weight = weight;
        if (amountOfSugar < 0f || amountOfSugar > 100f) {
            throw new IllegalArgumentException("Sugar must be in range(0, 100)");
        }
        this.amountOfSugar = amountOfSugar;
    }

    String getName() {
        return name;
    }

    float getWeight() {
        return weight;
    }

    float getAmountOfSugar() {
        return amountOfSugar;
    }
}