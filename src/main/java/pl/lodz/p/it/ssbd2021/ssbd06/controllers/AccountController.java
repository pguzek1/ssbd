package pl.lodz.p.it.ssbd2021.ssbd06.controllers;


import pl.lodz.p.it.ssbd2021.ssbd06.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.dto.RegisterAccountDto;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.endpoints.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd06.validation.Login;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
public class AccountController extends AbstractController {

    @Inject
    private AccountEndpointLocal accountEndpoint;

    /**
     * Blokuje konto użytkownika o podanym loginie.
     *
     * @param login login konta, które ma zostać zablokowane.
     * @throws AppBaseException gdy nie udało się zablokowanie konta.
     */
    @PUT
    @Path("/{login}/block")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeAccountActiveStatus(@NotNull @PathParam("login") @Valid String login) throws AppBaseException {
        // todo metoda repeat() z abstractController;
        accountEndpoint.blockAccount(login);
    }


    /**
     * Potwierdza konto użytkownika odpowiadające podanemu kodowi aktywacyjnemu
     *
     * @param code kod aktywacyjny konta
     * @throws AppBaseException gdzy potwierdzenie konta się nie powiodło
     */
    @POST
    @Path("/confirm/{code}")
    public void confirm(@PathParam("code") String code) throws AppBaseException {
        // todo metoda repeat() z abstractController;
        accountEndpoint.confirmAccount(code);
    }

    /**
     * Rejestruje konto użytkownika w systemie.
     *
     * @param registerAccountDto dane użytkownika do rejestracji
     * @throws AppBaseException podczas błędu związanego z bazą danych
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerAccount(@NotNull @Valid RegisterAccountDto registerAccountDto) throws AppBaseException {
        accountEndpoint.registerAccount(registerAccountDto);
    }

    /**
     * Resetuje hasło użytkownika w systemie.
     *
     * @param passwordResetDto obiekt zawierający dane wymagane do resetowania hasła
     * @throws AppBaseException podczas błędu związanego z bazą danych
     */
    @POST
    @Path("/reset/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void resetPassword(@NotNull @Valid PasswordResetDto passwordResetDto) throws AppBaseException {
        accountEndpoint.resetPassword(passwordResetDto);
    }

    /**
     * Wysyła na istniejący w systemie email wiadomość o resetowaniu hasła.
     *
     * @param email email na który zostanie wysłana wiadomość
     * @throws AppBaseException podczas błędu związanego z bazą danych
     */
    @POST
    @Path("/{email}/reset")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendResetPassword(@NotNull @Valid @Login String email) throws AppBaseException {
        accountEndpoint.sendResetPassword(email);
    }

    /**
     * Wysyła ponownie na istniejący w systemie email wiadomość o resetowaniu hasła.
     *
     * @param email email na który zostanie wysłana wiadomość
     * @throws AppBaseException podczas błędu związanego z bazą danych
     */
    @POST
    @Path("/{email}/reset")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendResetPasswordAgain(@NotNull @Valid @Login String email) throws AppBaseException {
        accountEndpoint.sendResetPasswordAgain(email);
    }
}