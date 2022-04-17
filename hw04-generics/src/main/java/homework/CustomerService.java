package homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> treeMap = new TreeMap<>((o1, o2) -> (int)(o1.getScores() - o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = treeMap.firstEntry();
        return Map.entry(entry.getKey().copyCustomer(), entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = treeMap.higherEntry(customer);
        return entry == null
                ? null
                : Map.entry(entry.getKey().copyCustomer(), entry.getValue());
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
