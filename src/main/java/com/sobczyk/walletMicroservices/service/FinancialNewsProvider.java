package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.responses.FinancialNewsResponse;
import io.polygon.kotlin.sdk.rest.reference.TickerNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinancialNewsProvider {

    private final PolygonApiClient polygonApiClient;

    public List<FinancialNewsResponse> getFinancialNews(String ticker) {
        return Objects.requireNonNull(this.polygonApiClient.getTickerNews(ticker)
                        .getResults())
                .stream()
                .limit(3)
                .map(this::mapToFinancialNewsResponse)
                .toList();
    }

    public FinancialNewsResponse mapToFinancialNewsResponse(TickerNews tickerNews) {
        return new FinancialNewsResponse(tickerNews.getArticleUrl(), tickerNews.getImageUrl(), tickerNews.getTitle());
    }

}
