package ch.so.aio.camel.ws.demo.routes;

import org.example.contactbook.SearchContacts;

public class SearchContactsRequestBuilder {

    public SearchContacts searchContacts(String name) {
        SearchContacts searchContacts = new SearchContacts();
        searchContacts.setName(name);
        return searchContacts;
    }
}
