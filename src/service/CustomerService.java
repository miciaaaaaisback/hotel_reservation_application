package service;

import reservationModel.Customer;
import java.util.Collection;
import java.util.Map;

//Per condividere il set di dati dei clienti tra tutte le istanze di CustomerService, dichiaro la variabile customers come static,
//in modo che tutti gli oggetti CustomerService condividano la stessa istanza della mappa dei clienti.

//Quando aggiungi oggetti a collezioni come Map, Set o List, questi oggetti vengono spesso gestiti in base ai loro valori
//piuttosto che ai riferimenti.Gli algoritmi delle collezioni utilizzano i metodi equals() e hashCode() per determinare
//l'uguaglianza e l'identificazione degli oggetti. Di conseguenza, se hai una collezione di oggetti CustomerService, sovrascrivere
//questi metodi può influire sulla corretta gestione degli oggetti all'interno della collezione.
public class CustomerService {
    private static Map<String, Customer> customers;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
