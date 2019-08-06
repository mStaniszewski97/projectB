package pl.michal.projectB.model;

import java.util.UUID;

public class Contact {
    public enum ContactType {
        unknown(0), email(1), phone(2), jabber(3);
        private int value;

        ContactType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
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
        this.contactType = chooseContactType(contact);
    }

    private ContactType chooseContactType(String contact) {
        if (isEmail(contact)) {
            return ContactType.email;
        }
        if (isPhone(contact)) {
            return ContactType.phone;
        }
        if (isJabber(contact)) {
            return ContactType.jabber;
        }
        return ContactType.unknown;
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

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", contactType=" + contactType +
                ", contact='" + contact + '\'' +
                '}';
    }
}
