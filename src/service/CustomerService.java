package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//Per condividere il set di dati dei clienti tra tutte le istanze di CustomerService, dichiaro la variabile customers come static,
//in modo che tutti gli oggetti CustomerService condividano la stessa istanza della mappa dei clienti.

//Quando aggiungi oggetti a collezioni come Map, Set o List, questi oggetti vengono spesso gestiti in base ai loro valori
//piuttosto che ai riferimenti.Gli algoritmi delle collezioni utilizzano i metodi equals() e hashCode() per determinare
//l'uguaglianza e l'identificazione degli oggetti. Di conseguenza, se hai una collezione di oggetti CustomerService, sovrascrivere
//questi metodi può influire sulla corretta gestione degli oggetti all'interno della collezione.
public class CustomerService {
    private static CustomerService INSTANCE; //KEBAB_CASE
    private Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public Customer addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
        return customer;
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }
}
