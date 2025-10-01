# portfolio_predictor.py

import numpy as np
import pandas as pd
import yfinance as yf
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout
import matplotlib.pyplot as plt

# -------------------------
# CONFIGURATION
# -------------------------
TICKERS = ["AAPL", "MSFT", "TSLA"]   # Multiple stocks
START_DATE = "2018-01-01"
END_DATE = "2025-01-01"
LOOKBACK = 60

# -------------------------
# FETCH MULTI-STOCK DATA
# -------------------------
def fetch_data(tickers, start, end):
    data = yf.download(tickers, start=start, end=end)["Close"]
    return data

# -------------------------
# PREPROCESS SINGLE STOCK
# -------------------------
def preprocess_data(prices, lookback=60):
    scaler = MinMaxScaler()
    scaled = scaler.fit_transform(prices.values.reshape(-1, 1))

    X, y = [], []
    for i in range(lookback, len(scaled)):
        X.append(scaled[i-lookback:i, 0])
        y.append(scaled[i, 0])
    return np.array(X).reshape(-1, lookback, 1), np.array(y), scaler

# -------------------------
# BUILD MODEL
# -------------------------
def build_model(input_shape):
    model = Sequential()
    model.add(LSTM(50, return_sequences=True, input_shape=input_shape))
    model.add(Dropout(0.2))
    model.add(LSTM(50))
    model.add(Dropout(0.2))
    model.add(Dense(1))
    model.compile(optimizer="adam", loss="mean_squared_error")
    return model

# -------------------------
# PORTFOLIO OPTIMIZATION
# -------------------------
def optimize_portfolio(predicted_returns):
    weights = np.array(predicted_returns) / np.sum(predicted_returns)
    return weights

# -------------------------
# MAIN PIPELINE
# -------------------------
if __name__ == "__main__":
    print("Fetching data...")
    data = fetch_data(TICKERS, START_DATE, END_DATE)

    predictions = {}
    predicted_returns = []

    for ticker in TICKERS:
        print(f"\nProcessing {ticker}...")
        prices = data[ticker].dropna()

        X, y, scaler = preprocess_data(prices, LOOKBACK)

        split = int(len(X) * 0.8)
        X_train, X_test = X[:split], X[split:]
        y_train, y_test = y[:split], y[split:]

        model = build_model((X_train.shape[1], 1))
        model.fit(X_train, y_train, epochs=10, batch_size=32, verbose=0)

        # Next-day prediction
        last_data = prices[-LOOKBACK:].values
        scaled_last = scaler.transform(last_data.reshape(-1, 1))
        X_input = scaled_last.reshape(1, LOOKBACK, 1)
        next_pred = model.predict(X_input)[0][0]
        next_price = scaler.inverse_transform([[next_pred]])[0][0]

        predictions[ticker] = next_price
        predicted_returns.append((next_price - prices.iloc[-1]) / prices.iloc[-1])

        print(f"Latest Close: {prices.iloc[-1]:.2f}, Predicted Next Close: {next_price:.2f}")

    # Portfolio recommendation
    weights = optimize_portfolio(predicted_returns)
    portfolio = {TICKERS[i]: round(weights[i] * 100, 2) for i in range(len(TICKERS))}

    print("\n📊 Portfolio Allocation Suggestion:")
    for stock, weight in portfolio.items():
        print(f"{stock}: {weight}%")
  
