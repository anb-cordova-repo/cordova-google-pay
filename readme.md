# Google Pay Plugin for Android 

A Cordova plugin that provides integration with Google Pay services.

## Installation

```bash
cordova plugin add https://github.com/anb-cordova-repo/google-pay-plugin.git
```

## Usage

The Google Pay Plugin provides several methods to interact with Google Pay services:

### 1. Check Eligibility

```javascript
cordova.plugins.GoogleProv.checkEligibility(success, error);
```

### 2. Get Active Wallet Id

```javascript
cordova.plugins.GoogleProv.getActiveWalletId(success, error);
```

### 3. Get Stable Hardware Id

```javascript
cordova.plugins.GoogleProv.getStableHardwareId(success, error);
```

### 4. Get Environment

```javascript
cordova.plugins.GoogleProv.getEnvironment(success, error);
```

### 5. Register Data Changed Listener

```javascript
cordova.plugins.GoogleProv.registerDataChangedListener(success, error);
```

### 6. Push Card Tokenize

```javascript
cordova.plugins.GoogleProv.pushCardTokenize(success, error, args);
```

### 7. Push Cobadge Card Tokenize

```javascript
cordova.plugins.GoogleProv.pushCobadgeCardTokenize(success, error, args);
```

### 8. View Card Token

```javascript
cordova.plugins.GoogleProv.viewCardToken(success, error, args);
```

### 9. Is Card Tokenized

```javascript
cordova.plugins.GoogleProv.isCardTokenized(success, error, args);
```

### 10. Get Card Tokens List

```javascript
cordova.plugins.GoogleProv.getCardTokensList(success, error);
```

### 11. Get Card Token Status

```javascript
cordova.plugins.GoogleProv.getCardTokenStatus(success, error, args);
```

## Platforms Supported

- Android

## Authors

- Mohammad Al Nassri - anb
- Ahmad Al Nsour - anb


## License

This project is licensed under the MIT License.