package BoxCollector;

import BoxCollector.sweets.Candy;
import BoxCollector.sweets.Chocolate;
import BoxCollector.sweets.Donut;
import BoxCollector.sweets.Sweet;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Ivan Yushin
 *
 * @see SweetsBox
 * @see Sweet
 * @see Box
 * @see Converter функциональный интерфейс
 * @see #AMOUNT_OF_SWEETS Candy, Donut, Chocolate количество видов сладостей
 *
 */
public class Laucnher {
    private static final int AMOUNT_OF_SWEETS = 3;

    public static void main(String[] args) throws InterruptedException {

        /** 2 пункт задания */
        /** Виды коробок с разными предикатами, раскоментировать коробку чтобы протестировать работу */
        //Box box = new SweetsBox(sweet -> sweet instanceof Chocolate && sweet.getWeight() > 5); // только шоколадки весом больше 5
        Box box = new SweetsBox(); // любые сладости
        //Box box = new SweetsBox(sweet -> sweet.getPrice() < 50); // любые сладости дешевле 50 рублей

        /** @see FactorySweets * ----- 1 пункт задания ----- */
        //Создание вкусняшек через статические ссылки на методы
        FactorySweets<Sweet> candyFactory = Candy::new;
        box.add(candyFactory.create(15, Sweet.getRandomWeight(), Candy.randomTaste()));
        FactorySweets<Sweet> chocolateFactory = Donut::new;
        box.add(chocolateFactory.create(100, Sweet.getRandomWeight(), Candy.randomTaste()));

        //через фабрику отдельных классов
        FactorySweets<Sweet> oneCandy = new FactorySweets<Sweet>() {
            @Override
            public Sweet create(double price, double weight, String taste) {
                return new Candy(price, weight, taste);
            }
        };

        FactorySweets<Sweet> oneDonut = new FactorySweets<Sweet>() {
            @Override
            public Sweet create(double price, double weight, String taste) {
                return new Chocolate(price, weight, taste);
            }
        };
        for (int i = 0; i < 20; i++) {
            box.add(oneCandy.create(10, Sweet.getRandomWeight(), Sweet.randomTaste()));
            box.add(oneDonut.create(80, Sweet.getRandomWeight(), Sweet.randomTaste()));
        }

        /** 4 пункт задания */
        box.getInfoAboutSweets();
        System.out.println("------------------------");
        System.out.println("Общая стоимость коробки " + box.totalCostOfBox());
        System.out.println("Общий вес коробки " + box.totalWeightOfBox());
        box.howMuchSweetInBox(sweet -> sweet instanceof Candy);//предикат, какие конфеты считаем в коробке
        System.out.println("------------------------");

        /** 3 пункт задания */
        int eur = 77;
        int usd = 68;
        Converter converter;
        converter = cost -> cost / eur;
        System.out.printf("В евро коробка стоит %.2f", converter.convert(box.totalCostOfBox())).println();
        converter = cost -> cost / usd;
        System.out.printf("В долларах %.2f", converter.convert(box.totalCostOfBox()));
    }
}