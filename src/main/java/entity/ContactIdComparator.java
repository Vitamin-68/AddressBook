package entity;

import java.util.Comparator;

public class ContactIdComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
