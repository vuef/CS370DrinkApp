package Service;
import Interface.IHttpCallbackListener;

/**
 * Created by student on 10/26/15.
 */
public class DrinkSearchRequestArgs {

        private IHttpCallbackListener listener;
        private String url;

        public IHttpCallbackListener getListener() {
            return listener;
        }

        public void setListener(IHttpCallbackListener listener) {
            this.listener = listener;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
