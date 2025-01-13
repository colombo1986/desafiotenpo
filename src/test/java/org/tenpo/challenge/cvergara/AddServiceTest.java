package org.tenpo.challenge.cvergara;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tenpo.challenge.cvergara.httpclient.RandomNumberClient;
import org.tenpo.challenge.cvergara.service.AddService;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddServiceTest {

    private AddService addService;

    @Mock
    private RandomNumberClient randomNumberClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addService = new AddService(randomNumberClient);
    }

    @Test
    void testAddNumbersSuccess() {
        // Mock del cliente de números aleatorios
        when(randomNumberClient.getRandomNumber()).thenReturn(Mono.just(10));

        // Ejecutar el servicio
        Integer result = addService.addNumbers(5, 5).block();

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(11, result); // (5 + 5) + 10% = 11

        verify(randomNumberClient, times(1)).getRandomNumber();
    }
}
