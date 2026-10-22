package ma.youcode.affiliatelink.common.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String email) {
        super("L'email " + email + " est deja utilise");
    }
}
