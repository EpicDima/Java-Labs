import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment {

    private final int id;
    private final List<Product> products;

    private Payment(int id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && products.equals(payment.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Покупка(идентификатор: %d; товары: %s)", id, products);
    }

    public static class Builder {
        private static int paymentCounter = 1;

        private List<Product> productList = new ArrayList<>();

        public Payment.Builder add(Product product) {
            if (product == null) {
                throw new IllegalArgumentException("Product == null");
            }
            productList.add(product);
            return this;
        }

        public Payment.Builder remove(Product product) {
            if (product == null) {
                throw new IllegalArgumentException("Product == null");
            }
            productList.remove(product);
            return this;
        }

        public Payment build() {
            if (productList.size() == 0) {
                throw new IllegalStateException("productList.size() == 0");
            }
            return new Payment(paymentCounter++, productList);
        }
    }


    public interface ImmutableProduct {
        int getId();
        String getName();
        String getDescription();
    }


    public static class Product implements ImmutableProduct {

        private static int productCounter = 1;

        private final int id = productCounter++;
        private final String name;
        private final String description;

        public Product(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return id == product.id &&
                    Objects.equals(name, product.name) &&
                    Objects.equals(description, product.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return String.format("Товар(идентификатор: %d; название: %s; описание: %s)", id, name, description);
        }
    }
}
