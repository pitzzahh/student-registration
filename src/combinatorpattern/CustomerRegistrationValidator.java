package combinatorpattern;

import java.time.Period;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.function.Function;
import static combinatorpattern.CustomerRegistrationValidator.*;
import static combinatorpattern.CustomerRegistrationValidator.ValidationResult.*;

public interface CustomerRegistrationValidator extends Function <Customer, ValidationResult> {

    static CustomerRegistrationValidator isEmailValid() {
        return customer -> Pattern.compile("^[a-zA-Z\\d._%+-]+@[A-Z\\d.-]+\\.[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(customer.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }

    static CustomerRegistrationValidator isPhoneNumberValid() {
        return customer -> Pattern.compile("^09\\d{9}$").matcher(customer.getPhoneNumber()).matches() ? SUCCESS : PHONE_NUMBER_NOT_VALID;
    }

    static CustomerRegistrationValidator isAdult() {
        return customer -> Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears() >= 18 ? SUCCESS : IS_NOT_AN_ADULT;
    }

    default CustomerRegistrationValidator and(CustomerRegistrationValidator other) {
        return customer -> this.apply(customer).equals(SUCCESS) ? other.apply(customer) : this.apply(customer);
    }

    enum ValidationResult {
        SUCCESS,
        EMAIL_NOT_VALID,
        PHONE_NUMBER_NOT_VALID,
        IS_NOT_AN_ADULT
    }
}
