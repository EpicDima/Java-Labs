import java.util.ArrayList;
import java.util.List;


class GiftImpl implements Gift {

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
		candies.sort((c1, c2) -> (int) (c1.getAmountOfSugar() - c2.getAmountOfSugar()));
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