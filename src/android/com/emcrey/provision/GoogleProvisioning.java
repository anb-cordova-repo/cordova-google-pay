package com.emcrey.provision;

import android.content.Intent;

import com.emcrey.payment.GoogleProvSDK;
import com.emcrey.payment.IGoogleProvSDK;
import com.emcrey.payment.card.CardNetwork;
import com.emcrey.payment.card.CardToken;
import com.emcrey.payment.card.TokenProvider;
import com.emcrey.payment.card.TokenStatus;
import com.emcrey.payment.card.TokenizeResult;
import com.emcrey.payment.listeners.ResultValueListener;
import com.emcrey.payment.listeners.TokenStatusListener;
import com.emcrey.payment.listeners.TokenizeListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class GoogleProvisioning extends CordovaPlugin {
    private CallbackContext dataChangeCallbackContext;
    IGoogleProvSDK iGoogleProvSDK;
    private final Gson gson = new Gson();

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        iGoogleProvSDK = GoogleProvSDK.initGooglePrevisioning(this.cordova.getActivity());
        iGoogleProvSDK.registerDataChangedListener(() -> {
            if (dataChangeCallbackContext != null) {
                PluginResult r = new PluginResult(PluginResult.Status.OK, "data has changed ");
                r.setKeepCallback(true);
                dataChangeCallbackContext.sendPluginResult(r);
            }
        });
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "getActiveWalletId":
                this.getActiveWalletId(callbackContext);
                return true;
            case "checkEligibility":
                this.checkEligibility(callbackContext);
                return true;
            case "getStableHardwareId":
                this.getStableHardwareId(callbackContext);
                return true;
            case "registerDataChangedListener":
                dataChangeCallbackContext = callbackContext;
                PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
                r.setKeepCallback(true);
                dataChangeCallbackContext.sendPluginResult(r);
                return true;
            case "getEnvironment":
                this.getEnvironment(callbackContext);
                return true;
            case "pushCardTokenize":
                this.pushCardTokenize(args, callbackContext);
                return true;
            case "pushCobadgeCardTokenize":
                this.pushCobadgeCardTokenize(args, callbackContext);
                return true;
            case "viewCardToken":
                this.viewCardToken(args, callbackContext);
                return true;
            case "isCardTokenized":
                this.isCardTokenized(args, callbackContext);
                return true;
            case "getCardTokensList":
                this.getCardTokensList(callbackContext);
                return true;
            case "getCardTokenStatus":
                this.getCardTokenStatus(args, callbackContext);
                return true;
            
        }
        
        return false;
    }

    public void checkEligibility(CallbackContext callbackContext) {
        iGoogleProvSDK.checkEligibility(new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean available) {
                JSONObject object = new JSONObject();
                try {
                    object.put("available", available);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getActiveWalletId(CallbackContext callbackContext) {
        iGoogleProvSDK.getActiveWalletId(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String activeWalletId) {
                JSONObject object = new JSONObject();
                try {
                    object.put("activeWalletId", activeWalletId);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getStableHardwareId(CallbackContext callbackContext) {
        iGoogleProvSDK.getStableHardwareId(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String stableHardwareId) {
                JSONObject object = new JSONObject();
                try {
                    object.put("stableHardwareId", stableHardwareId);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getEnvironment(CallbackContext callbackContext) {
        iGoogleProvSDK.getEnvironment(new ResultValueListener<String>() {
            @Override
            public void onSuccess(String environment) {
                JSONObject object = new JSONObject();
                try {
                    object.put("environment", environment);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void pushCardTokenize(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String payloadBase64 = args.getString(0);
        CardNetwork network = CardNetwork.valueOf(args.getString(1));
        TokenProvider provider = TokenProvider.valueOf(args.getString(2));
        String googleOPCPayload = args.getString(3);
        String displayName = args.getString(4);
        String last4Pan = args.getString(5);

        iGoogleProvSDK.pushCardTokenize(payloadBase64, network, provider, googleOPCPayload, displayName, last4Pan,
                new TokenizeListener() {
                    @Override
                    public void onComplete(TokenizeResult tokenizeResult) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TokenizeResult", tokenizeResult);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    public void pushCobadgeCardTokenize(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String primaryPayloadBase64 = args.getString(0);
        CardNetwork primaryNetwork = CardNetwork.valueOf(args.getString(1));
        TokenProvider primaryProvider = TokenProvider.valueOf(args.getString(2));
        String auxiliaryPayloadBase64 = args.getString(3);
        CardNetwork auxiliaryNetwork = CardNetwork.valueOf(args.getString(4));
        TokenProvider auxiliaryProvider = TokenProvider.valueOf(args.getString(5));
        String googleOPCPayload = args.getString(6);
        String displayName = args.getString(7);
        String last4Pan = args.getString(8);

        iGoogleProvSDK.pushCobadgeCardTokenize(primaryPayloadBase64, primaryNetwork, primaryProvider,
                auxiliaryPayloadBase64, auxiliaryNetwork, auxiliaryProvider, googleOPCPayload, displayName, last4Pan,
                new TokenizeListener() {
                    @Override
                    public void onComplete(TokenizeResult tokenizeResult) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TokenizeResult", tokenizeResult);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callbackContext.success(object);
                    }

                    @Override
                    public void onFail(int code, String message) {
                        JSONObject object;
                        try {
                            object = new JSONObject();
                            object.put("code", code);
                            object.put("message", message);
                        } catch (JSONException e) {
                            object = new JSONObject();
                        }
                        callbackContext.error(object);
                    }
                });
    }

    public void viewCardToken(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String issuerTokenId = args.getString(0);
        TokenProvider provider = TokenProvider.valueOf(args.getString(1));

        iGoogleProvSDK.viewCardToken(issuerTokenId, provider, new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                JSONObject object = new JSONObject();
                try {
                    object.put("success", success);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void isCardTokenized(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String last4Pan = args.getString(0);
        CardNetwork network = CardNetwork.valueOf(args.getString(1));
        TokenProvider provider = TokenProvider.valueOf(args.getString(2));

        iGoogleProvSDK.isCardTokenized(last4Pan, network, provider, new ResultValueListener<Boolean>() {
            @Override
            public void onSuccess(Boolean isCardTokenized) {
                JSONObject object = new JSONObject();
                try {
                    object.put("isCardTokenized", isCardTokenized);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getCardTokensList(CallbackContext callbackContext) {
        iGoogleProvSDK.getCardTokensList(new ResultValueListener<List<CardToken>>() {
            @Override
            public void onSuccess(List<CardToken> cards) {
                JSONObject object = new JSONObject();
                try {
                    Type listType = new TypeToken<List<CardToken>>() {
                    }.getType();
                    String serialize = gson.toJson(cards, listType);
                    object.put("cardTokens", serialize);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    public void getCardTokenStatus(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String issuerTokenId = args.getString(0);
        TokenProvider provider = TokenProvider.valueOf(args.getString(1));

        iGoogleProvSDK.getCardTokenStatus(issuerTokenId, provider, new TokenStatusListener() {
            @Override
            public void onSuccess(TokenStatus tokenStatus, Boolean isSelected) {
                JSONObject object = new JSONObject();
                try {
                    object.put("tokenStatus", tokenStatus.name());
                    object.put("isSelected", isSelected);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                callbackContext.success(object);
            }

            @Override
            public void onFail(int code, String message) {
                JSONObject object;
                try {
                    object = new JSONObject();
                    object.put("code", code);
                    object.put("message", message);
                } catch (JSONException e) {
                    object = new JSONObject();
                }
                callbackContext.error(object);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        iGoogleProvSDK.handleTokenizationResult(requestCode, resultCode, intent);
    }

}
