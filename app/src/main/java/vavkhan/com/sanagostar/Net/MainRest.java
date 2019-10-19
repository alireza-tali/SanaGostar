package vavkhan.com.sanagostar.Net;

import android.app.Application;
import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vavkhan.com.sanagostar.models.ErrorModel;
import vavkhan.com.sanagostar.R;
import vavkhan.com.sanagostar.SanaApplication;
import vavkhan.com.sanagostar.utils.GsonHelper;

public class MainRest<T> {
    private Application application;
    public static final int NETWORK_ERROR = 0;
    public static final int SERVER_ERROR = 1;
    public static final int AUTH_ERROR = 2;
    public static final int PARSER_ERROR = 3;
    public static final int NO_CONNECTION_ERROR = 4;
    public static final int TIMEOUT_ERROR = 5;
    Class<T> classType;
    public MainRest(Application application,Class<T> classType) {
        this.application = application;
        this.classType = classType;
    }

    public void postAuth(JSONObject jsonParams, RestAPIListener deligate) {
        String address = "http://stage.achareh.ir/api/karfarmas/address";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                address, jsonParams,
                response -> {
                    T result = GsonHelper.ConvertStringToModel(response.toString(), classType);
                    deligate.Success(result);
                },
                volleyError -> {
                    ErrorModel errorMsg = createErrorModel(volleyError);
                    deligate.Error(errorMessage(errorMsg), String.valueOf(errorMsg.getErrorCode()));
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                String credentials = "09822222222"+":"+"sana1234";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);

                return headers;
            }
        };

        SanaApplication.Companion.get(application).addToRequestQueue(jsonObjReq, "SEND_TO_SERVER_TAG");
    }
    public void getListAuth(RestApiListListener deligate) {
        String address = "http://stage.achareh.ir/api/karfarmas/address";


        CustomJsonArrayRequest jsonObjReq = new CustomJsonArrayRequest(Request.Method.GET,
                address, null,
                response -> {
                    List<T> result = GsonHelper.convertJsonArrayToList(response.toString(), classType);
                    deligate.Success(result);
                },
                volleyError -> {
                    ErrorModel errorMsg = createErrorModel(volleyError);
                    deligate.Error(errorMessage(errorMsg), String.valueOf(errorMsg.getErrorCode()));
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                String credentials = "09822222222"+":"+"sana1234";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);

                return headers;
            }
        };

        SanaApplication.Companion.get(application).addToRequestQueue(jsonObjReq, "GET_FROM_SERVER_TAG");
    }
    private ErrorModel createErrorModel(VolleyError volleyError) {
        String message = null;
        Integer errorType = -1;
        int code = -1;

        try {
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                VolleyError newerror = new VolleyError(new String(volleyError.networkResponse.data));
                code = volleyError.networkResponse.statusCode;
                if (newerror.getMessage() == null || newerror.getMessage().isEmpty()
                        || newerror.getMessage().replace("\"", "").isEmpty())
                    message = null;
                else
                    message = newerror.getMessage();
                volleyError = new VolleyError(new String(volleyError.networkResponse.data));
            } else {
                if (volleyError.networkResponse != null && volleyError.getMessage().contains("auth")) {
                    return new ErrorModel(401, "401", errorType);
                }
            }
            if (volleyError.getMessage().equalsIgnoreCase("java.io.IOException: No authentication challenges found")) {
                return new ErrorModel(401, "401", errorType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (volleyError instanceof NetworkError) {
            errorType = NETWORK_ERROR;
        } else if (volleyError instanceof ServerError) {
            errorType = SERVER_ERROR;
        } else if (volleyError instanceof AuthFailureError) {
            errorType = AUTH_ERROR;
            code = 401;
        } else if (volleyError instanceof ParseError) {
            errorType = PARSER_ERROR;
        } else if (volleyError instanceof NoConnectionError) {
            errorType = NO_CONNECTION_ERROR;
        } else if (volleyError instanceof TimeoutError) {
            errorType = TIMEOUT_ERROR;
        }

        return new ErrorModel(code, message, errorType);
    }
    protected String errorMessage(ErrorModel errorModel) {
        String resultMessage = "";

        if (errorModel.getErrorCode() != -1) {

            switch (errorModel.getErrorCode()) {
                case 429:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage() .isEmpty()
                            || errorModel.getErrorMessage() .replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.request_denied_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 412:

                    if (errorModel.getErrorMessage()  == null || errorModel.getErrorMessage() .isEmpty() ||
                            errorModel.getErrorMessage() .replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.lastdata_incomplete_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 400:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.service_error_400);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 444:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.no_data_available_error_msg);
                    else
                        resultMessage =errorModel.getErrorMessage() ;
                    break;
                case 403:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.access_denied_request_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 401:
                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.login_error_status);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 410:
                    if (errorModel.getErrorMessage() != null && !errorModel.getErrorMessage().isEmpty())
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 417:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.addding_error_417);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 406:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.addding_error_406);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 423:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.process_denied_request_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 424:
                case 422:
                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.item_is_used_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 500:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.try_again_msg);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 416:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.addrequest_error_416);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;
                case 405:

                    if (errorModel.getErrorMessage() == null || errorModel.getErrorMessage().isEmpty() || errorModel.getErrorMessage().replace("\"", "").isEmpty())
                        resultMessage = application.getResources().getString(R.string.service_error_405);
                    else
                        resultMessage = errorModel.getErrorMessage() ;
                    break;

            }

        } else {
            if (errorModel.getErrorMessage() != null && errorModel.getErrorMessage().contains("authentication")) {
                resultMessage = application.getResources().getString(R.string.login_error_status);
            }
        }
        if ((resultMessage == null || resultMessage.isEmpty())) {
            resultMessage = application.getResources().getString(R.string.try_again_msg);
            if (errorModel.getErrorType() == NETWORK_ERROR) {
                resultMessage =  application.getResources().getString(R.string.try_again_msg);
            } else if (errorModel.getErrorType() == SERVER_ERROR) {
                resultMessage = application.getResources().getString(R.string.check_net_msg);
            } else if (errorModel.getErrorType() == AUTH_ERROR) {
                resultMessage = "401";
            } else if (errorModel.getErrorType() == PARSER_ERROR) {
                resultMessage = application.getResources().getString(R.string.try_again_msg);
            } else if (errorModel.getErrorType() == NO_CONNECTION_ERROR) {
                resultMessage = application.getResources().getString(R.string.check_net_msg);
            } else if (errorModel.getErrorType() == TIMEOUT_ERROR) {
                resultMessage =  application.getResources().getString(R.string.check_net_msg);
            }
        }

        return resultMessage;
    }
}
