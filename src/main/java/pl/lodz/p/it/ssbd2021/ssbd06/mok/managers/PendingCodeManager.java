package pl.lodz.p.it.ssbd2021.ssbd06.mok.managers;

import pl.lodz.p.it.ssbd2021.ssbd06.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd06.entities.PendingCode;
import pl.lodz.p.it.ssbd2021.ssbd06.entities.enums.CodeType;
import pl.lodz.p.it.ssbd2021.ssbd06.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.facades.PendingCodeFacade;
import pl.lodz.p.it.ssbd2021.ssbd06.utils.email.EmailSender;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PendingCodeManager {
    @Inject
    private EmailSender emailSender;

    @Inject
    private PendingCodeFacade pendingCodeFacade;

    @Inject
    private AccountFacade accountFacade;

    /**
     * Tworzy nowy kod resetu hasła i wysyła wiadomość na e-mail odpowiadający kontu.
     *
     * @param account - konto, którego hasło ma zostać zresetowane
     * @throws AppBaseException - jeżeli nie uda się wysłać maila
     */
    public void sendResetPassword(Account account) throws AppBaseException {
        PendingCode pendingCode = new PendingCode();
        pendingCode.setCode(UUID.randomUUID().toString());
        pendingCode.setUsed(false);
        pendingCode.setAccount(account);
        pendingCode.setCodeType(CodeType.PASSWORD_RESET);
        pendingCode.setCreatedBy(account);

        account.getPendingCodeList().add(pendingCode);

        pendingCodeFacade.create(pendingCode);
        accountFacade.edit(account);
        emailSender.sendResetPasswordEmail(account.getFirstname(), account.getLogin(), pendingCode.getCode());
    }

    /**
     * Ponownie ysyła wiadomość dotyczącą resetowania hasła na e-mail odpowiadający kontu.
     *
     * @param account - konto, którego hasło ma zostać zresetowane
     * @throws AppBaseException - jeżeli nie uda się wysłać maila
     */
    public void sendResetPasswordAgain(Account account) throws AppBaseException {
        PendingCode previousResetCode = pendingCodeFacade.findResetCodeByAccount(account);
        previousResetCode.setUsed(true);
        pendingCodeFacade.edit(previousResetCode);

        PendingCode pendingCode = new PendingCode();
        pendingCode.setCode(UUID.randomUUID().toString());
        pendingCode.setUsed(false);
        pendingCode.setAccount(account);
        pendingCode.setCodeType(CodeType.PASSWORD_RESET);
        pendingCode.setCreatedBy(account);

        account.getPendingCodeList().add(pendingCode);

        pendingCodeFacade.create(pendingCode);
        accountFacade.edit(account);
        emailSender.sendResetPasswordEmail(account.getFirstname(), account.getLogin(), pendingCode.getCode());
    }
}