import java.util.List;

public interface Gift {
    void addCandy(Candy candy);
    List<Candy> getCandies();
    float getWeightOfCandies();
    void sort();
    Candy chooseBySugarFromRange(float from, float to);
}
