package com.hackerrank.stocktrade.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackerrank.stocktrade.model.Trade;


@Repository
public interface TradeRepository extends JpaRepository<Trade,Long>  {
	
	List<Trade> findById (Long id);
	
	List<Trade> findBySymbol (String symbol);

	List<Trade> findByUserId(Long id);
	
	@Query(value="SELECT * FROM TRADE WHERE SYMBOL=?1 AND TIMESTAMP BETWEEN ?2 AND ?3",nativeQuery= true )
	List<Trade> findBySymbolAndTimestampBetween(String stockSymbol, Date startDate, Date endDate);
	  
	@Query(value="SELECT * FROM TRADE WHERE TRUNC(TIMESTAMP) BETWEEN ?1 AND ?2",nativeQuery= true )
	List<Trade> findByFluctuationsCount( Date startDate, Date endDate);

	@Query(value="SELECT distinct symbol FROM TRADE ",nativeQuery= true )
	Set<String> findAllSymbols();

}

