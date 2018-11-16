package org.linphone.server.interfaces;

import org.linphone.interfaces.CommonInterfaces;
import org.linphone.model.User;

/**
 * Created by macmini02 on 3/10/16.
 */

public interface IBalanceRestApiUtil {

    void getBalance(User mUser, CommonInterfaces.Balance in);
}
