package at.technikum.springrestbackend.model;

import at.technikum.springrestbackend.dto.lawyer.CreateLawyerRequest;
import at.technikum.springrestbackend.model.Specialization;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentMethodTest {

    @Test
    void allValues_Present() {
        // Arrange
        PaymentMethod[] allPaymentMethods = PaymentMethod.values();

        // Assert
        assertEquals(4, allPaymentMethods.length);
        assertTrue(Arrays.asList(allPaymentMethods).contains(PaymentMethod.CREDIT_CARD));
        assertTrue(Arrays.asList(allPaymentMethods).contains(PaymentMethod.PAYPAL));
        assertTrue(Arrays.asList(allPaymentMethods).contains(PaymentMethod.BANK_TRANSFER));
        assertTrue(Arrays.asList(allPaymentMethods).contains(PaymentMethod.CASH));
    }
}