import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.Hindol.Book_Service.BookServiceApplication;
import com.Hindol.Book_Service.Entity.BookEntity;
import com.Hindol.Book_Service.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BookServiceApplication.class)
@Provider("Book-Service")
/* @PactFolder("pacts") */
@PactBroker(
        url = "https://spring-boot.pactflow.io/",
        authentication = @PactBrokerAuth(token = "${pact.broker.token}")
)
public class PactProviderTest {

    @Autowired
    private BookRepository bookRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTest(PactVerificationContext context) {

        context.verifyInteraction();
    }

    @State(value = "book exist", action = StateChangeAction.SETUP)
    public void bookExist() {

    }

    @State(value = "book exist", action = StateChangeAction.TEARDOWN)
    public void bookExistTearDown() {

    }

    @State(value = "book not exist", action = StateChangeAction.SETUP)
    public void bookNotExist() {
        /* Delete Record */
        Optional<BookEntity> book = bookRepository.findById(2L);
        if(book.isPresent()) {
            bookRepository.deleteById(2L);
        }

    }

    @State(value = "book not exist", action = StateChangeAction.TEARDOWN)
    public void bookNotExistTearDown() {

    }
}
