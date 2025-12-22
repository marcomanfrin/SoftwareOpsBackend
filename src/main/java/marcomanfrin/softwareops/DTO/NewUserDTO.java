package marcomanfrin.softwareops.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewUserDTO(
		@NotBlank(message = "Il nome è un campo obbligatorio")
		@Size(min = 2, max = 30, message = "Il nome deve avere una lunghezza tra 2 e 30 caratteri")
		String firstName,
		@NotBlank(message = "Il cognome è un campo obbligatorio")
		@Size(min = 2, max = 30, message = "Il cognome deve avere una lunghezza tra 2 e 30 caratteri")
		String surname,
        String username,
		@NotBlank(message = "L'email è un campo obbligatorio")
		@Email(message = "L'email non è nel formato corretto")
		String email,
		@NotBlank(message = "Il nome è un campo obbligatorio")
		@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
		String password,
		LocalDate dateOfBirth) {
}
