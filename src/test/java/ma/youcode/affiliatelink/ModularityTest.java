package ma.youcode.affiliatelink;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    private static final ApplicationModules modules =
            ApplicationModules.of(AffiliatelinkApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.forEach(System.out::println);
        modules.verify();
    }
}
