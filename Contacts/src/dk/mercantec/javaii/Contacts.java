package dk.mercantec.javaii;

import java.util.ArrayList;

/**
 * Created by code on 17/02/15.
 */
public class Contacts {
    private ArrayList<BaseContact> contacts = new ArrayList<BaseContact>();

    public Contacts(){
        SQLHandler sqlHandler = new SQLHandler();
        contacts.addAll(sqlHandler.loadInternal());
        contacts.addAll(sqlHandler.loadExternal());
    }

    public ArrayList<BaseContact> getContacts() {
        return contacts;
    }

    public void AddInternalContact(String name, String phone, String email, String department) {
        Internal internal = new Internal(name, phone, email, department)
        contacts.add(internal);
        SQLHandler sqlHandler = new SQLHandler();
        sqlHandler.insertInternal(internal);
    }

    public void AddExternalContact(String name, String phone, String email, String company) {
        External external = new External(name, phone, email, company);
        contacts.add(external);


    }
}
