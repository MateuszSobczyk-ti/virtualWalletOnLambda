package com.sobczyk.walletMicroservices.service;
import io.polygon.kotlin.sdk.rest.*;
import io.polygon.kotlin.sdk.rest.reference.TickerNewsParametersV2Builder;
import io.polygon.kotlin.sdk.rest.reference.TickerNewsResponse;
import io.polygon.kotlin.sdk.rest.stocks.PreviousCloseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PolygonApiClient {

    //many PolygonRestClients to increase maximum free api calls ;)
    private final PolygonRestClient client1;
    private final PolygonRestClient client2;
    private final PolygonRestClient client3;

    public PolygonApiClient(@Value("${polygon.key1}") String key1, @Value("${polygon.key2}") String key2,
            @Value("${polygon.key3}") String key3) {
        this.client1 = new PolygonRestClient(key1);
        this.client2 = new PolygonRestClient(key2);
        this.client3 = new PolygonRestClient(key3);
    }

    public AggregatesDTO getAggregatesBlocking(String ticker, String timespan, Long multiplier, LocalDate dateFrom) {
        return client1.getAggregatesBlocking(
                new AggregatesParametersBuilder()
                        .ticker(ticker)
                        .timespan(timespan)
                        .multiplier(multiplier)
                        .fromDate(dateFrom.toString())
                        .toDate(LocalDate.now().toString())
                        .build());
    }

    public PreviousCloseDTO getPreviousCloseDto(String ticker) {
        return client2.getStocksClient().getPreviousCloseBlocking(ticker, false);
    }

    public TickerNewsResponse getTickerNews(String ticker) {
        return client3.getReferenceClient().getTickerNewsBlockingV2(
                new TickerNewsParametersV2Builder()
                        .ticker(ComparisonQueryFilterParameters.equal(ticker))
                        .build()
        );
    }
}
