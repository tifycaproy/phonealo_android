package org.linphone.server.interfaces;


import org.linphone.interfaces.CommonInterfaces;

/**
 * Created by macmini02 on 27/9/16.
 */

public interface IRegisterRestApiUtil {

    void retrieveData(String name, String mobile, String email, String prefix, CommonInterfaces.registerUser in);
}
