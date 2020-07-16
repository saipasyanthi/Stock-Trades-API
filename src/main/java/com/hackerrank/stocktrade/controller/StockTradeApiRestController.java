package com.hackerrank.stocktrade.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.stocktrade.exception.RecordNotFoundException;
import com.hackerrank.stocktrade.model.User;
import com.hackerrank.stocktrade.service.TradeService;
import com.hackerrank.stocktrade.model.Trade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class StockTradeApiRestController {


	@Autowired
    private TradeService tradeService;
	
	
	 @PostMapping("/trades")
	    public  ResponseEntity<Trade> createTrade(@RequestBody Trade trade)
	                                                    throws RecordNotFoundException {
	            
		 ResponseEntity<Trade> Trade= tradeService.createTrade(trade);
	    	return Trade;  	    	
	  	    }
    
    @GetMapping("/trades") 
    public ResponseEntity<List<Trade>> getAllTrades() {
    	
    	 List<Trade> list =tradeService.getAllTrade();
    	  return new ResponseEntity<List<Trade>>(list, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/trades/users/{userID}") 
    public ResponseEntity<List<Trade>> getTradesByUserId(@PathVariable("userID") Long id) 
            throws RecordNotFoundException {
    	
    	 List<Trade> list = tradeService.getTradesByUserId(id);
    	if(list.size()>0) {
    		return new ResponseEntity<List<Trade>>(list, new HttpHeaders(), HttpStatus.OK);}
    	else {
    		return new ResponseEntity<List<Trade>>(new HttpHeaders(), HttpStatus.NOT_FOUND);
    		}
       }
    @GetMapping("/stocks/{stockSymbol}/price") 
    public ResponseEntity<JSONObject> getStocksPrices(@PathVariable("stockSymbol") String stockSymbol,
    		
    	 @RequestParam(name = "start", required = false, defaultValue = "10-10-2017") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
    	 @RequestParam(name = "end", required = false, defaultValue = "10-10-2017") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end

    		) 
            throws RecordNotFoundException {
    	
    	   JSONObject json= tradeService.getStocksPrice(stockSymbol,start,end);
    	   if(json!=null) {
    		 return new ResponseEntity<JSONObject>(json,new HttpHeaders(), HttpStatus.OK);
    	   }
    	   else {
    		   return new ResponseEntity<JSONObject>(new HttpHeaders(), HttpStatus.NOT_FOUND);
    	   }
    	    	
    }       

    
    @GetMapping("/stocks/stats") 
    public ResponseEntity<JSONArray> getFluctuationsCount (
    	 @RequestParam(name = "start", required = false, defaultValue = "10-10-2017") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
    	 @RequestParam(name = "end", required = false, defaultValue = "10-10-2017") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end

    		) 
            throws RecordNotFoundException {
    	
    	   JSONArray json= tradeService.getFluctuationsCount(start,end);
    	 
    		 return new ResponseEntity<JSONArray>(json,new HttpHeaders(), HttpStatus.OK);
    	   
    	  
    	    	
    }  
  
      @DeleteMapping("/erase")
     	    public HttpStatus erase()  throws RecordNotFoundException {
    	     tradeService.deleteAllTrades();
	        return HttpStatus.OK;
	    }
}
