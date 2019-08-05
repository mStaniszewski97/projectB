package pl.michal.projectB.model;

import java.util.UUID;

public class Contact {
    enum ContactType {
        unknown(0), email(1), phone(2), jabber(3);
        private int value;

        private ContactType(int value) {
            this.value = value;
        }
    }

    private UUID id;
    private UUID customerId;
    private ContactType contactType;
    private String contact;

    public Contact(UUID customerId, String contact) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.contact = contact;
        this.contactType = ContactType.unknown;

        if (isEmail(contact)) {
            this.contactType = ContactType.email;
        }
        if (isPhone(contact)) {
            this.contactType = ContactType.phone;
        }
        if (isJabber(contact)) {
            this.contactType = ContactType.jabber;
        }
    }

    private boolean isJabber(String contact) {
        return contact.startsWith("jbr:");
    }

    private boolean isEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean isPhone(String phone) {
        String phoneNumberWithoutSpace = phone.replaceAll(" ", "");
        return phoneNumberWithoutSpace.length() == 9;
    }
}
