package handlers;

import models.Request;
import models.Response;

/**
 * Created by green on 6/17/2019.
 */
public interface Handler {
    Response handle(Request request);
}
