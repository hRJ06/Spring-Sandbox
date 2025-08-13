import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.Hindol.Library_Service.DTO.UserDTO;
import com.Hindol.Library_Service.Exception.ResourceNotFoundException;
import com.Hindol.Library_Service.LibraryServiceApplication;
import com.Hindol.Library_Service.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = LibraryServiceApplication.class)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Book-Service")
public class PactConsumerTest {

    @Autowired
    private UserService userService;

    @Pact(consumer = "BookDetail")
    public RequestResponsePact getBookDetail(PactDslWithProvider provider) {
        return provider.given("book exist")
                .uponReceiving("get book detail")
                .path("/book/get")
                .query("id=4")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 2)
                        .stringValue("name", "Basic of C++"))
                .toPact();

    }

    @Pact(consumer = "BookDetail")
    public RequestResponsePact getBookNotExist(PactDslWithProvider provider) {
        return provider.given("book not exist")
                .uponReceiving("get book detail")
                .path("/book/get")
                .query("id=2")
                .willRespondWith()
                .status(404)
                .toPact();
    }
    @Test
    @PactTestFor(pactMethod = "getBookDetail", port = "9999")
    public void rentBook() {
        Long userId = 2L, bookId = 4L;
        UserDTO user = userService.rentBook(userId, bookId);
        Assertions.assertTrue(user.getRentedBook().stream()
                .anyMatch(id -> id.equals(bookId)));
    }

    @Test
    @PactTestFor(pactMethod = "getBookNotExist", port = "9999")
    public void rentBook_whenBookNotExist() {
        Long userId = 2L, bookId = 2L;
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> userService.rentBook(userId, bookId)
        );
    }

}
