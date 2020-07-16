package com.hackerrank.stocktrade.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackerrank.stocktrade.exception.RecordNotFoundException;
import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.model.User;
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
	
	public JSONObject getFluctuationsCount(Date start, Date end) {
	
		List<Trade>  tradesfluctuations = tradeRepo.findByFluctuationsCount(start,end);
				
		 JSONObject json = new JSONObject();
		 
	     MultiMap multiMap = new MultiValueMap();
	     
		for(int i=0;i<tradesfluctuations.size();i++) {				
			 
		        multiMap.put(tradesfluctuations.get(i).getSymbol(), tradesfluctuations.get(i).getPrice());
			}	
		
	     
	        Set<String> keys = multiMap.keySet();
	        
	        for (String key : keys) {
	            System.out.println("Key = " + key);
	            System.out.println("Values = " + multiMap.get(key) + "n");
	           
	            List<Float> price = (ArrayList<Float>) multiMap.get(key);	
	            
	         //   System.out.println(price.get(1)); 
	        }
	        return null;
	}

	
}
			
	 


