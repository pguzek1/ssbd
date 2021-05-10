package pl.lodz.p.it.ssbd2021.ssbd06.mok.endpoints;


import pl.lodz.p.it.ssbd2021.ssbd06.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.dto.RegisterAccountDto;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.Date;

@Local
public interface AccountEndpointLocal {
    /**
     * Blokuje konto użytkownika o podanym loginie.
     *
     * @param login login konta, które ma zostać zablokowane.
     * @throws AppBaseException gdy nie udało się zablokowanie konta.
     */
    @RolesAllowed("blockAccount")
    void blockAccount(String login) throws AppBaseException;

    /**
     * Rejestruje konto użytkownika.
     *
     * @param registerAccountDto obiekt zawierający dane wymagane do utworzenia konta
     * @throws AppBaseException podczas błędu związanego z bazą danych
     */
    @PermitAll
    void registerAccount(RegisterAccountDto registerAccountDto) throws AppBaseException;

    /**
     * Potwierdza konto użytkownika odpowiadające podanemu kodowi aktywacyjnemu
     *
     * @param code kod aktywacyjny konta
     */
    @PermitAll
    void confirmAccount(String code) throws AppBaseException;

    /**
     * Aktualizuje dane związane z niepoprawnym uwierzytelnieniem się użytkownika.
     *
     * @param login login użytkownika
     * @param ipAddress adres ip użytkownika
     * @param authDate data udanego uwierzytelnienia
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @PermitAll
    void updateValidAuth(String login, String ipAddress, Date authDate) throws AppBaseException;

    /**
     * Aktualizuje dane związane z poprawnym uwierzytelnieniem się użytkownika.
     *
     * @param login login użytkownika
     * @param ipAddress adres ip użytkownika
     * @param authDate data nieudanego uwierzytelnienia
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @PermitAll
    void updateInvalidAuth(String login, String ipAddress, Date authDate) throws AppBaseException;

    /**
     * Resetuje hasło użytkownika.
     *
     * @param passwordResetDto obiekt zawierający dane wymagane do resetowania hasła
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @PermitAll
    void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException;

    /**
     * Wysyła token resetujący hasło użytkownika o podanym emailu.
     *
     * @param email email użytkownika
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @PermitAll
    void sendResetPassword(String email) throws AppBaseException;

    /**
     * Wysyła ponownie token resetujący hasło użytkownika o podanym emailu.
     *
     * @param email email użytkownika
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @PermitAll
    void sendResetPasswordAgain(String email) throws AppBaseException;

    /**
     * Zmienia hasło innego użytkownika.
     *
     * @param passwordResetDto obiekt zawierający dane wymagane do zmiany hasła
     * @throws AppBaseException gdy nie udało się zaktualizować danych
     */
    @RolesAllowed("editOwnPassword")
    void changeOtherPassword(PasswordResetDto passwordResetDto) throws AppBaseException;
}
