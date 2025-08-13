import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.Hindol.Book_Service.BookServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BookServiceApplication.class)
@Provider("Book-Service")
@PactFolder("pacts")
public class PactProviderTest {

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
}
