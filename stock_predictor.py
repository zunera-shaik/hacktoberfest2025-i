# stock_predictor.py

import numpy as np
import pandas as pd
import yfinance as yf
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout
import matplotlib.pyplot as plt

# -------------------------
# CONFIGURATION
# -------------------------
TICKER = "AAPL"
START_DATE = "2015-01-01"
END_DATE = "2025-01-01"
LOOKBACK = 60

# -------------------------
# FETCH DATA
# -------------------------
def fetch_data(ticker, start, end):
    data = yf.download(ticker, start=start, end=end)
    return data

# -------------------------
# INDICATORS
# -------------------------
def add_indicators(df):
    df["SMA_20"] = df["Close"].rolling(window=20).mean()
    df["SMA_50"] = df["Close"].rolling(window=50).mean()
    df["RSI"] = compute_rsi(df["Close"])
    df["MACD"], df["MACD_signal"] = compute_macd(df["Close"])
    df.fillna(method="bfill", inplace=True)
    return df

def compute_rsi(series, period=14):
    delta = series.diff(1)
    gain = delta.clip(lower=0).rolling(window=period).mean()
    loss = -delta.clip(upper=0).rolling(window=period).mean()
    rs = gain / loss
    return 100 - (100 / (1 + rs))

def compute_macd(series, fast=12, slow=26, signal=9):
    exp1 = series.ewm(span=fast, adjust=False).mean()
    exp2 = series.ewm(span=slow, adjust=False).mean()
    macd = exp1 - exp2
    signal_line = macd.ewm(span=signal, adjust=False).mean()
    return macd, signal_line

# -------------------------
# PREPROCESS DATA
# -------------------------
def preprocess_data(data, lookback=60):
    features = ["Close", "SMA_20", "SMA_50", "RSI", "MACD", "MACD_signal"]
    scaler = MinMaxScaler()
    scaled = scaler.fit_transform(data[features])

    X, y = [], []
    for i in range(lookback, len(scaled)):
        X.append(scaled[i-lookback:i])
        y.append(scaled[i, 0])

    return np.array(X), np.array(y), scaler

# -------------------------
# BUILD LSTM MODEL
# -------------------------
def build_model(input_shape):
    model = Sequential()
    model.add(LSTM(64, return_sequences=True, input_shape=input_shape))
    model.add(Dropout(0.3))
    model.add(LSTM(64, return_sequences=False))
    model.add(Dropout(0.3))
    model.add(Dense(32, activation="relu"))
    model.add(Dense(1))
    model.compile(optimizer="adam", loss="mean_squared_error")
    return model

# -------------------------
# SIGNAL GENERATOR
# -------------------------
def generate_signal(latest_price, predicted_price, rsi, sma_20, sma_50, macd, macd_signal):
    signal = "HOLD"
    confidence = 0.0

    # Price trend prediction
    if predicted_price > latest_price:
        signal = "BUY"
        confidence += 0.4
    elif predicted_price < latest_price:
        signal = "SELL"
        confidence += 0.4

    # RSI confirmation
    if rsi < 30:
        signal = "BUY"
        confidence += 0.3
    elif rsi > 70:
        signal = "SELL"
        confidence += 0.3

    # Moving average confirmation
    if sma_20 > sma_50:
        signal = "BUY" if signal == "BUY" else "HOLD"
        confidence += 0.2
    elif sma_20 < sma_50:
        signal = "SELL" if signal == "SELL" else "HOLD"
        confidence += 0.2

    # MACD confirmation
    if macd > macd_signal:
        if signal == "BUY": confidence += 0.1
    else:
        if signal == "SELL": confidence += 0.1

    return signal, round(confidence * 100, 2)

# -------------------------
# MAIN PIPELINE
# -------------------------
if __name__ == "__main__":
    print(f"Fetching {TICKER} data...")
    data = fetch_data(TICKER, START_DATE, END_DATE)

    print("Adding indicators...")
    data = add_indicators(data)

    print("Preprocessing data...")
    X, y, scaler = preprocess_data(data, LOOKBACK)

    split = int(len(X) * 0.8)
    X_train, X_test = X[:split], X[split:]
    y_train, y_test = y[:split], y[split:]

    print("Training model...")
    model = build_model((X_train.shape[1], X_train.shape[2]))
    model.fit(X_train, y_train, epochs=20, batch_size=32, validation_data=(X_test, y_test))

    print("Predicting...")
    predictions = model.predict(X_test)
    predictions = scaler.inverse_transform(np.concatenate([predictions, np.zeros((len(predictions), 5))], axis=1))[:,0]
    y_test_scaled = scaler.inverse_transform(np.concatenate([y_test.reshape(-1,1), np.zeros((len(y_test), 5))], axis=1))[:,0]

    # Plot
    plt.figure(figsize=(12,6))
    plt.plot(y_test_scaled, label="Actual Price", color="blue")
    plt.plot(predictions, label="Predicted Price", color="red")
    plt.legend()
    plt.title(f"{TICKER} Stock Prediction & Signals")
    plt.show()

    # Next day prediction
    last_data = data[-LOOKBACK:]
    scaled_last = scaler.transform(last_data[["Close", "SMA_20", "SMA_50", "RSI", "MACD", "MACD_signal"]])
    X_input = np.reshape(scaled_last, (1, LOOKBACK, 6))
    next_day_pred = model.predict(X_input)
    next_day_price = scaler.inverse_transform(np.concatenate([next_day_pred, np.zeros((1,5))], axis=1))[0][0]

    latest_price = data["Close"].iloc[-1]
    rsi = data["RSI"].iloc[-1]
    sma_20 = data["SMA_20"].iloc[-1]
    sma_50 = data["SMA_50"].iloc[-1]
    macd = data["MACD"].iloc[-1]
    macd_signal = data["MACD_signal"].iloc[-1]

    signal, confidence = generate_signal(latest_price, next_day_price, rsi, sma_20, sma_50, macd, macd_signal)

    print(f"\n📌 Latest Close: ${latest_price:.2f}")
    print(f"📌 Predicted Next Close: ${next_day_price:.2f}")
    print(f"📊 Trading Signal: {signal} (Confidence: {confidence}%)")
      
