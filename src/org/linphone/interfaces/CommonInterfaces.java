package org.linphone.interfaces;

/**
 * Created by fjdelahorra on 27/6/16.
 */
public interface CommonInterfaces {

    interface registerUser {
        void onGetDataDone(String result);
    }

    interface Country {
        void onGetCountriesDone(String result);
    }

    interface Balance {
        void onGetBalanceDone(String result);
    }

    interface Exceptions {
        void onForbbiden(int callRef);

        void onUnauthorized(int callRef);

        void NotFound(int callRef);

        void ConectionException(int callRef);

        void ErrorServer(int callRef);
    }

}
