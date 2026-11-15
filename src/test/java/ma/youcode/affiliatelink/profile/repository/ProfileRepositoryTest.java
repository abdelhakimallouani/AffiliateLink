package ma.youcode.affiliatelink.profile.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ma.youcode.affiliatelink.profile.entity.Profile;

@DataJpaTest
@Testcontainers
// @Testcontainers(disabledWithoutDocker = true)
// @AutoConfigureTestDatabase(replace = Replace.NONE)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ProfileRepositoryTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void findById_shouldReturnEmpty_whenProfileDoesNotExist() {
        Long nonExistentId = 999L;
        var result = profileRepository.findById(nonExistentId);
        assertThat(result).isEmpty();
    }

    @Test
    void existsByNormalizedEmail_shouldReturnFalse_whenEmailDoesNotExist() {

        // Arrange
        String email = "unknown@example.com";

        // Act
        boolean result = profileRepository.existsByEmailIgnoreCase(email);

        // Assert
        assertThat(result).isFalse();
    }
}
