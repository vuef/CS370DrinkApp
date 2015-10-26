package Service;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by student on 10/26/15.
 */
public class HttpRequestManager {
        // TODO look into making this a config rather than a const
        private final int MAX_BYTE_CAPACITY = 5000000;
        private AndroidHttpClient client;

        public HttpRequestManager(){
            client = AndroidHttpClient.newInstance(null);
        }


        @Override
        public void finalize(){
            close();
        }


        public void close() {
            if(client!=null){
                client.close();
                client = null;
            }
        }

        public final String initiateHttpGet(URI uri) throws Exception {
            StringBuilder stringBuilder = new StringBuilder();
            HttpGet getRequest = new HttpGet(uri);

            try {
                HttpResponse response = client.execute(getRequest);
                int statusCode = response.getStatusLine().getStatusCode();


                if(statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String line;

                    while((line = reader.readLine()) != null){
                        stringBuilder.append(line);
                    }

                    reader.close();

                    return stringBuilder.toString();
                }
                else if (statusCode == HttpStatus.SC_FORBIDDEN || statusCode == HttpStatus.SC_UNAUTHORIZED)
                {
                    throw new HttpResponseException(statusCode, "Authorization Failure");
                }
                else
                {
                    throw new HttpResponseException(statusCode, "Unable to complete request");
                }
            }finally {
                close();
            }
        }
    }

}
