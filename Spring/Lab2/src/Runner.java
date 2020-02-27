public class Runner {
    public static void main(String[] args) {

        Payment.Product product0 = new Payment.Product("NULL", "NULL");
        Payment.Product product1 = new Payment.Product("Компьютер", "Мощный уникальный компьютер на базе процессора AMD");
        Payment.Product product2 = new Payment.Product("Ноутбук", "Ультратонкий ноутбук компании Asus");
        Payment.Product product3 = new Payment.Product("Сапоги", "Резиновые сапоги красного цвета 39 размера");
        Payment.Product product4 = new Payment.Product("Телефон Xiaomi", "Xiaomi Mi A3");
        Payment.Product product5 = new Payment.Product("Провод для зарядки", "Тип: USB-C, длина: 1.5 м");

        Payment payment1 = new Payment.Builder().add(product1).add(product2).add(product1).build();
        Payment payment2 = new Payment.Builder().add(product2).add(product1).add(product0).remove(product0).build();
        Payment payment3 = new Payment.Builder().add(product3).add(product2).add(product2).remove(product2).build();
        Payment payment4 = new Payment.Builder().add(product4).add(product1).add(product1).build();
        Payment payment5 = new Payment.Builder().add(product5).add(product5).build();

        String payments = String.join("\n", payment1.toString(), payment2.toString(), payment3.toString(),
                payment4.toString(), payment5.toString());

        System.out.println(payments);

        System.out.println("\n\n");
        Payment.ImmutableProduct product6 = new Payment.Product("Стол компьютерный", "Материал: красное дерево");
        System.out.println(String.format("Id: %d; Name: %s; Description: %s",
                product6.getId(), product6.getName(), product6.getDescription()));
    }
}
