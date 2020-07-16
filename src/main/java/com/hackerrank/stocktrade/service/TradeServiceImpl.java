package com.hackerrank.stocktrade.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackerrank.stocktrade.exception.RecordNotFoundException;
import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.repository.TradeRepository;


@Service
public class TradeServiceImpl  implements TradeService{
	
	@Autowired
    private TradeRepository tradeRepo;
	
	@Override
	public ResponseEntity<Trade> createTrade(Trade trade) {
		
		List<Trade> trades = tradeRepo.findById(trade.getId());
        
        if(trades.size()>0) {
        	 return new ResponseEntity<Trade>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
        	tradeRepo.save(trade);
        	 return new ResponseEntity<Trade>(tradeRepo.save(trade), new HttpHeaders(), HttpStatus.CREATED);
        }
    }		
	
	@Override
	public List<Trade>  getAllTrade() {
		  List<Trade> tradeList = tradeRepo.findAll();
	         
	        if(tradeList.size() > 0) {
	            return tradeList;
	        } else {
	            return new ArrayList<Trade>();
	        }
	}

	@Override
	public List<Trade> getTradesByUserId(Long id) throws RecordNotFoundException {
		 List<Trade>  trade = tradeRepo.findByUserId(id);
         
	        if(trade.size() > 0) {
	        	 return trade;
	        } else {
	            return new ArrayList<Trade>();
	        }
	}

	@Override
	public JSONObject getStocksPrice(String stockSymbol, Date startDate, Date endDate) throws RecordNotFoundException{
		
		List<Trade>  tradesymbol = tradeRepo.findBySymbol(stockSymbol);
		
		if(tradesymbol.size()>0) {
			
			List<Trade>  trade = tradeRepo.findBySymbolAndTimestampBetween(stockSymbol,startDate,endDate);
			
			if(trade.size()>0) {
			
			     List<Float> numbers = new ArrayList<Float>();
			
				for(int i=0;i<trade.size();i++) {			
				    numbers.add(trade.get(i).getPrice());
				}
								
				JSONObject json = new JSONObject();
				 json.put("symbol", stockSymbol);
				 json.put("highest", Collections.max(numbers));
				 json.put("lowest", Collections.min(numbers));		 
				 
			       return json;
				}
				else {
					 JSONObject json1 = new JSONObject();
		    		 json1.put("message", "There are no trades in the given date range");
		    		 return json1;
				}
				
			}
	
		else {
		return null ;
	}
	}
	

	@Override
	public void deleteAllTrades() {
		tradeRepo.deleteAll();
		
	}

	@Override
	
	public JSONArray getFluctuationsCount(Date start, Date end) {
	
		List<Trade>  tradesfluctuations = tradeRepo.findByFluctuationsCount(start,end);


				
		JSONArray json = new JSONArray();

		Map<String, List<Trade>> tradesBySymbol = tradesfluctuations.stream().collect(Collectors.groupingBy(Trade::getSymbol));
		Set<String> allSymbols = tradeRepo.findAllSymbols();
		Set<String> queriedSymbols = tradesBySymbol.keySet();

		allSymbols.removeAll(queriedSymbols);

		for(String missingSymbol:allSymbols){
			JSONObject symbolObject= new JSONObject();
			symbolObject.put("stock",missingSymbol);
			symbolObject.put("message", "There are no trades in the given date range");
			json.add(symbolObject);
		}

		for(String symbol:tradesBySymbol.keySet()){
			List<Trade> trades =  tradesBySymbol.get(symbol);
			Collections.sort(trades);
			double previousPrice = 0.0;
			double max_rise = 0.0;
			double max_fall = 0.0;
			int fluctuations = 0;
			boolean increased = false;
			boolean decreased = false;
			int i = 0;
			for(Trade trade:trades){
				if(i>0){
					max_rise = (trade.getPrice() - previousPrice) > 0 && (trade.getPrice() - previousPrice) > max_rise ? (trade.getPrice() - previousPrice) : max_rise;
					max_fall = (previousPrice - trade.getPrice()) > 0 && (previousPrice - trade.getPrice()) > max_fall ? (previousPrice - trade.getPrice()) : max_fall;
				}

				if(i>0 && previousPrice>trade.getPrice()){
					decreased = true;
				}else if(i>0 && previousPrice<trade.getPrice()){
					increased = true;
				}

				if(increased && decreased){
					fluctuations ++;
					decreased = previousPrice>trade.getPrice()?true:false;
					increased = previousPrice<trade.getPrice()?true:false;
				}
				previousPrice = trade.getPrice();
				i++;
			}

			JSONObject symbolObject= new JSONObject();
			symbolObject.put("stock",symbol);
			symbolObject.put("max_rise",Math.round(max_rise * 100.0) / 100.0);
			symbolObject.put("max_fall",Math.round(max_fall * 100.0) / 100.0);
			symbolObject.put("fluctuations",fluctuations);

			json.add(symbolObject);

		}

		return json;
	}

	
}
			
	 


