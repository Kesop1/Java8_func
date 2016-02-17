package pl.pivovarit.kata;


import static java.util.Collections.emptyList;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

import pl.pivovarit.commons.dataset.Store;
import pl.pivovarit.commons.domain.Customer;
import pl.pivovarit.commons.domain.Item;
import pl.pivovarit.commons.domain.ItemEntry;
import pl.pivovarit.commons.domain.Shop;

public class Kata extends Store {

    private final List<Customer> customerList = getMall().getCustomerList();
    private final List<Shop> shopList = getMall().getShopList();

    /**
     * Find customers who have budget higher than 10_000
     */
    @Test
    public void P01_richCustomers() {

        // when
        final List<Customer> result = customerList.stream()
                .filter(customer -> customer.getBudget() > 10_000)
                .collect(toList());

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(customerList.get(3), customerList.get(7));
    }

    /**
     * Find ages of all customers
     */
    @Test
    public void P02_howOldAreTheCustomers() {

        // when
        final List<Integer> result = customerList.stream()
                .map(Customer::getAge)
                .collect(toList());

        // then
        assertThat(result).hasSize(10);
        assertThat(result).contains(22, 27, 28, 38, 26, 22, 32, 35, 21, 36);
    }

    /**
     * Create a list of customer's ascending ordered age values.
     */
    @Test
    public void P03_sortByAge() {

        // when
        final List<Integer> result = customerList.stream()
                .map(Customer::getAge)
                .sorted()
                .collect(toList());

        // then
        assertThat(result).contains(21, 22, 22, 26, 27, 28, 32, 35, 36, 38);
    }

    /**
     * Create a list of customer's descending ordered age values.
     */
    @Test
    public void P04_descSortByAge() {

        // when
        final List<Integer> result = customerList.stream()
                .map(Customer::getAge)
                .sorted((o1, o2) -> o2 - o1)
                .collect(toList());

        // then
        assertThat(result).contains(38, 36, 35, 32, 28, 27, 26, 22, 22, 21);
    }

    /**
     * Find 3 richest customers
     */
    @Test
    public void P05_top3RichCustomer() {

        // when
        final List<String> result = customerList.stream()
                .sorted((left, right) -> right.getBudget() - left.getBudget())
                .limit(3)
                .map(Customer::getName)
                .collect(toList());

        // then
        assertThat(result).contains("Diana", "Andrew", "Chris");
    }

    /**
     * Find all customers age distinct values
     */
    @Test
    public void P06_distinctAge() {

        // when
        final List<Integer> result = customerList.stream()
                .map(Customer::getAge)
                .distinct()
                .collect(toList());

        // then
        assertThat(result).contains(22, 27, 28, 38, 26, 32, 35, 21, 36);
    }

    /**
     * Find all items customers want to buy
     */
    @Test
    public void P07_itemsCustomersWantToBuy() {
        // when
        final List<String> result = customerList.stream()
                .flatMap(c -> c.getWantToBuy().stream())
                .map(ItemEntry::getName)
                .collect(toList());


        // then
        assertThat(result).contains("small table", "plate", "fork", "ice cream", "screwdriver", "cable", "earphone", "onion",
                "ice cream", "crisps", "chopsticks", "cable", "speaker", "headphone", "saw", "bond",
                "plane", "bag", "cold medicine", "chair", "desk", "pants", "coat", "cup", "plate", "fork",
                "spoon", "ointment", "poultice", "spinach", "ginseng", "onion");
    }

    /**
     * Find the name of the richest customer (willing to spend the most),
     * if no customers throw {@link NoSuchElementException}
     * *hint* reduce *hint*
     */
    @Test
    public void P08_richestCustomer() {
        // when
        final String result = customerList.stream()
                .reduce((left, right) -> left.getBudget() > right.getBudget() ? left : right)
                .map(Customer::getName)
                .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(result).isEqualTo("Diana");
    }

    /**
     * Find what does the youngest customer want to buy.
     * If the youngest customer does not exist, return an empty list.
     */
    @Test
    public void P09_youngestCustomer() {

        // when
        final List<ItemEntry> result = customerList.stream()
                .reduce((left, right) -> left.getAge() < right.getAge() ? left : right)
                .map(Customer::getWantToBuy)
                .orElse(emptyList());

        // then
        assertThat(result)
                .extracting(ItemEntry::getName)
                .containsOnly("fork", "cup", "plate", "spoon");
    }

    /**
     * Find the first customer who registered this online store by using {@link Stream#findFirst}
     * Throw {@link NoSuchElementException} if such customer does not exist.
     * The customerList are ascending ordered by registered timing.
     */
    @Test
    public void P10_firstRegistrant() {

        // when
        final Customer result = customerList.stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(result).isEqualTo(customerList.get(0));
    }

    /**
     * Find whether any customer older than 40 exists or not.
     */
    @Test
    public void P11_isThereAnyoneOlderThan40() {
        // when
        boolean result = customerList.stream()
                .anyMatch(customer -> customer.getAge() > 40);

        // then
        assertThat(result).isFalse();
    }

    /**
     * Check whether all customer are older than 20 or not
     */
    @Test
    public void P12_isEverybodyOlderThan20() {
        // when
        boolean result = customerList.stream()
                .allMatch(customer -> customer.getAge() > 20);

        // then
        assertThat(result).isTrue();
    }

    /**
     * Confirm that none of the customer has an empty WTB list
     */
    @Test
    public void P13_everyoneWantsSomething() {
        // when
        boolean result = customerList.stream()
                .noneMatch(customer -> customer.getWantToBuy().isEmpty());

        // then
        assertThat(result).isTrue();
    }

    /**
     * Create a list of customer names (no duplicates)
     */
    @Test
    public void P14_nameList() {
        // when
        final List<String> result = customerList.stream()
                .map(Customer::getName)
                .distinct()
                .collect(toList());

        // then
        assertThat(result).containsOnly("Joe", "Steven", "Patrick", "Diana", "Chris", "Kathy", "Alice", "Andrew",
                "Martin", "Amy");
    }

    /**
     * Create a set of customer ages
     */
    @Test
    public void P15_ageSet() {
        // when
        final Set<Integer> result = customerList.stream()
                .map(Customer::getAge)
                .collect(toSet());

        // then
        assertThat(result).hasSize(9);
        assertThat(result).containsOnly(21, 22, 26, 27, 28, 32, 35, 36, 38);
    }

    /**
     * Create a csv string of customer names in brackets "[]"
     */
    @Test
    public void P16_nameInCsv() {
        // when
        final String result = customerList.stream()
                .map(Customer::getName)
                .collect(joining(",", "[", "]"));

        // then
        assertThat(result).isEqualTo("[Joe,Steven,Patrick,Diana,Chris,Kathy,Alice,Andrew,Martin,Amy]");
    }

    /**
     * Create a map of age as key and number of customers as value
     */
    @Test
    public void P17_ageDistribution() {
        // when
        final Map<Integer, Long> result = customerList.stream()
                .collect(groupingBy(Customer::getAge, counting()));

        // then
        assertThat(result).hasSize(9);
        result.forEach((k, v) -> {
            if (k.equals(22)) {
                assertThat(v).isEqualTo(2L);
            } else {
                assertThat(v).isEqualTo(1L);
            }
        });
    }

    /**
     * Calculate the average customer's age
     */
    @Test
    public void P18_averageAge() {
        // when
        final double result = customerList.stream()
                .mapToInt(Customer::getAge)
                .average()
                .orElse(0);


        // then
        assertThat(result).isEqualTo(28.7);
    }

    /**
     * Calculate the sum of all items' prices
     */
    @Test
    public void P19_howMuchToBuyAllItems() {
        // when
        long result = shopList.stream()
                .flatMap(shop -> shop.getItemList().stream())
                .mapToLong(Item::getPrice)
                .sum();

        // then
        assertThat(result).isEqualTo(60930L);
    }

    /**
     * Create a set of item names that customers want to buy, but are not available anywhere
     */
    @Test
    public void P20_itemsNotOnSale() {

        // when
        final List<String> itemListOnSale = shopList.stream().flatMap(shop -> shop.getItemList().stream())
                .map(Item::getName)
                .collect(toList());

        final Set<String> result = customerList.stream().flatMap(customer -> customer.getWantToBuy().stream())
                .map(ItemEntry::getName)
                .filter(itemName -> itemListOnSale.stream().noneMatch(itemName::equals))
                .collect(toSet());

        // then
        assertThat(result).hasSize(3);
        assertThat(result).containsOnly("bag", "pants", "coat");
    }

    /**
     * Create a customer's name list including who can everything they want.
     * All items must be in stock.
     */
    @Test
    public void P21_havingEnoughMoney() {
        // when

        final List<Item> availableItems = shopList.stream()
                .flatMap(shop -> shop.getItemList().stream())
                .collect(toList());

        final Predicate<Customer> customersWithDesiredItemsInStock = c -> c.getWantToBuy().stream()
                .allMatch(i -> availableItems.stream().anyMatch(item -> item.getName().equals(i.getName())));

        final Predicate<Customer> customersWithEnoughMoney = c -> c.getBudget() >= c.getWantToBuy().stream()
                .mapToInt(name -> availableItems.stream()
                        .filter(isEqual(name.getName()))
                        .mapToInt(Item::getPrice)
                        .min()
                        .orElse(0))
                .sum();

        final List<String> customerNameList = customerList.stream()
                .filter(customersWithDesiredItemsInStock)
                .filter(customersWithEnoughMoney)
                .map(Customer::getName)
                .collect(toList());

        // then
        assertThat(customerNameList).hasSize(8);
        assertThat(customerNameList).containsOnly("Joe", "Steven", "Patrick", "Diana", "Kathy", "Alice", "Martin", "Amy");
    }

}
