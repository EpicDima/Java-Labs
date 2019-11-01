import java.util.ArrayList;
import java.util.List;

public class BigGiftImpl implements Gift {

    private List<Candy> candies = new ArrayList<>();

    @Override
    public void addCandy(Candy candy) {
        candies.add(candy);
    }

    @Override
    public List<Candy> getCandies() {
        return candies;
    }

    @Override
    public float getWeightOfCandies() {
        float weight = 0;
        for (Candy c : candies) {
            weight += c.getWeight();
        }
        return weight;
    }

    @Override
    public void sort() {
        candies.sort((c1, c2) -> (int) (c2.getWeight() - c1.getWeight()));
    }

    @Override
    public Candy chooseBySugarFromRange(float from, float to) {
        for (Candy c : candies) {
            if (c.getAmountOfSugar() >= from && c.getAmountOfSugar() <= to) {
                return c;
            }
        }
        return null;
    }
}