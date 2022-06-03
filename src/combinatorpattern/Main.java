package combinatorpattern;

import java.time.LocalDate;

import static combinatorpattern.CustomerRegistrationValidator.*;
import static combinatorpattern.CustomerRegistrationValidator.ValidationResult.SUCCESS;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer(
                "Catherine Alba",
                "catherineAlba@gmail.com",
                "9329272823",
                LocalDate.of(2003, 1, 1)
        );

        ValidationResult result = isEmailValid().and(isPhoneNumberValid()).and(isAdult()).apply(customer);
        if (result != SUCCESS) throw new IllegalStateException(result.name());
        System.out.println(result);
    }
}
