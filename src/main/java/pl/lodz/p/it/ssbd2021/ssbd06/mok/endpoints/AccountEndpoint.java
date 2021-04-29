package pl.lodz.p.it.ssbd2021.ssbd06.mok.endpoints;

import pl.lodz.p.it.ssbd2021.ssbd06.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.endpoints.interfaces.AccountEndpointLocal;
import pl.lodz.p.it.ssbd2021.ssbd06.mok.services.AccountService;

import pl.lodz.p.it.ssbd2021.ssbd06.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd06.utils.common.AbstractEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;


@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccountEndpoint extends AbstractEndpoint implements AccountEndpointLocal {

    @Inject
    private AccountManager accountManager;

    @Override
    @RolesAllowed("blockAccount")
    public void blockAccount(String login) throws AppBaseException {
        accountManager.blockAccount(login);
    }

    @Override
    public void confirmAccount(String code) throws AppBaseException {
        accountService.confirm(code);
    }
}
