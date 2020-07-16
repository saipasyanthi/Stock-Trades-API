package com.hackerrank.stocktrade.service;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import com.hackerrank.stocktrade.exception.RecordNotFoundException;
import com.hackerrank.stocktrade.model.Trade;

public interface TradeService {

  public ResponseEntity<Trade> createTrade(Trade trade);
  
  public List<Trade>  getAllTrade();

public List<Trade> getTradesByUserId(Long id) throws RecordNotFoundException;

public JSONObject getStocksPrice(String stockSymbol, Date startDate, Date endDate) throws RecordNotFoundException;

public void deleteAllTrades();

public JSONArray getFluctuationsCount(Date start, Date end);
  
  
}
